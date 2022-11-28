package indices.application;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import com.mongodb.BasicDBList;
import com.mongodb.DBCollection;
import indices.jpa.phw_vac.F_Datos_ContactoProjection;
import indices.mongo.webVisibilityAnalytics.WebVisibilityAnalyticsProjection;
import indices.mongo.webVisibilityKpisMapping.WebVisibilityKpisMappingProjection;
import jpa.ConnectionJpa;
import mongo.ConnectionMongo;

import javax.persistence.EntityManager;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DigitalPresenceIndex {

    private static Logger LOG = Logger.getLogger(DigitalPresenceIndex.class);

    private ConnectionMongo connectionMongo;

    private DBCollection webVisibilityAnalyticsDbCollection;

    private DBCollection webVisibilityKpisMappingDbCollection;

    private WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection;

    private WebVisibilityKpisMappingProjection webVisibilityKpisMappingProjection;

    private ConnectionJpa connectionJpa;

    private EntityManager entityManagerPHWVAC;

    private F_Datos_ContactoProjection f_datos_contactoProjection;

    public DigitalPresenceIndex() {
        noRelationalDBStart();
        relationalDBStart();
        initMongoProjections();
        initOracleProjections();
    }

    /**
     * Método que establece la conexión a la Base de datos MONGO
     */
    public void noRelationalDBStart() {
        LOG.info("Abriendo conexiones a MONGODB");
        connectionMongo = new ConnectionMongo();
        if (connectionMongo.getMongoClient() != null) {
            webVisibilityAnalyticsDbCollection = connectionMongo.getDBCollection("webVisibilityAnalytics");
            webVisibilityKpisMappingDbCollection = connectionMongo.getDBCollection("webVisibilityKPIsMapping");
            LOG.info("Conexión establecida con MONGODB");
        }
    }

    /**
     * Método que establece las conexiones a las Bases de datos realcionales Oracle y MySql
     */
    public void relationalDBStart() {
        LOG.info("Abriendo conexiones a Oracle...");
        connectionJpa = new ConnectionJpa();
        entityManagerPHWVAC = connectionJpa.getPHWVACEntityManager();
        if (entityManagerPHWVAC != null && entityManagerPHWVAC.isOpen()) {
            LOG.info("Conexión establecida correctamente.");
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
        LOG.info("Las conexiones a MongoDB han sido cerradas");
    }

    public void initMongoProjections() {
        LOG.info("Instanciado clases projection para realizar las consultas a MongoDB");
        webVisibilityAnalyticsProjection = new WebVisibilityAnalyticsProjection(webVisibilityAnalyticsDbCollection);
        webVisibilityKpisMappingProjection = new WebVisibilityKpisMappingProjection(webVisibilityKpisMappingDbCollection);
    }

    /**
     * Método que instancia las clases de proyección para recoger devolver todos los
     * valores asociados a los KPIs desde OracleDB y MySql
     */
    public void initOracleProjections() {
        LOG.info("Instanciado clases projection para realizar las consultas a OracleDB");
        f_datos_contactoProjection = new F_Datos_ContactoProjection(entityManagerPHWVAC);
    }


    public Map<String, String> findAllDigitalPresenceIndex() {
        BasicDBList kpiseg400 = webVisibilityKpisMappingProjection.findDependentIndicesOnKPISEG("KPISEG400");
        BasicDBList kpiseg401 = webVisibilityKpisMappingProjection.findDependentIndicesOnKPISEG("KPISEG401");
//        return webVisibilityAnalyticsProjection.findAllDigitalPresenceIndexToAllClientCodesWithUrlTrue(kpiseg400, kpiseg401);
        return null;
    }


    public List<String> getAllValuesToDigitalPresenceIndex(Map<String, String> indexMap) {
        List<String> result = new ArrayList<>();
        String head = "TX_LOCA_APA;DE_PROV;CO_CCAA;TX_CCAA;CO_ACTVAD_PRAL;TX_ACTVAD;" +
                "CO_CLIENTE;INDICE_PRESENCIA;CLAIMBUSINESS;REVIEWS;PROBABILIDAD_PAGINA1;RANKING_NUMBER;KEYWORD_TOP_10;ERRORES_GRAVES;" +
                "KPISEG05;KPISEG07;KPISEG08;KPISEG09;KPISEG10;KPISEG11;KPISEG12;KPISEG13;KPISEG15;KPISEG16;KPISEG400_recalculado";
        result.add(head);
        int i=1;
        for (String clienteCode : indexMap.keySet()) {
            String projection = f_datos_contactoProjection.getProjectionFromF_Datos_ContactoToMuestraMadreByClientCode(Integer.parseInt(clienteCode));
            if(Utils.evaluateString(projection)){
                projection=projection+";"+indexMap.get(clienteCode);
            }else{
                projection=";;;;;;"+indexMap.get(clienteCode);
            }
            result.add(projection);
            LOG.info("Registro completado "+i);
            i++;
        }
        return result;
    }

    public Map<String, String> findOneToOneDigitalPresenceIndex() {
        BasicDBList kpiseg400 = webVisibilityKpisMappingProjection.findDependentIndicesOnKPISEG("KPISEG400");
        BasicDBList kpiseg401 = webVisibilityKpisMappingProjection.findDependentIndicesOnKPISEG("KPISEG401");
        String path = "C:/tasks/indices/src/main/resources/";
        File file = new File(path+"clientes_con_analitica_web.txt");
        List<String> clientCodes;
        if(!file.exists()){
            clientCodes = webVisibilityAnalyticsProjection.findAllClientCodesWithUrlTrue();
            Text.generateTxtFileWithStrings(clientCodes, path, "clientes_con_analitica_web.txt");
        }else{
            clientCodes = Utils.generateListFromFile(path, "clientes_con_analitica_web.txt");
        }
        Map<String, String> mapa = new HashMap<>();
        for(String clientCode : clientCodes){
            String value = webVisibilityAnalyticsProjection.findDigitalPresenceIndexToClientCodeWithUrlTrue(clientCode, kpiseg400, kpiseg401);
            mapa.put(clientCode, value);
        }
        return mapa;
    }

    public String findDomainByClientCode(String clientCode){
        return webVisibilityAnalyticsProjection.findDomainByClientCode(clientCode);
    }
}
