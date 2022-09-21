package mercado.potencial.application;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import jpa.SisoraMessage;
import mercado.potencial.jpa.gestcli_sisora.Tcl_ClieProjection;
import mercado.potencial.jpa.gestcli_sisora.Tipcl_Camp_RespuestaProjection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de generar datos de Auditoria por cada registro encontrado
 */
public class AuditoriaProjection {

    private static Logger LOG = Logger.getLogger(AuditoriaProjection.class);

    private String path;

    protected String FILENAME_CLIENTCODES_AND_SOURCES = "clientCodesAndSources.txt";

    protected String FILENAME_AUDIT_ALTAS = "altas.csv";

    protected String FILENAME_AUDIT_MODIFICACIONES = "modificaciones.csv";

    protected String FILENAME_AUDIT_RECHAZOS = "rechazos.csv";

    protected String FILENAME_AUDIT = "audit_projections.csv";

    private Map<Integer, Object[]> mapaAuditoria;

    private Tcl_ClieProjection tcl_clieProjection;

    private Tipcl_Camp_RespuestaProjection tipcl_camp_respuestaProjection;

    public AuditoriaProjection(String path){
        this.path = path;
    }

    public Map<Integer, Object[]> getMapaAuditoria() {
        return mapaAuditoria;
    }

    public void initAuditProjections(Tcl_ClieProjection tcl_clieProjection,
                                     Tipcl_Camp_RespuestaProjection tipcl_camp_respuestaProjection){
        this.tcl_clieProjection = tcl_clieProjection;
        this.tipcl_camp_respuestaProjection = tipcl_camp_respuestaProjection;
    }

    /**
     * Método que genera los recursos iniciales para la construcción del fichero madre
     * Los recursos iniciales versan de una Auditoria para saber:
     * Código de cliente
     * Fecha de creación
     * Fecha de modificación
     * Tipo de operación
     * Origen del dato
     */
    public void generateResourcesForTheAudit() {
        File auditFile = new File(this.path + FILENAME_AUDIT);
        if (!auditFile.exists()) {
            Map<Integer, String> clientCodesAndSources;
            File source = new File(this.path+FILENAME_CLIENTCODES_AND_SOURCES);
            if (!source.exists()) {
                /**PASO 1: GENERO UN MAPA CON LOS ORIGENES DE LOS CÓDIGOS DE CLIENTES **/
                clientCodesAndSources = getClientCodesAndSources();

                /**PASO 2: HAGO UNA COPIA CON EL RESULTADO DEL PASO 1 **/
                backUpClientCodesAndSource(clientCodesAndSources);
            } else {
                List<String> values = Utils.generateListFromFile(this.path, FILENAME_CLIENTCODES_AND_SOURCES);
                clientCodesAndSources = Utils.getMapIntegerStringFromListString(values);
            }

            /**PASO 3: BUSQUEDA DE PROYECCIONES DE ALTAS, MODIFICACIONES Y RECHAZOS*/
            searchingForProjectionsProjections(FILENAME_AUDIT_ALTAS, "ALTA", clientCodesAndSources);
            searchingForProjectionsProjections(FILENAME_AUDIT_MODIFICACIONES, "MODIFICACION", clientCodesAndSources);
            searchingForProjectionsProjections(FILENAME_AUDIT_RECHAZOS, "MODIFICACIÓN-RECHAZADA", clientCodesAndSources);

            mapaAuditoria = auditBackUpRecover(new LinkedHashMap<>(), this.path, FILENAME_AUDIT_ALTAS);//149452
            mapaAuditoria = auditBackUpRecover(mapaAuditoria, this.path, FILENAME_AUDIT_MODIFICACIONES);//326624
            mapaAuditoria = auditBackUpRecover(mapaAuditoria, this.path, FILENAME_AUDIT_RECHAZOS);//564533

            LOG.info("Escribiendo resultados...");
            List<Object[]> projectionWithOutRepetition = new ArrayList<>(mapaAuditoria.values());
            Text.generateCsvFileFromObjectProjection(projectionWithOutRepetition, this.path, FILENAME_AUDIT);
            LOG.info("Los resultados han sido escritos en " + this.path+FILENAME_AUDIT);
        }
        if (mapaAuditoria == null || mapaAuditoria.isEmpty()) {
            mapaAuditoria = auditBackUpRecover(new LinkedHashMap<>(), this.path, FILENAME_AUDIT_ALTAS);
            mapaAuditoria = auditBackUpRecover(mapaAuditoria, this.path, FILENAME_AUDIT_MODIFICACIONES);
            mapaAuditoria = auditBackUpRecover(mapaAuditoria, this.path, FILENAME_AUDIT_RECHAZOS);
        }
    }

    /**
     * Método que genera un mapa con los códigos de clientes y sus origenes
     * Se usa un fichero externo que constiene los origenes de los códigos y también se realizan consultas a bases de datos
     *
     * @return mapa con todos los clientes y sus origenes
     */
    private Map<Integer, String> getClientCodesAndSources() {
        Map<Integer, String> result = null;
        try {
            LOG.info("Cargando códigos de clientes y origenes que han sido eliminados por error...");
            result = Utils.generateMapFromFileCsv(this.path, "client_and_resource_codes_before_deletion.csv");//504270
            Map<String, Map<String, String>> allSources = getAllSources();
            for (String operationType : allSources.keySet()) {
                Map<String, String> origenes = allSources.get(operationType);
                for (String origen : origenes.keySet()) {
                    LOG.info("Buscando los códigos de los clientes (clientCodes) con origen " + origenes.get(origen) + " y tipo de operación " + operationType);
                    List<Integer> clientCodes = this.tcl_clieProjection.getClientCodesSegmented(origen, operationType);
                    result = getMapWithSurce(result, clientCodes, origenes.get(origen));
                }
            }
        } catch (Exception e) {
            LOG.error(SisoraMessage.GENERIC_ERROR + ": " + e.getMessage());
        }
        return result;
    }

    /**
     * Método que genera un mapa con los tipos de operación y sus respectivos origenes
     *
     * @return
     */
    private Map<String, Map<String, String>> getAllSources() {
        Map<String, String> origenes = new LinkedHashMap<>();
        origenes.put("0001", "octoparse");
        origenes.put("0004", "scrappinghub");
        origenes.put("0006", "datacentric");
        origenes.put("0007", "facebook");
        origenes.put("0008", "listanegocio");
        Map<String, Map<String, String>> result = new LinkedHashMap<>();
        result.put("0160", origenes);
        result.put("0161", origenes);
        return result;
    }

    /**
     * Método que genera un mapa con los códigos de clientes y el origen del dato (octoparse, scrappinghub, datacentric, facebook, listanegocio)     *
     *
     * @param mapa
     * @param clientCodes
     * @param source
     * @return
     */
    private Map<Integer, String> getMapWithSurce(Map<Integer, String> mapa, List<Integer> clientCodes, String source) {
        clientCodes.forEach(clientCode -> {
            mapa.put(clientCode, source);
        });
        return mapa;
    }

    private void backUpClientCodesAndSource(Map<Integer, String> mapa) {
        if (mapa != null && mapa.size() > 0) {
            List<String> values = new ArrayList<>();
            mapa.forEach((clave, valor) -> {
                values.add(clave + "," + valor);
            });
            Text.generateTxtFileWithStrings(values, this.path, FILENAME_CLIENTCODES_AND_SOURCES);
        }
    }

    /**
     * Método que busca proyecciones según el tipo de operación que se haya realizado (alta, modificación, rechazos)
     * @param fileName
     * @param operationType
     * @param clientCodesAndSources
     */
    private void searchingForProjectionsProjections(String fileName, String operationType, Map<Integer, String> clientCodesAndSources){
        File file = new File(this.path+fileName);
        if (!file.exists()) {
            LOG.info("Buscando proyecciones para la auditoria con tipo de operación "+operationType+"...");
            List<Object[]> projections;
            if("ALTA".equalsIgnoreCase(operationType)){
                projections = this.tcl_clieProjection.getRegistrationByScrappingProcess();//149452
            }else if("MODIFICACION".equalsIgnoreCase(operationType)){
                projections = this.tcl_clieProjection.getModificationByScrappingProcess();//184800
            }else{
                projections = this.tipcl_camp_respuestaProjection.getRejectedModificationsByScrappingProcess();//564533
            }
            List<Object[]> auditProjection = getAuditoriaProjection(clientCodesAndSources, projections, operationType);
            Text.generateCsvFileFromObjectProjection(auditProjection, this.path, fileName);
        }
    }

    /**
     * Método que añade el origen del dato y el tipo de operación realizada en el dato, ejemplo: (facebook, modificacion)
     *
     * @param origenes
     * @param projections
     * @param tipo_operacion
     * @return Lista de auditoria (proyecciones)
     */
    private List<Object[]> getAuditoriaProjection(Map<Integer, String> origenes, List<Object[]> projections, String tipo_operacion) {
        LOG.info("Añadiendo origen del dato y tipo de operación en el dato...");
        List<Object[]> result = new ArrayList<>();
        for (Object[] projection : projections) {
            Object[] values = new Object[5];
            values[0] = projection[0];
            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            if (projection[1] != null) {
                values[1] = formato.format(projection[1]);
            }
            if (projection[2] != null) {
                values[2] = formato.format(projection[2]);
            }
            values[3] = (Utils.evaluateString(origenes.get(projection[0]))) ? origenes.get(projection[0]) : "DESCONOCIDO";
            values[4] = tipo_operacion;
            result.add(values);
        }
        return result;
    }

    /**
     * Método que genera una lista de tipo String desde un fichero de texto     *
     *
     * @param path
     * @param fileName
     * @return
     */
    private Map<Integer, Object[]> auditBackUpRecover(Map<Integer, Object[]> values, String path, String fileName) {
        File file = new File(path + fileName);
        if (!file.exists()) {
            LOG.info("No existe el fichero " + fileName);
            return null;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String value;
            while ((value = bufferedReader.readLine()) != null) {
                String[] array = value.split(",");
                Integer clientCode = Integer.parseInt(array[0]);

                Object[] projection = new Object[5];
                projection[0] = Integer.parseInt(array[0]);
                projection[1] = array[1];
                projection[2] = array[2];
                projection[3] = array[3];
                projection[4] = array[4];

                values.put(clientCode, projection);
            }
            bufferedReader.close();
        } catch (Exception e) {
            LOG.info("Ha ocurrido un error al leer el fichero " + fileName + ": " + e.getMessage());
        }
        return values;
    }
}
