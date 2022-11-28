package mercado.potencial.application;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import com.mongodb.BasicDBList;
import com.mongodb.DBCollection;
import jpa.ConnectionJpa;
import mercado.potencial.mongo.webVisibilityKpisMapping.WebVisibilityKpisMappingProjection;
import mongo.ConnectionMongo;
import mercado.potencial.jpa.gestcli_sisora.Tcl_ClieProjection;
import mercado.potencial.jpa.gestcli_sisora.Tipcl_Camp_RespuestaProjection;
import mercado.potencial.jpa.gestford.Tsf_AccountProjection;
import mercado.potencial.jpa.gestford.Tsf_TaskProjection;
import mercado.potencial.jpa.pa.Tdv_Oportunidades_Por_ClienteProjection;
import mercado.potencial.jpa.pa.Tdv_Tramo_Historico_AlianzaProjection;
import mercado.potencial.jpa.pa.Thv_Actividad_Comercial_ConsProjection;
import mercado.potencial.jpa.pa.Vhv_Cuotas_Mes_V2Projection;
import mercado.potencial.jpa.phw_vac.F_Datos_ContactoProjection;
import mercado.potencial.jpa.phw_vac.F_ImpagoProjection;
import mercado.potencial.jpa.phw_vac.Gestor_OportunidadesProjection;
import mercado.potencial.jpa.sisora.Tsi_ActvadProjection;
import mercado.potencial.jpa.sisora.Tsi_EmprvcaractProjection;
import mercado.potencial.jpa.sisora.Tsi_F_WebProjection;
import mercado.potencial.jpa.sisora.Tsi_SectorProjection;
import mercado.potencial.mongo.biRecomendador.Bi_RecomendatorProjection;
import mercado.potencial.mongo.webVisibilityAnalytics.WebVisibilityAnalyticsProjection;

import javax.persistence.EntityManager;
import java.io.File;
import java.util.*;

/**
 * @author Edwin Patricio Arévalo Angulo
 */
public class MercadoPotencial {

    private static Logger LOG = Logger.getLogger(MercadoPotencial.class);

    protected String PATH = "C:/tasks/mercado-potencial/src/main/resources/";

    protected String FILENAME_MERCADO_POTENCIAL = "MERCADO_POTENCIAL_";

    protected String EXTENSION = ".xlsx";

    private String FILENAME_EMAIL_HUNTER = "email_hunter.csv";

    private String FILENAME_ACTIVIDADES_EXCLUIR = "actividades_a_excluir.txt";

    private boolean continuaProceso = true;

    private Map<Integer, Object[]> mapaAuditoria;

    private Map<String, String> mapaEmailHunter;

    private List<String> actividadesExcluir;

    private ConnectionMongo connectionMongo;

    private DBCollection webVisibilityAnalyticsDbCollection;

    private DBCollection bi_RecomendatorCollection;

    private DBCollection webVisibilityKpisMappingDbCollection;

    private ConnectionJpa connectionJpa;

    private EntityManager entityManagerPHWVAC;

    private EntityManager entityManagerSISORA;

    private EntityManager entityManagerGESTFORD;

    private EntityManager entityManagerPA;

    private EntityManager entityManagerGESTCLISISORA;

    private F_Datos_ContactoProjection f_datos_contactoProjection;

    private F_ImpagoProjection f_impagoProjection;

    private Gestor_OportunidadesProjection gestor_oportunidadesProjection;

    private Tsf_AccountProjection tsf_accountProjection;

    private Tsf_TaskProjection tsf_taskProjection;

    private Tsi_ActvadProjection tsi_actvadProjection;

    private Tsi_EmprvcaractProjection tsi_emprvcaractProjection;

    private Tsi_F_WebProjection tsi_f_webProjection;

    private Tsi_SectorProjection tsi_sectorProjection;

    private Tdv_Oportunidades_Por_ClienteProjection tdv_oportunidades_por_clienteProjection;

    private Tdv_Tramo_Historico_AlianzaProjection tdv_tramo_historico_alianzaProjection;

    private Thv_Actividad_Comercial_ConsProjection thv_actividad_comercial_consProjection;

    private Vhv_Cuotas_Mes_V2Projection vhv_cuotas_mes_v2Projection;

    private Tcl_ClieProjection tcl_clieProjection;

    private Tipcl_Camp_RespuestaProjection tipcl_camp_respuestaProjection;

    private WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection;

    private Bi_RecomendatorProjection bi_recomendatorProjection;

    private BasicDBList kpiseg400;

    private BasicDBList kpiseg401;

    public MercadoPotencial() {
        initPhysycalResource();
        relationalDBStart();
        noRelationalDBStart();
        initOracleProjections();
        initMySQLProjection();
        initMongoProjections();
        generateResourcesForTheAudit();
    }

    /**
     * Método que establece las conexiones a las Bases de datos relacionales Oracle y MySql
     */
    public void relationalDBStart() {
        if (continuaProceso) {
            LOG.info("Abriendo conexiones a OracleDB");
            connectionJpa = new ConnectionJpa();
            entityManagerPHWVAC = connectionJpa.getPHWVACEntityManager();
            entityManagerGESTFORD = connectionJpa.getGestfordEntityManager();
            entityManagerSISORA = connectionJpa.getSisoraEntityManager();
            entityManagerPA = connectionJpa.getPAEntityManager();
            entityManagerGESTCLISISORA = connectionJpa.getGestcliSisoraEntityManager();
            if (entityManagerPHWVAC == null || !entityManagerPHWVAC.isOpen() ||
                    entityManagerSISORA == null || !entityManagerSISORA.isOpen() ||
                    entityManagerGESTFORD == null || !entityManagerGESTFORD.isOpen() ||
                    entityManagerPA == null || !entityManagerPA.isOpen() ||
                    entityManagerGESTCLISISORA == null || !entityManagerGESTCLISISORA.isOpen()) {
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
                bi_RecomendatorCollection = connectionMongo.getDBCollection("bi_Recomendador");
                webVisibilityKpisMappingDbCollection = connectionMongo.getDBCollection("webVisibilityKPIsMapping");
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
        if (entityManagerGESTFORD != null && entityManagerGESTFORD.isOpen()) {
            entityManagerGESTFORD.close();
        }
        if (entityManagerSISORA != null && entityManagerSISORA.isOpen()) {
            entityManagerSISORA.close();
        }
        if (entityManagerPA != null && entityManagerPA.isOpen()) {
            entityManagerPA.close();
        }
        if (entityManagerGESTCLISISORA != null && entityManagerGESTCLISISORA.isOpen()) {
            entityManagerGESTCLISISORA.close();
        }
        LOG.info("Las conexiones a OracleDB, MySQL y MongoDB han sido cerradas");
    }

    /**
     * Método que instancia los recursos físicos para el email hunter y las actividades a excluir
     */
    public void initPhysycalResource() {
        if (continuaProceso) {
            LOG.info("Leyendo fichero " + FILENAME_EMAIL_HUNTER + " para la construcción del mapa HUNTER");
            continuaProceso = false;
            mapaEmailHunter = Utils.generateMapFromFile(PATH, FILENAME_EMAIL_HUNTER);
            if (mapaEmailHunter.size() > 0) {
                LOG.info("Leyendo fichero " + FILENAME_ACTIVIDADES_EXCLUIR + " para la construcción de la lista de actividades a excluir");
                actividadesExcluir = Utils.generateListFromFile(PATH, FILENAME_ACTIVIDADES_EXCLUIR);
                if (actividadesExcluir.size() > 0) {
                    continuaProceso = true;
                }
            }
        }
    }

    /**
     * Método que instancia las clases de proyección para recoger devolver todos los
     * valores asociados a los KPIs desde OracleDB
     */
    public void initOracleProjections() {
        LOG.info("Instanciado clases projection para realizar las consultas a OracleDB");
        f_datos_contactoProjection = new F_Datos_ContactoProjection(entityManagerPHWVAC);
        f_impagoProjection = new F_ImpagoProjection(entityManagerPHWVAC);
        gestor_oportunidadesProjection = new Gestor_OportunidadesProjection(entityManagerPHWVAC);

        tsf_accountProjection = new Tsf_AccountProjection(entityManagerGESTFORD);
        tsf_taskProjection = new Tsf_TaskProjection(entityManagerGESTFORD);

        tsi_actvadProjection = new Tsi_ActvadProjection(entityManagerSISORA);
        tsi_emprvcaractProjection = new Tsi_EmprvcaractProjection(entityManagerSISORA);
        tsi_f_webProjection = new Tsi_F_WebProjection(entityManagerSISORA);
        tsi_sectorProjection = new Tsi_SectorProjection(entityManagerSISORA);

        tcl_clieProjection = new Tcl_ClieProjection(entityManagerGESTCLISISORA);
        tipcl_camp_respuestaProjection = new Tipcl_Camp_RespuestaProjection(entityManagerGESTCLISISORA);
    }

    /**
     * Método que instancia las clases de proyección para recoger y devolver todos los
     * valores asociados a los KPIs desde MySQL
     */
    public void initMySQLProjection() {
        LOG.info("Instanciado clases projection para realizar las consultas a MySQL");
        tdv_oportunidades_por_clienteProjection = new Tdv_Oportunidades_Por_ClienteProjection(entityManagerPA);
        tdv_tramo_historico_alianzaProjection = new Tdv_Tramo_Historico_AlianzaProjection(entityManagerPA);
        thv_actividad_comercial_consProjection = new Thv_Actividad_Comercial_ConsProjection(entityManagerPA);
        vhv_cuotas_mes_v2Projection = new Vhv_Cuotas_Mes_V2Projection(entityManagerPA);
    }

    /**
     * Método que instancia las colecciones para recoger, evaluar y devolver todos los
     * valores asociados a los KPIs desde MongoDB
     */
    public void initMongoProjections() {
        LOG.info("Instanciado clases projection para realizar las consultas a MongoDB");
        webVisibilityAnalyticsProjection = new WebVisibilityAnalyticsProjection(webVisibilityAnalyticsDbCollection);
        bi_recomendatorProjection = new Bi_RecomendatorProjection(bi_RecomendatorCollection);
        WebVisibilityKpisMappingProjection webVisibilityKpisMappingProjection = new WebVisibilityKpisMappingProjection(webVisibilityKpisMappingDbCollection);
        if (webVisibilityKpisMappingProjection != null) {
            this.kpiseg400 = webVisibilityKpisMappingProjection.findDependentIndicesOnKPISEG("KPISEG400");
            this.kpiseg401 = webVisibilityKpisMappingProjection.findDependentIndicesOnKPISEG("KPISEG401");
        }
    }

    public F_Datos_ContactoProjection getF_datos_contactoProjection() {
        return f_datos_contactoProjection;
    }

    public F_ImpagoProjection getF_impagoProjection() {
        return f_impagoProjection;
    }

    public Gestor_OportunidadesProjection getGestor_oportunidadesProjection() {
        return gestor_oportunidadesProjection;
    }

    public Tsf_AccountProjection getTsf_accountProjection() {
        return tsf_accountProjection;
    }

    public Tsf_TaskProjection getTsf_taskProjection() {
        return tsf_taskProjection;
    }

    public Tsi_ActvadProjection getTsi_actvadProjection() {
        return tsi_actvadProjection;
    }

    public Tsi_EmprvcaractProjection getTsi_emprvcaractProjection() {
        return tsi_emprvcaractProjection;
    }

    public Tsi_F_WebProjection getTsi_f_webProjection() {
        return tsi_f_webProjection;
    }

    public Tsi_SectorProjection getTsi_sectorProjection() {
        return tsi_sectorProjection;
    }

    public Tdv_Oportunidades_Por_ClienteProjection getTdv_oportunidades_por_clienteProjection() {
        return tdv_oportunidades_por_clienteProjection;
    }

    public Tdv_Tramo_Historico_AlianzaProjection getTdv_tramo_historico_alianzaProjection() {
        return tdv_tramo_historico_alianzaProjection;
    }

    public Thv_Actividad_Comercial_ConsProjection getThv_actividad_comercial_consProjection() {
        return thv_actividad_comercial_consProjection;
    }

    public Vhv_Cuotas_Mes_V2Projection getVhv_cuotas_mes_v2Projection() {
        return vhv_cuotas_mes_v2Projection;
    }

    public WebVisibilityAnalyticsProjection getWebVisibilityAnalyticsProjection() {
        return webVisibilityAnalyticsProjection;
    }

    public Bi_RecomendatorProjection getBi_recomendatorProjection() {
        return bi_recomendatorProjection;
    }

    public BasicDBList getKpiseg400() {
        return kpiseg400;
    }

    public BasicDBList getKpiseg401() {
        return kpiseg401;
    }

    public boolean isContinuaProceso() {
        return continuaProceso;
    }

    public Map<Integer, Object[]> getMapaAuditoria() {
        return mapaAuditoria;
    }

    public Map<String, String> getMapaEmailHunter() {
        return mapaEmailHunter;
    }

    public List<String> getActividadesExcluir() {
        return actividadesExcluir;
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
        AuditoriaProjection auditoriaProjection = new AuditoriaProjection(PATH);
        auditoriaProjection.initAuditProjections(tcl_clieProjection, tipcl_camp_respuestaProjection);
        auditoriaProjection.generateResourcesForTheAudit();
        mapaAuditoria = auditoriaProjection.getMapaAuditoria();
    }

    /**
     * Método que genera una Lista de lista de códigos de clientes
     *
     * @param numberOfDivisions
     * @return lista de lista de códigos
     */
    public List<List<Integer>> listsForExecutionByThreads(int numberOfDivisions) {
        List<Integer> clientCodes = new ArrayList<>(mapaAuditoria.keySet());
        File file = new File(PATH + "mercado-potencial/found_client_codes.txt");
        if (file.exists()) {
            List<Integer> clientCodesFound = Utils.generateIntegerListFromFile(PATH, "mercado-potencial/found_client_codes.txt");
            List<Integer> disjunction = Utils.getDisjunctionFromLists(clientCodes, clientCodesFound);
            return Utils.getListDivision(disjunction, numberOfDivisions);
        }
        return Utils.getListDivision(clientCodes, numberOfDivisions);
    }

    public void generateFileListsToExecution(int numberOfDivisions) {
        List<Integer> allClientCodes = new ArrayList<>(mapaAuditoria.keySet());
       List<List<Integer>> division = Utils.getListDivision(allClientCodes, numberOfDivisions);
        for(int i=0; i<numberOfDivisions; i++){
            String fileName = "clientCodes"+(i+1)+".txt";
            File file = new File(PATH+fileName);
            if(!file.exists()){
                Text.generateTxtFileWithIntegers(division.get(i), PATH, fileName);
            }
        }
    }

    /**
     * Método que genera una Lista de lista de códigos de clientes
     *
     * @return lista de lista de códigos
     */
    public List<List<Integer>> getClientCodes(String fileName) {
        List<Integer> result = Utils.generateIntegerListFromFile(PATH, fileName);
        return Utils.getListDivision(result, 1);

    }
}
