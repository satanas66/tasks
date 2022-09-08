package recomendador.paquetes.application;

import automation.factory.Logger;
import automation.factory.Utils;
import com.mongodb.DBCollection;
import mongo.ConnectionMongo;
import recomendador.paquetes.mongo.webVisibilityAnalytics.WebVisibilityAnalyticsProjection;

import java.util.Collections;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 */
public class Recomendador {

    private static Logger LOG = Logger.getLogger(Recomendador.class);

    protected String PATH = "C:/tasks/recomendador-paquetes/src/main/resources/";

    protected  String FILENAME_RECOMENDADOR_PAQUETES = "RECOMENDADOR_PAQUETES";

    protected String EXTENSION = ".csv";

    private ConnectionMongo connectionMongo;

    private DBCollection webVisibilityAnalyticsDbCollection;

    private WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection;

    private boolean continuaProceso = true;

    public Recomendador(){
        noRelationalDBStart();
        initMongoProjections();
    }

    /**
     * Método que establece la conexión a la Base de datos MONGO
     */
    public void noRelationalDBStart() {
        if (continuaProceso) {
            LOG.info("Abriendo conexión a MONGODB");
            connectionMongo = new ConnectionMongo("PRO");
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
        LOG.info("Cerrando conexión a MongoDB");
        if (connectionMongo != null) {
            connectionMongo.endConnection();
        }
        LOG.info("Las conexión a MongoDB ha sido cerrada");
    }

    /**
     * Método que instancia las colecciones para recoger, evaluar y devolver todos los
     * valores asociados a los KPIs desde MongoDB
     */
    public void initMongoProjections() {
        LOG.info("Instanciado clases projection para realizar las consultas a MongoDB");
        webVisibilityAnalyticsProjection = new WebVisibilityAnalyticsProjection(webVisibilityAnalyticsDbCollection);
    }

    public boolean isContinuaProceso() {
        return continuaProceso;
    }

    public WebVisibilityAnalyticsProjection getWebVisibilityAnalyticsProjection() {
        return webVisibilityAnalyticsProjection;
    }

    /**
     * Método que genera una lista de lista de códigos de clientes
     * @param numberOfDivisions
     * @return lista de lista de códigos de clientes
     */
    public List<List<String>> listsForExecutionByThreads(int numberOfDivisions) {
        List<String> first = Utils.generateListFromFile(PATH, "20220603.txt");
        List<String> second = Utils.generateListFromFile(PATH, "20220721.txt");
        List<String> clientCodes = Utils.getDisjunctionFromLists(first, second);
        Collections.sort(clientCodes);
        return Utils.getListDivision(clientCodes, numberOfDivisions);
    }
}
