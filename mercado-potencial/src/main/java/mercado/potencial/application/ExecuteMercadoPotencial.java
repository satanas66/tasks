package mercado.potencial.application;

import automation.factory.Logger;
import automation.factory.xlsx.Excel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 */
public class ExecuteMercadoPotencial {

    private static Logger LOG = Logger.getLogger(ExecuteMercadoPotencial.class);

    /**
     * Previa a la ejecución del proceso se deben tener en cuenta e cosas:
     * a) El proceso Recomendador de paquetes se debe lanzar la última semana del mes actual o cómo muy tarde la primera semana del mes siguiente
     * b) Deben existir en el directorio resources los siguientes ficheros actualizados:
     * 1. actividades_a_excluir.txt (Solicitar a Rubén Coll el último actualizado)
     * 2. email_hunter.csv (Solicitar a Juan Blasco el último actualizado)
     * 3. client_and_resource_codes_before_deletion.csv (Son los códigos de clientes actuales y eliminados de la colección scrapping)
     *
     * @param arg
     */
    public static void main(String[] arg) {
        MercadoPotencial mercadoPotencial = new MercadoPotencial();
        execution(10, mercadoPotencial);
//        combineFiles();
//        combineSheets();
    }

    private static void execution(int threadNumber, MercadoPotencial mercadoPotencial) {
        if (mercadoPotencial.isContinuaProceso()) {
            List<List<Integer>> clientCodes = mercadoPotencial.listsForExecutionByThreads(threadNumber);
            setUp(mercadoPotencial, clientCodes);
        } else {
            mercadoPotencial.closureOfConnections();
        }
    }

    private static void setUp(MercadoPotencial mercadoPotencial, List<List<Integer>> clientCodes) {
        List<MercadoPotencialThread> hilos = new ArrayList<>();
        for (int i = 0; i < clientCodes.size(); i++) {
            MercadoPotencialThread hilo = new MercadoPotencialThread(" Hilo" + (i + 1));
            hilo.instancePatameters(mercadoPotencial.PATH + "mercado-potencial/", mercadoPotencial.FILENAME_MERCADO_POTENCIAL + (i + 1) + mercadoPotencial.EXTENSION);
            hilo.instanceAuditoria(mercadoPotencial.getMapaAuditoria());
            hilo.instancePhysycalResource(mercadoPotencial.getMapaEmailHunter(), mercadoPotencial.getActividadesExcluir());
            hilo.instanceOracleProjection(
                    mercadoPotencial.getF_datos_contactoProjection(),
                    mercadoPotencial.getF_impagoProjection(),
                    mercadoPotencial.getGestor_oportunidadesProjection(),
                    mercadoPotencial.getTsf_accountProjection(),
                    mercadoPotencial.getTsf_taskProjection(),
                    mercadoPotencial.getTsi_actvadProjection(),
                    mercadoPotencial.getTsi_emprvcaractProjection(),
                    mercadoPotencial.getTsi_f_webProjection(),
                    mercadoPotencial.getTsi_sectorProjection()
            );
            hilo.instanceMySqlProjection(mercadoPotencial.getTdv_oportunidades_por_clienteProjection(),
                    mercadoPotencial.getTdv_tramo_historico_alianzaProjection(),
                    mercadoPotencial.getThv_actividad_comercial_consProjection(),
                    mercadoPotencial.getVhv_cuotas_mes_v2Projection()
            );
            hilo.instanceMongoProjection(mercadoPotencial.getWebVisibilityAnalyticsProjection(),
                    mercadoPotencial.getBi_recomendatorProjection()
            );
            hilo.instanceListClientCodes(clientCodes.get(i));
            hilos.add(hilo);
        }
        hilos.forEach(Thread::start);
    }

    public static void combineFiles() {
        LOG.info("Inicio de la combinación de los ficheros MERCADO_POTENCIAL_X...");
        String path = "C:/tasks/mercado-potencial/src/main/resources/mercado-potencial/A/";
        Excel.mergeExcelDocuments(path, "MERCADO_POTENCIAL_", "MERCADO_POTENCIAL.xlsx");
        LOG.info("Final de la combinación de los ficheros MERCADO_POTENCIAL_X.");
    }

    private static void combineSheets() {
        LOG.info("Inicio de la combinación de las hojas del fichero MERCADO_POTENCIAL_X...");
        String path = "C:/tasks/mercado-potencial/src/main/resources/mercado-potencial/A/";
        Excel.combineSheets(path, "MERCADO_POTENCIAL.xlsx", "MERCADO_POTENCIAL_DEST.xlsx");
        LOG.info("Final de la combinación de las hojas del fichero MERCADO_POTENCIAL_X.");

    }

}
