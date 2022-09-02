package urls.marketgoo.mongo.webVisibilityAnalytics;

import automation.factory.Logger;
import automation.factory.Utils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import mongo.WebVisibilityAnalyticsMessage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la colección WEBVISIBILITYANALYTICS
 */
public class WebVisibilityAnalyticsProjection {

    private static Logger LOG = Logger.getLogger(WebVisibilityAnalyticsProjection.class);

    private DBCollection dbCollection;

    private BasicDBObject query;

    private BasicDBObject projection;

    private BasicDBObject sort;

    private DBCursor dbCursor;

    public WebVisibilityAnalyticsProjection(DBCollection dbCollection) {
        this.dbCollection = dbCollection;
    }

    public void instanceProjection() {
        projection = new BasicDBObject();
        projection.put("_id", 0);
    }

    /**
     * Método que permite obtener un cursor cuya proyección devuelva únicamente los códigos de clientes (identifier)
     *
     * @return
     */
    public List<String> findAllClientCodesWithoutRepetition() {
        Set<String> values = new HashSet<>();
        try {
            instanceProjection();

            query = new BasicDBObject();

            projection.put("identifier", 1);

            dbCursor = dbCollection.find(query, projection);

            while (dbCursor.hasNext()) {
                BasicDBObject basicDBObject = (BasicDBObject) dbCursor.next();
                String identifier = (String) basicDBObject.get("identifier");
                if(Utils.evaluateString(identifier) && Utils.isNumericString(identifier) && Integer.parseInt(identifier) > 0){
                    values.add(identifier);
                }
            }
        } catch (Exception e) {
            LOG.error(WebVisibilityAnalyticsMessage.GENERIC_ERROR+ e.getMessage());
        }

        return new ArrayList<>(values);
    }

    /**
     * Método que evalua si se han enviado o no URLs del código de cliente pasado como parámetro a Marketgoo
     * La evaluación consiste en la existencia o no del dato marketgoo en la colección consultada
     *
     * @param clientCode
     * @return
     */
    public boolean evaluateURLSendToMarketgoo(String clientCode) {
        boolean exist = true;

        try {
            query = new BasicDBObject();
            query.put("identifier", clientCode);
            query.put("provider", "marketgoo");
            query.put("marketgooResponse", new BasicDBObject("$exists", true));
            query.put("kpiseg", new BasicDBObject("$exists", true));
            query.put("kpiseg.exists_url", new BasicDBObject("$exists", true));
            query.put("kpiseg.exists_url", true);

            sort = new BasicDBObject();
            sort.put("timestamp", -1);

            dbCursor = dbCollection.find(query).sort(sort).limit(1);
            dbCursor.next();

        } catch (Exception e) {
            if (e instanceof java.util.NoSuchElementException) {
                LOG.info(WebVisibilityAnalyticsMessage.NO_RESULT);
                exist = false;
            }
        }

        return exist;
    }
}
