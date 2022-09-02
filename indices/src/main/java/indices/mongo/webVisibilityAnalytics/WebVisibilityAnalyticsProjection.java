package indices.mongo.webVisibilityAnalytics;

import automation.factory.Logger;
import com.mongodb.*;
import mongo.WebVisibilityAnalyticsMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Edwin Patricio Arévalo Angulo
 * <p>
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
     * Método que genera un Array de objeto con todos los valores para los KPIs 303 y 23 que pertenezcan a un código de cliente
     *
     * @param clientCode
     * @return projección
     */
    public Object[] getKpis303And23FromWebVisibilityAnalytics(String clientCode) {
        LOG.info(WebVisibilityAnalyticsMessage.SEARCH_CLIENT + clientCode);
        Object[] result;
        try {
            query = new BasicDBObject();
            query.put("identifier", clientCode);
            query.put("provider", "marketgoo");
            query.put("marketgooResponse", new BasicDBObject("$exists", true));
            query.put("kpiseg", new BasicDBObject("$exists", true));
            query.put("kpiseg.exists_url", new BasicDBObject("$exists", true));
            query.put("kpiseg.exists_url", true);

            instanceProjection();
            projection = new BasicDBObject();
            projection.put("kpiseg", 1);

            sort = new BasicDBObject();
            sort.put("timestamp", -1);

            dbCursor = dbCollection.find(query, projection).sort(sort);

            List<Object[]> resultados = new ArrayList<>();
            while (dbCursor.hasNext()) {
                DBObject dbObject = dbCursor.next();
                result = new Object[2];

                DBObject kpiseg = (DBObject) dbObject.get("kpiseg");
                result[0] = 999999999;
                result[1] = 999999999;

                if (kpiseg.get("KPISEG23") != null) {//Keywordtop10
                    result[0] = Integer.parseInt(String.valueOf(kpiseg.get("KPISEG23")));
                }
                if (kpiseg.get("KPISEG303") != null) {//PosicionGMB
                    result[1] = Integer.parseInt(String.valueOf(kpiseg.get("KPISEG303")));
                }
                resultados.add(result);
            }

            if (resultados.size() == 0) {
                result = new Object[2];
                result[0] = 999999999;
                result[1] = 999999999;
                return result;
            }
            if (resultados.size() == 1) {
                return resultados.get(0);
            }

            Object[] uno = resultados.get(0);
            Object[] dos = resultados.get(1);
            int cuenta1 = 0;
            int cuenta2 = 0;
            for (int i = 0; i < 2; i++) {
                if (uno[i] != null && dos[i] == null) {
                    cuenta1++;
                }
                if (uno[i] == null && dos[i] != null) {
                    cuenta2++;
                }
            }
            if (cuenta1 >= cuenta2) {
                result = uno;
            } else {
                result = dos;
            }
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                LOG.info(WebVisibilityAnalyticsMessage.NO_RESULT+ clientCode + ": " + e.getMessage());
            } else {
                LOG.error(WebVisibilityAnalyticsMessage.GENERIC_ERROR + clientCode + " => " + e.getMessage());
            }
            result = new Object[2];
            result[0] = 999999999;
            result[1] = 999999999;
        }
        return result;
    }
}
