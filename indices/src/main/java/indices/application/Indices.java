package indices.application;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import automation.factory.xlsx.Excel;
import com.mongodb.DBCollection;
import indices.domain.business.IndiceVisibilidad;
import indices.domain.mapper.IndicesMapper;
import indices.jpa.phw_vac.F_Datos_ContactoProjection;
import indices.jpa.sisora.Tsi_ActvadProjection;
import indices.mongo.webVisibilityAnalytics.WebVisibilityAnalyticsProjection;
import jpa.ConnectionJpa;
import jpa.SisoraMessage;
import mongo.ConnectionMongo;

import javax.persistence.EntityManager;
import java.io.File;
import java.util.*;

public class Indices {

    private static Logger LOG = Logger.getLogger(Indices.class);

    protected String PATH = "C:/tasks/indices/src/main/resources/";

    protected String PROYECCION = "PROYECCION";

    protected String EXTENSION = ".csv";

    private ConnectionMongo connectionMongo;

    private DBCollection webVisibilityAnalyticsDbCollection;

    private ConnectionJpa connectionJpa;

    private EntityManager entityManagerPHWVAC;

    private EntityManager entityManagerSISORA;

    private F_Datos_ContactoProjection f_datos_contactoProjection;

    private Tsi_ActvadProjection tsi_actvadProjection;

    private WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection;

    private boolean continuaProceso = true;

    public Indices() {
        relationalDBStart();
        noRelationalDBStart();
        initOracleProjections();
        initMongoProjections();
    }

    public F_Datos_ContactoProjection getF_datos_contactoProjection() {
        return f_datos_contactoProjection;
    }

    public Tsi_ActvadProjection getTsi_actvadProjection() {
        return tsi_actvadProjection;
    }

    public WebVisibilityAnalyticsProjection getWebVisibilityAnalyticsProjection() {
        return webVisibilityAnalyticsProjection;
    }

    /**
     * Método que establece las conexiones a las Bases de datos relacionales Oracle y MySql
     */
    public void relationalDBStart() {
        if (continuaProceso) {
            LOG.info("Abriendo conexiones a OracleDB");
            connectionJpa = new ConnectionJpa();
            entityManagerPHWVAC = connectionJpa.getPHWVACEntityManager();
            entityManagerSISORA = connectionJpa.getSisoraEntityManager();
            if (entityManagerPHWVAC == null || !entityManagerPHWVAC.isOpen() ||
                    entityManagerSISORA == null || !entityManagerSISORA.isOpen()) {
                continuaProceso = false;
            }
            LOG.info("Conexiones establecidas a OracleDB");
        }
    }

    /**
     * Método que establece la conexión a la Base de datos MONGO
     */
    public void noRelationalDBStart() {
        if (continuaProceso) {
            LOG.info("Abriendo conexión a MONGODB");
            connectionMongo = new ConnectionMongo();
            if (connectionMongo.getMongoClient() != null) {
                webVisibilityAnalyticsDbCollection = connectionMongo.getDBCollection("webVisibilityAnalytics");
            } else {
                continuaProceso = false;
            }
            LOG.info("Conexión establecida con MONGODB");
        }
    }

    /**
     * Método que cierra las conexiones a MONGO, ORACLE y MySql
     */
    public void closureOfConnections() {
        LOG.info("Cerrando conexiones a MongoDB y OracleDB");
        if (connectionMongo != null) {
            connectionMongo.endConnection();
        }
        if (entityManagerPHWVAC != null && entityManagerPHWVAC.isOpen()) {
            entityManagerPHWVAC.close();
        }
        if (entityManagerSISORA != null && entityManagerSISORA.isOpen()) {
            entityManagerSISORA.close();
        }
        LOG.info("Las conexiones a OracleDB, MySQL y MongoDB han sido cerradas");
    }

    /**
     * Método que instancia las clases de proyección para recoger devolver todos los
     * valores asociados a los KPIs desde OracleDB
     */
    public void initOracleProjections() {
        LOG.info("Instanciado clases projection para realizar las consultas a OracleDB");
        f_datos_contactoProjection = new F_Datos_ContactoProjection(entityManagerPHWVAC);
        tsi_actvadProjection = new Tsi_ActvadProjection(entityManagerSISORA);
    }

    /**
     * Método que instancia las colecciones para recoger, evaluar y devolver todos los
     * valores asociados a los KPIs desde MongoDB
     */
    public void initMongoProjections() {
        LOG.info("Instanciado clases projection para realizar las consultas a MongoDB");
        webVisibilityAnalyticsProjection = new WebVisibilityAnalyticsProjection(webVisibilityAnalyticsDbCollection);
    }

    public List<List<Integer>> listsForExecutionByThreads(int numberOfDivisions) {
        List<Integer> clientCodes = Utils.generateIntegerListFromFile(PATH, "client_codes.txt");
        Collections.sort(clientCodes);
        return Utils.getListDivision(clientCodes, numberOfDivisions);
    }

    /**
     * Método encargado de buscar el código de sector y de asociarlo a cada línea del fichero proyecciones1.csv
     */
    public void searchAndAsociationInformationFromOracle(int threadNumber) {
        LOG.info("INICIO-PASO 2: Buscando los sectores en ORACLE y asociándolos a cada línea del fichero proyecciones1.csv...");
        try {
            if (!new File(PATH, PROYECCION + (threadNumber+1) + EXTENSION).exists()) {
                List<String> projectionsPartTwo = new ArrayList<>();
                List<Object[]> tsi_actvadValues = tsi_actvadProjection.getAllCoActvadGroupByCoSector();
                Map<String, List<Integer>> mapa = generateMapCoSectorAndCoActvads(tsi_actvadValues);
                for (int i = 0; i < threadNumber; i++) {
                    List<String> projectionsPartOne = Utils.generateListFromFile(PATH, PROYECCION + i + EXTENSION);
                    if (projectionsPartOne != null && projectionsPartOne.size() > 0 && mapa.size() > 0) {
                        for (String proyeccion : projectionsPartOne) {
                            String[] array = proyeccion.split(",");
                            Integer co_actvad = Integer.parseInt(array[1]);
                            if (co_actvad.equals(999999999)) {
                                String value = proyeccion + "," + 999999999;
                                projectionsPartTwo.add(value);
                            } else {
                                for (String co_sector : mapa.keySet()) {
                                    List<Integer> listActvad = mapa.get(co_sector);
                                    if (listActvad.contains(co_actvad)) {
                                        String value = proyeccion + "," + co_sector;
                                        projectionsPartTwo.add(value);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    LOG.info("FIN-PASO 2: Se ha generado el fichero proyecciones2.csv correctamente.");
                }
                Text.generateTxtFileWithStrings(projectionsPartTwo, PATH, PROYECCION + (threadNumber+1) + EXTENSION);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * Método encargado de calcular las medias por sector para posición GMB y keyword top10.
     * Una vez calculadas las mediar se procede a la construcción de objetos java IndiceVisibilidad
     * para así generar un fichero xlsx con los resultados.
     */
    public void generateFileIndiceVisibilidad(int threadNumber) {
        try {
            LOG.info("INICIO-PASO 4: Calculo de las medias para Posición GMB y Keyword Top10 y creación de fichero xlsx.");
            Map<String, List<IndiceVisibilidad>> mapa = getMapaSectoresAndIndiceVisibilidad(threadNumber);
            List<List<Object>> totales = new ArrayList<>();
            for (String key : mapa.keySet()) {

                    LOG.info("SECTOR: " + key);
                    List<IndiceVisibilidad> values = mapa.get(key);
                    int sumaPosicionGMB = 0;
                    int sumaKeywordTop10 = 0;
                    int tamPosicionGMB = 0;
                    int tamKeywordTop10 = 0;
                    for (IndiceVisibilidad value : values) {
                        if (!value.getPosicionGMB().equals(999999999)) {
                            tamPosicionGMB++;
                            sumaPosicionGMB = sumaPosicionGMB + value.getPosicionGMB();
                        }
                        if (!value.getKeywordTop10().equals(999999999)) {
                            tamKeywordTop10++;
                            sumaKeywordTop10 = sumaKeywordTop10 + value.getKeywordTop10();
                        }
                    }
                    float mediaPosicionGMB = (float) sumaPosicionGMB / tamPosicionGMB;
                    float mediaKeywordTop10 = (float) sumaKeywordTop10 / tamKeywordTop10;
                    LOG.info("INTERMEDIO-PASO 4: Asociación de las medias a cada objeto IndiceVisibilidad..");
                    //Aquí añado las medias calculadas a cada objeto
                    for (IndiceVisibilidad value : values) {
                        if (value.getPosicionGMB().equals(999999999)) {
                            value.setPosicionGMB(null);
                        }
                        if (value.getKeywordTop10().equals(999999999)) {
                            value.setKeywordTop10(null);
                        }
                        value.setMediaPosiciónGMB(mediaPosicionGMB);
                        value.setMediaKeywordTop10(mediaKeywordTop10);
                        totales.add(value.getKpisIndicesVisibilidad());
                    }

            }
            LOG.info("FIN-PASO 4: Escribiendo resultados en fichero INDICES_VISIBILIDAD.xlsx");
            Excel.writeKPIsAllValues(PATH, "INDICES_VISIBILIDAD.xlsx", totales, getKPIsIndiceVisibilidad());
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * Método encargado de generar los objetos JAVA IndiceVisibilidad que aglutinará toda la información anteriormente buscada
     *
     * @return mapa
     */
    private Map<String, List<IndiceVisibilidad>> getMapaSectoresAndIndiceVisibilidad(int threadNumber) {
        Map<String, List<IndiceVisibilidad>> mapa = new LinkedHashMap<>();
        try {
            LOG.info("INICIO-PASO 3: Generando los objetos IndiceVisibilidad...");
            List<String> projections = Utils.generateListFromFile(PATH, PROYECCION+(threadNumber+1)+EXTENSION);
            IndicesMapper indicesMapper = new IndicesMapper();

            for (int i = 0; i < projections.size(); i++) {
                String[] values = projections.get(i).split(",");
                IndiceVisibilidad indiceVisibilidad = indicesMapper.getIndiceVisibilidadFromProjection(new IndiceVisibilidad(), values);
                mapa = getMapaIndiceVisibilidad(mapa, indiceVisibilidad);
            }
            LOG.info("FIN-PASO 3: Se han generando los objetos IndiceVisibilidad correctamente");
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return mapa;
    }

    /**
     * Método que genera un mapa cuyas claves son los códigos de sectores y sus valores son las actividades asociadas a dichos códigos
     *
     * @param projections
     * @return
     */
    private static Map<String, List<Integer>> generateMapCoSectorAndCoActvads(List<Object[]> projections) {
        Map<String, List<Integer>> result = new LinkedHashMap<>();
        try {
            List<Integer> co_actvads = new ArrayList<>();
            Object[] primero = projections.get(0);
            co_actvads.add((Integer) primero[1]);
            for (int i = 1; i < projections.size(); i++) {
                Object[] siguiente = projections.get(i);
                if (primero[0].equals(siguiente[0])) {
                    co_actvads.add((Integer) siguiente[1]);
                } else {
                    result.put((String) primero[0], co_actvads);
                    primero = siguiente;
                    co_actvads = new ArrayList<>();
                    co_actvads.add((Integer) primero[1]);
                }
            }
        } catch (Exception e) {
            LOG.error(SisoraMessage.GENERIC_ERROR + ": " + e.getMessage());
        }
        return result;
    }

    /**
     * Método que genera un mapa cuyas claves son los códigos de los sectores y sus valores son una lista de Indices de visibilidad
     *
     * @param mapa
     * @param indiceVisibilidad
     * @return mapa
     */
    private Map<String, List<IndiceVisibilidad>> getMapaIndiceVisibilidad(Map<String, List<IndiceVisibilidad>> mapa, IndiceVisibilidad indiceVisibilidad) {
        List<IndiceVisibilidad> values = mapa.get(indiceVisibilidad.getCo_sector());
        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(indiceVisibilidad);
        mapa.put(indiceVisibilidad.getCo_sector(), values);
        return mapa;
    }

    /**
     * Método que genera un listado de los KPIs que serán la cabecera en el fichero xlsx resultante
     *
     * @return
     */
    private List<String> getKPIsIndiceVisibilidad() {
        return Arrays.asList("CO_CLIENTE", "CO_SECTOR", "CO_ACTVAD",
                "KEYWORDS_TOP10", "POSICION_GMB", "VISIBILIDAD",
                "MEDIA_KEYWORDS_TOP10_BY_SECTOR", "MEDIA_POSICION_GMB_BY_SECTOR");
    }
}
