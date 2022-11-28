package mercado.potencial.application;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import com.mongodb.BasicDBList;
import com.mongodb.DBCollection;
import mercado.potencial.mongo.biRecomendador.Bi_RecomendatorProjection;
import mercado.potencial.mongo.webVisibilityAnalytics.WebVisibilityAnalyticsProjection;
import mercado.potencial.mongo.webVisibilityKpisMapping.WebVisibilityKpisMappingProjection;
import mongo.ConnectionMongo;

import java.io.File;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 */
public class MercadoPotencial {

    private static Logger LOG = Logger.getLogger(MercadoPotencial.class);

    protected String PATH = "C:/tasks/mercado-potencial-mongo/src/main/resources/";

    private boolean continuaProceso = true;

    private ConnectionMongo connectionMongo;

    private DBCollection webVisibilityAnalyticsDbCollection;

    private DBCollection bi_RecomendatorCollection;

    private DBCollection webVisibilityKpisMappingDbCollection;

    private WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection;

    private Bi_RecomendatorProjection bi_recomendatorProjection;

    private BasicDBList kpiseg400;

    private BasicDBList kpiseg401;

    public MercadoPotencial() {
        noRelationalDBStart();
        initMongoProjections();
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
        LOG.info("Las conexiones a OracleDB, MySQL y MongoDB han sido cerradas");
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


    public void generateFileListsToExecution(int numberOfDivisions) {
        List<Integer> allClientCodes = Utils.generateIntegerListFromFile(PATH, "clientCodes.txt");
//        List<Integer> clientCodesFound = Utils.generateIntegerListFromFile(PATH, "clientCodesFound.txt");
//        List<Integer> disjuntion = Utils.getDisjunctionFromLists(allClientCodes, clientCodesFound);
        List<List<Integer>> division = Utils.getListDivision(allClientCodes, numberOfDivisions);
        for (int i = 0; i < numberOfDivisions; i++) {
            String fileName = "clientCodes" + (i + 1) + ".txt";
            File file = new File(PATH + fileName);
            if (!file.exists()) {
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
