package recurrent.infraestructure;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import com.mongodb.DBCollection;
import mongo.ConnectionMongo;
import recurrent.mongo.WebVisibilityAnalyticsProjection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExecuteWebVisibilityAnalytics {

    private static Logger LOG = Logger.getLogger(ExecuteWebVisibilityAnalytics.class);

    private static final String PATH = "C:/tasks/recurrent/src/main/resources/";

    private static ConnectionMongo connectionMongo;

    private static DBCollection webVisibilityAnalyticsDbCollection;

    private static WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection;

    public static void main(String[] arg) {
        setup();
        execute4();
    }

    public static void setup() {
        connectionMongo = new ConnectionMongo();
        connectionMongo.setMongoClientReplica();
        webVisibilityAnalyticsDbCollection = connectionMongo.getDBCollection("webVisibilityAnalytics");
        webVisibilityAnalyticsProjection = new WebVisibilityAnalyticsProjection(webVisibilityAnalyticsDbCollection);
    }

    public static void execute1() {
        List<String> errores = new ArrayList<>();
        Map<String, String> mapa = Utils.generateMapFromFile(PATH, "clientCodesAndCompetenciaIndex_1.csv", ";");
        for (String identifier : mapa.keySet()) {
            if (Utils.isNumericString(identifier)) {
                LOG.info("Actualizando KPISEG404 para el código de cliente " + identifier);
                webVisibilityAnalyticsProjection.updateKPISEG404ByIdentifier(identifier, Integer.parseInt(mapa.get(identifier)));
            } else {
                errores.add(identifier + ";" + mapa.get(identifier));
            }
        }
        Text.generateTxtFileWithStrings(errores, PATH, "NO_ACTUALIZADOS.csv");
        connectionMongo.endConnection();
    }

    public static void execute2() {
        Map<String, String> mapa = Utils.generateMapFromFile(PATH, "KPISEG400_actualizar.csv", ";");
        for (String identifier : mapa.keySet()) {
            LOG.info("Actualizando KPISEG400 para el código de cliente " + identifier);
            webVisibilityAnalyticsProjection.updateKPISEG400ByIdentifier(identifier, (mapa.get(identifier)));
        }
        connectionMongo.endConnection();
    }

    public static void execute3() {
        List<String> values = webVisibilityAnalyticsProjection.findAllClientCodesWithEcommerceCheckoutLink();
        Text.generateTxtFileWithStrings(values, PATH, "ECOMMERCE_CHECKOUTLINK.csv");
        connectionMongo.endConnection();
    }

    public static void execute4(){
        List<String> values = Utils.generateListFromFile(PATH+"remove/", "remove5.txt");
        for(String identifier : values){
            webVisibilityAnalyticsProjection.deleteRegisterByIdentifier(identifier);
        }
        connectionMongo.endConnection();
    }

}
