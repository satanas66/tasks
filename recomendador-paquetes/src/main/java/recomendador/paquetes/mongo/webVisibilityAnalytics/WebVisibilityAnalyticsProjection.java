package recomendador.paquetes.mongo.webVisibilityAnalytics;

import automation.factory.Logger;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import mongo.WebVisibilityAnalyticsMessage;

import java.util.ArrayList;
import java.util.List;

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
     * Método encargado de la búsqueda de KPIS asociados a la generación de recomendador de paquetes
     * El resultado devuelto será una proyección de la colección webVisibilityAnalytics
     */
    public Object[] findProjectionToRecomendadorByClientCode(String clientCode) {
        Object[] result = null;
        try {
            LOG.info(WebVisibilityAnalyticsMessage.SEARCH_CLIENT+clientCode);

            instanceProjection();

            query = new BasicDBObject();
            query.put("identifier", clientCode);

            projection.put("kpiseg", 1);
            projection.put("marketgooResponse", 1);

            sort = new BasicDBObject();
            sort.put("timestamp", -1);

            dbCursor = dbCollection.find(query, projection).sort(sort).limit(2);
            List<Object[]> resultados = new ArrayList<>();
            while (dbCursor.hasNext()) {
                BasicDBObject basicDBObject = (BasicDBObject) dbCursor.next();
                if (basicDBObject.get("kpiseg") != null) {
                    BasicDBObject kpiseg = (BasicDBObject) basicDBObject.get("kpiseg");

                    result = new Object[19];

                    result[0] = Integer.parseInt(clientCode);

                    if (kpiseg.get("KPISEG120") != null) {
                        result[1] = kpiseg.get("KPISEG120"); //projection.put("rankingNumber", 1)
                    }
                    if (kpiseg.get("KPISEG122") != null) {
                        result[2] = kpiseg.get("KPISEG122");//projection.put("reviews", 1);
                    }
                    if (kpiseg.get("KPISEG201") != null) {
                        result[3] = kpiseg.get("KPISEG201");//projection.put("web", 1);
                    }
                    if (kpiseg.get("KPISEG32") != null) {
                        result[4] = kpiseg.get("KPISEG32");//projection.put("claimBusiness", 1);
                    }
                    if (kpiseg.get("KPISEG09") != null) {
                        result[5] = String.valueOf(kpiseg.get("KPISEG09"));
                    }
                    if (kpiseg.get("KPISEG07") != null) {
                        result[6] = String.valueOf(kpiseg.get("KPISEG07"));
                    }
                    if (kpiseg.get("KPISEG08") != null) {
                        result[7] = String.valueOf(kpiseg.get("KPISEG08"));
                    }
                    if (kpiseg.get("KPISEG03") != null) {
                        result[8] = String.valueOf(kpiseg.get("KPISEG03"));
                    }
                    if (kpiseg.get("KPISEG23") != null) {
                        result[9] = Integer.parseInt(String.valueOf(kpiseg.get("KPISEG23")));
                    }
                    if (kpiseg.get("KPISEG111") != null) {
                        result[10] = Integer.parseInt(String.valueOf(kpiseg.get("KPISEG111")));
                    }
                    if (kpiseg.get("KPISEG16") != null) {
                        result[11] = String.valueOf(kpiseg.get("KPISEG16"));
                    }
                    if (kpiseg.get("KPISEG15") != null) {
                        result[12] = String.valueOf(kpiseg.get("KPISEG15"));
                    }
                    if (kpiseg.get("KPISEG12") != null) {
                        result[13] = String.valueOf(kpiseg.get("KPISEG12"));
                    }
                    if (kpiseg.get("KPISEG10") != null) {
                        result[14] = String.valueOf(kpiseg.get("KPISEG10"));
                    }
                    if (kpiseg.get("KPISEG11") != null) {
                        result[15] = String.valueOf(kpiseg.get("KPISEG11"));
                    }
                    if (kpiseg.get("KPISEG105") != null) {
                        result[16] = Integer.parseInt(String.valueOf(kpiseg.get("KPISEG105")));
                    }
                }

                if (basicDBObject.get("marketgooResponse") != null &&
                        ((BasicDBObject) basicDBObject.get("marketgooResponse")).get("data") != null &&
                        ((BasicDBObject) ((BasicDBObject) basicDBObject.get("marketgooResponse")).get("data")).get("attributes") != null) {
                    BasicDBObject attributes = (BasicDBObject) ((BasicDBObject) ((BasicDBObject) basicDBObject.get("marketgooResponse")).get("data")).get("attributes");
                    if (attributes.get("results") != null && ((BasicDBObject) attributes.get("results")).get("technologies") != null) {
                        BasicDBObject ecommerce = (BasicDBObject) ((BasicDBObject) ((BasicDBObject) attributes.get("results")).get("technologies")).get("ecommerce");
                        if (ecommerce != null && ecommerce.get("checkout_link") != null) {
                            result[17] = String.valueOf(ecommerce.get("checkout_link"));
                        }
                        BasicDBObject social_links = (BasicDBObject) ((BasicDBObject) ((BasicDBObject) attributes.get("results")).get("technologies")).get("social_links");
                        if (social_links != null && social_links.get("facebook") != null) {
                            result[18] = String.valueOf(social_links.get("facebook"));
                        }
                    }
                }
                resultados.add(result);
            }
            if (resultados.size() == 1) {
                return resultados.get(0);
            }
            if(resultados.size() > 0){
                Object[] uno = resultados.get(0);
                Object[] dos = resultados.get(1);
                int cuenta1 = 0;
                int cuenta2 = 0;
                for (int i = 0; i < 18; i++) {
                    if (uno[i] != null && dos[i] == null) {
                        cuenta1++;
                    }
                    if (uno[i] == null && dos[i] != null) {
                        cuenta2++;
                    }
                }
                if (cuenta1 > cuenta2) {
                    result = uno;
                } else {
                    result = dos;
                }
            }
        } catch (Exception e) {
            LOG.error("Ha ocurrido un error al obtener el cursor: " + e.getMessage());
        }
        return result;
    }
}
