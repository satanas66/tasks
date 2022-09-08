package mercado.candidato.application;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import com.mongodb.DBCollection;
import jpa.ConnectionJpa;
import mercado.candidato.domain.business.Visitas;
import mercado.candidato.jpa.gestford.Tsf_AccountProjection;
import mercado.candidato.jpa.gestford.Tsf_Csord__Service__CProjection;
import mercado.candidato.jpa.gestford.Tsf_TaskProjection;
import mercado.candidato.jpa.pa.*;
import mercado.candidato.jpa.phw_vac.Estacionalidad_ActvProjection;
import mercado.candidato.jpa.phw_vac.F_Datos_ContactoProjection;
import mercado.candidato.jpa.phw_vac.F_ImpagoProjection;
import mercado.candidato.jpa.phw_vac.Gestor_OportunidadesProjection;
import mercado.candidato.jpa.sisora.Tsi_ActvadProjection;
import mercado.candidato.jpa.sisora.Tsi_EmprvcaractProjection;
import mercado.candidato.jpa.sisora.Tsi_F_WebProjection;
import mercado.candidato.jpa.sisora.Tsi_SectorProjection;
import mercado.candidato.mongo.biRecomendador.Bi_RecomendatorProjection;
import mercado.candidato.mongo.kpisActividad.Kpis_ActividadProjection;
import mercado.candidato.mongo.kpisCalculados.Kpis_CalculadosProjection;
import mercado.candidato.mongo.kpisPrCancDaily.Kpis_Pr_Canc_DailyProjection;
import mercado.candidato.mongo.webVisibilityAnalytics.WebVisibilityAnalyticsProjection;
import mongo.ConnectionMongo;

import javax.persistence.EntityManager;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MercadoCandidato {

    private static Logger LOG = Logger.getLogger(MercadoCandidato.class);

    protected String PATH = "C:/tasks/mercado-candidato/src/main/resources/";

    protected  String FILENAME_MERCADO_CANDIDATO = "MERCADO_CANDIDATO_";

    protected String EXTENSION = ".xlsx";

    protected String FILENAME_BIPA = "clientCodes_bipa.txt";

    protected String FILENAME_EMAIL_HUNTER = "email_hunter.csv";

    private String FILENAME_ACTIVIDADES_EXCLUIR = "actividades_a_excluir.txt";

    private String FILENAME_VISITAS = "ultima_visita.csv";

    private boolean continuaProceso = true;

    private Map<String, String> mapaEmailHunter;

    private List<String> actividadesExcluir;

    private Map<String, Visitas> mapaVisitas;

    private ConnectionJpa connectionJpa;

    private EntityManager entityManagerPHWVAC;

    private EntityManager entityManagerSISORA;

    private EntityManager entityManagerGESTFORD;

    private EntityManager entityManagerPA;

    private ConnectionMongo connectionMongo;

    private DBCollection scrappingDbCollection;

    private DBCollection webVisibilityAnalyticsDbCollection;

    private DBCollection bi_RecomendatorCollection;

    private DBCollection kpis_ActividadDbCollection;

    private DBCollection kpis_CalculadosDbCollection;

    private DBCollection kpis_Pr_Can_DailyCollection;

    private F_Datos_ContactoProjection f_datos_contactoProjection;

    private F_ImpagoProjection f_impagoProjection;

    private Gestor_OportunidadesProjection gestor_oportunidadesProjection;

    private Estacionalidad_ActvProjection estacionalidad_actvProjection;

    private Tsf_AccountProjection tsf_accountProjection;

    private Tsf_TaskProjection tsf_taskProjection;

    private Tsf_Csord__Service__CProjection tsf_csord__service__cProjection;

    private Tsi_ActvadProjection tsi_actvadProjection;

    private Tsi_EmprvcaractProjection tsi_emprvcaractProjection;

    private Tsi_F_WebProjection tsi_f_webProjection;

    private Tsi_SectorProjection tsi_sectorProjection;

    private Tdv_Oportunidades_Por_ClienteProjection tdv_oportunidades_por_clienteProjection;

    private Tdv_Tramo_Historico_AlianzaProjection tdv_tramo_historico_alianzaProjection;

    private Thv_Actividad_Comercial_ConsProjection thv_actividad_comercial_consProjection;

    private Vhv_Cuotas_Mes_V2Projection vhv_cuotas_mes_v2Projection;

    private Thv_Actividad_Comercial_Cons_V2Projection thv_actividad_comercial_cons_v2Projection;

    private Vdv_Asignacion_Cuenta_EcProjection vdv_asignacion_cuenta_ecProjection;

    private Tdv_UsuarioProjection tdv_usuarioProjection;

    private Tdp_Yext_PublicacionProjection tdp_yext_publicacionProjection;

    private WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection;

    private Bi_RecomendatorProjection bi_recomendatorProjection;

    private Kpis_ActividadProjection kpis_ActividadProjection;

    private Kpis_CalculadosProjection kpis_calculadosProjection;

    private Kpis_Pr_Canc_DailyProjection kpis_pr_canc_dailyProjection;

    public MercadoCandidato(){
        relationalDBStart();
        noRelationalDBStart();
        initPhysycalResource();
        initOracleProjections();
        initMySQLProjection();
        initMongoProjections();
        generateResources();
    }

    /**
     * Método que establece las conexiones a las Bases de datos realcionales Oracle y MySql
     */
    public void relationalDBStart() {
        if (continuaProceso) {
            LOG.info("Abriendo conexiones a Oracle y MySql");
            connectionJpa = new ConnectionJpa();
            entityManagerPHWVAC = connectionJpa.getPHWVACEntityManager();
            entityManagerSISORA = connectionJpa.getSisoraEntityManager();
            entityManagerPA = connectionJpa.getPAEntityManager();
            entityManagerGESTFORD = connectionJpa.getGestfordEntityManager();

            if (entityManagerPHWVAC == null || !entityManagerPHWVAC.isOpen() ||
                    entityManagerSISORA == null || !entityManagerSISORA.isOpen() ||
                    entityManagerGESTFORD == null || !entityManagerGESTFORD.isOpen() ||
                    entityManagerPA == null || !entityManagerPA.isOpen()) {
                continuaProceso = false;
            }
            LOG.info("Conexiones establecidas con éxito a las Bases de Datos Relacionales");
        }
    }

    /**
     * Método que establece la conexión a la Base de datos MONGO
     */
    public void noRelationalDBStart() {
        if (continuaProceso) {
            LOG.info("Abriendo conexión a MONGODB");
            connectionMongo = new ConnectionMongo("PRO");
            if (connectionMongo.getMongoClient() != null) {
                scrappingDbCollection = connectionMongo.getDBCollection("scrapping");
                webVisibilityAnalyticsDbCollection = connectionMongo.getDBCollection("webVisibilityAnalytics");
                bi_RecomendatorCollection = connectionMongo.getDBCollection("bi_Recomendador");
                kpis_ActividadDbCollection = connectionMongo.getDBCollection("kpis_actividad");
                kpis_CalculadosDbCollection = connectionMongo.getDBCollection("kpis_calculados");
                kpis_Pr_Can_DailyCollection = connectionMongo.getDBCollection("kpis_pr_canc_daily");
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
                    LOG.info("Leyendo fichero " + FILENAME_VISITAS + " para la construcción del Mapa de Visitas");
                    generateResourceVists();
                    if(mapaVisitas.size() > 0){
                        continuaProceso = true;
                    }
                }
            }
        }
    }

    /**
     * Método que instancia las clases de proyección para recoger devolver todos los
     * valores asociados a los KPIs desde OracleDB y MySql
     */
    public void initOracleProjections() {
        LOG.info("Instanciado clases projection para realizar las consultas a OracleDB");
        f_datos_contactoProjection = new F_Datos_ContactoProjection(entityManagerPHWVAC);
        f_impagoProjection = new F_ImpagoProjection(entityManagerPHWVAC);
        gestor_oportunidadesProjection = new Gestor_OportunidadesProjection(entityManagerPHWVAC);

        tsf_accountProjection = new Tsf_AccountProjection(entityManagerGESTFORD);
        tsf_taskProjection = new Tsf_TaskProjection(entityManagerGESTFORD);
        tsf_csord__service__cProjection = new Tsf_Csord__Service__CProjection(entityManagerGESTFORD);

        tsi_actvadProjection = new Tsi_ActvadProjection(entityManagerSISORA);
        tsi_emprvcaractProjection = new Tsi_EmprvcaractProjection(entityManagerSISORA);
        tsi_f_webProjection = new Tsi_F_WebProjection(entityManagerSISORA);
        tsi_sectorProjection = new Tsi_SectorProjection(entityManagerSISORA);

        estacionalidad_actvProjection = new Estacionalidad_ActvProjection(entityManagerPHWVAC);
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

        thv_actividad_comercial_cons_v2Projection = new Thv_Actividad_Comercial_Cons_V2Projection(entityManagerPA);
        vdv_asignacion_cuenta_ecProjection = new Vdv_Asignacion_Cuenta_EcProjection(entityManagerPA);
        tdv_usuarioProjection = new Tdv_UsuarioProjection(entityManagerPA);
        tdp_yext_publicacionProjection = new Tdp_Yext_PublicacionProjection(entityManagerPA);
    }

    /**
     * Método que instancia las colecciones para recoger, evaluar y devolver todos los
     * valores asociados a los KPIs desde MongoDB
     */
    public void initMongoProjections() {
        LOG.info("Instanciado clases projection para realizar las consultas a MongoDB");
        webVisibilityAnalyticsProjection = new WebVisibilityAnalyticsProjection(webVisibilityAnalyticsDbCollection);
        bi_recomendatorProjection = new Bi_RecomendatorProjection(bi_RecomendatorCollection);
        kpis_ActividadProjection = new Kpis_ActividadProjection(kpis_ActividadDbCollection);
        kpis_calculadosProjection = new Kpis_CalculadosProjection(kpis_CalculadosDbCollection);
        kpis_pr_canc_dailyProjection = new Kpis_Pr_Canc_DailyProjection(kpis_Pr_Can_DailyCollection);
    }

    public Map<String, String> getMapaEmailHunter() {
        return mapaEmailHunter;
    }

    public List<String> getActividadesExcluir() {
        return actividadesExcluir;
    }

    public Map<String, Visitas> getMapaVisitas() {
        return mapaVisitas;
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

    public Tsf_Csord__Service__CProjection getTsf_csord__service__cProjection() {
        return tsf_csord__service__cProjection;
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

    public Estacionalidad_ActvProjection getEstacionalidad_actvProjection() {
        return estacionalidad_actvProjection;
    }

    public Thv_Actividad_Comercial_Cons_V2Projection getThv_actividad_comercial_cons_v2Projection() {
        return thv_actividad_comercial_cons_v2Projection;
    }

    public Vdv_Asignacion_Cuenta_EcProjection getVdv_asignacion_cuenta_ecProjection() {
        return vdv_asignacion_cuenta_ecProjection;
    }

    public Tdv_UsuarioProjection getTdv_usuarioProjection() {
        return tdv_usuarioProjection;
    }

    public Tdp_Yext_PublicacionProjection getTdp_yext_publicacionProjection() {
        return tdp_yext_publicacionProjection;
    }

    public WebVisibilityAnalyticsProjection getWebVisibilityAnalyticsProjection() {
        return webVisibilityAnalyticsProjection;
    }

    public Bi_RecomendatorProjection getBi_recomendatorProjection() {
        return bi_recomendatorProjection;
    }

    public Kpis_ActividadProjection getKpis_ActividadProjection() {
        return kpis_ActividadProjection;
    }

    public Kpis_CalculadosProjection getKpis_calculadosProjection() {
        return kpis_calculadosProjection;
    }

    public Kpis_Pr_Canc_DailyProjection getKpis_pr_canc_dailyProjection() {
        return kpis_pr_canc_dailyProjection;
    }

    public boolean isContinuaProceso() {
        return continuaProceso;
    }

    /**
     * Método que genera los recursos iniciales para la construcción del fichero madre
     */
    public void generateResources() {
        File file = new File(PATH + FILENAME_BIPA);
        if (!file.exists()) {
            LOG.info("Buscando los códigos de los clientes (clientCodes) con origen octoparse y scrappingHub...");
            List<String> clientCodes = vhv_cuotas_mes_v2Projection.findAllAliveClientCodes();
            LOG.info("Fin de la búsqueda de los códigos de los clientes (clientCodes) con origen octoparse y scrappingHub");
            LOG.info("Escribiendo resultados...");
            continuaProceso = Text.generateTxtFile(clientCodes, PATH, FILENAME_BIPA);
            LOG.info("Los resultados han sido escritos en " + PATH + FILENAME_BIPA);
        }
    }

    public List<List<Integer>> listsForExecutionByThreads(int numberOfDivisions, String path, String nameFile) {
        List<Integer> clientCodes = Utils.generateIntegerListFromFile(path, nameFile);
        return Utils.getListDivision(clientCodes, numberOfDivisions);
    }

    /**
     * Método que genera un mapa con objetos JAVA para la recopilación de la información de visitas a los negocios del cliente
     */
    private void generateResourceVists(){
        List<String> visitas = Utils.generateListFromFile(PATH, FILENAME_VISITAS);
        List<Integer> meses = Utils.getCurrentMonthsAndPreviousMonth(6);
        mapaVisitas = new HashMap<>();
        for(String visita : visitas){
            String[] array = visita.split(",");
            if(meses.contains(Integer.parseInt(array[1]))){
                Visitas physicalResources = (mapaVisitas.keySet().contains(array[0]))?mapaVisitas.get(array[0]):new Visitas();
                physicalResources.setLastVisitBusinessArea(Integer.parseInt(array[1]));
                physicalResources.setNumberVisitsLastSixMonths(Integer.parseInt(array[2]));
                mapaVisitas.put(array[0], physicalResources);
            }
        }
    }
}
