package mercado.candidato.mongo.webVisibilityAnalytics;

import automation.factory.Logger;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import mongo.WebVisibilityAnalyticsMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

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
     * Método encargado de obtener un cursor cuya proyección será la obtención de los KPIs que dependera del código de cliente (identifier) pasado por parámetro
     * Se devolverá la última proyección actualizada según el campo timestamp
     *
     * @param clientCode
     * @return
     */
    public DBCursor findValuesKPIsFromWebVisibilityAnalytics(String clientCode) {
        try {
            LOG.info(WebVisibilityAnalyticsMessage.SEARCH_CLIENT + clientCode);
            instanceProjection();

            query = new BasicDBObject();
            query.put("identifier", clientCode);

            projection = new BasicDBObject();

            projection.put("domain", 1);
            projection.put("listingVersion", 1);
            projection.put("timestamp", 1);
            projection.put("kpiseg", 1);
            projection.put("marketgooResponse", 1);

            sort = new BasicDBObject();
            sort.put("timestamp", -1);

            dbCursor = dbCollection.find(query, projection).sort(sort);
        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                LOG.info(WebVisibilityAnalyticsMessage.NO_RESULT + e.getMessage());
            } else {
                LOG.error(WebVisibilityAnalyticsMessage.GENERIC_ERROR + e.getMessage());
            }
        }
        return dbCursor;
    }

    /**
     * Método que devuelve una lista con objetos que tengan la misma fecha
     *
     * @param clientCode
     * @return
     */
    private List<BasicDBObject> getResultListKPIs(String clientCode) {
        List<BasicDBObject> result = new ArrayList<>();
        Date lastDate = null;
        Date penultimateDate;
        boolean bandera = true;
        dbCursor = findValuesKPIsFromWebVisibilityAnalytics(clientCode);
        while (dbCursor.hasNext()) {
            BasicDBObject basicDBObject = (BasicDBObject) dbCursor.next();
            if (bandera) {
                lastDate = (Date) basicDBObject.get("timestamp");
                bandera = false;
            }
            penultimateDate = (Date) basicDBObject.get("timestamp");
            if (!lastDate.equals(penultimateDate)) {
                break;
            }
            result.add(basicDBObject);
        }
        return result;
    }

    /**
     * Método que devuelve la proyección más actualizada
     * @param clientCode
     * @return
     */
    private BasicDBObject getLastProjection(String clientCode) {
        List<BasicDBObject> projections = getResultListKPIs(clientCode);
        return (projections.size()>0)?projections.get(0):null;
    }

    /**
     * Método que evalua la lista de las proyecciones obtenidas y las disgrega en lista válida o lista inválida
     * Prepondera la lista válida
     * Este método se realiza debido a que hay varios registros para un mismo cliente que tienen un mismo timestamp y en su gran mayoria
     * muchos de estos registros son inválidos
     * @param clientCode
     * @return
     */
    private BasicDBObject getValidBasicDBObject(String clientCode) {
        BasicDBObject result = null;
        List<BasicDBObject> basicDBObjects = getResultListKPIs(clientCode);
        if (basicDBObjects.size() == 0) {
            return null;
        }
        if (basicDBObjects.size() == 1) {
            return basicDBObjects.get(0);
        }

        List<BasicDBObject> validList = new ArrayList<>();
        List<BasicDBObject> invalidList = new ArrayList<>();
        for (BasicDBObject basicDBObject : basicDBObjects) {
            if (basicDBObject.get("marketgooResponse") == null) {
                validList.add(basicDBObject);
            } else {
                BasicDBObject marketgooResponse = (BasicDBObject) basicDBObject.get("marketgooResponse");
                if (marketgooResponse.get("data") != null &&
                        ((BasicDBObject) marketgooResponse.get("data")).get("attributes") != null) {
                    BasicDBObject data = (BasicDBObject) marketgooResponse.get("data");
                    BasicDBObject attributes = (BasicDBObject) data.get("attributes");
                    if (attributes.get("error") != null) {
                        invalidList.add(basicDBObject);
                    } else {
                        validList.add(basicDBObject);
                    }
                }
            }
        }

        if (validList.size() > 0) {
            return validList.get(0);
        }
        if (invalidList.size() > 0) {
            result = invalidList.get(0);
        }

        return result;
    }

    /**
     * Método que genera un Array de objeto con todos los valores para los KPIs que pertenezcan a un código de cliente
     *
     * @param clientCode
     * @return
     */
    public Object[] getProjectionFromWebVisibilityAnalytics(String clientCode) {
        Object[] projection = null;
//        BasicDBObject basicDBObject = getValidBasicDBObject(clientCode);
        BasicDBObject basicDBObject = getLastProjection(clientCode);
        if(basicDBObject != null){
            projection = new Object[53];
            if (basicDBObject.get("domain") != null) {
                projection[0] = basicDBObject.get("domain");
            }
            if (basicDBObject.get("listingVersion") != null) {
                projection[1] = basicDBObject.get("listingVersion");
            }

            if (basicDBObject.get("kpiseg") != null) {
                BasicDBObject kpiseg = (BasicDBObject) basicDBObject.get("kpiseg");
                if (kpiseg.get("exists_url") != null) {
                    projection[2] = kpiseg.get("exists_url");
                }
                if (kpiseg.get("KPISEG105") != null) {
                    projection[3] = kpiseg.get("KPISEG105");
                }
                if (kpiseg.get("KPISEG19") != null) {
                    projection[4] = kpiseg.get("KPISEG19");
                }
                if (kpiseg.get("KPISEG16") != null) {
                    projection[5] = kpiseg.get("KPISEG16");
                }
                if (kpiseg.get("KPISEG03") != null) {
                    projection[6] = kpiseg.get("KPISEG03");
                }
                if (kpiseg.get("KPISEG15") != null) {
                    projection[7] = kpiseg.get("KPISEG15");
                }
                if (kpiseg.get("KPISEG12") != null) {
                    projection[8] = kpiseg.get("KPISEG12");
                }
                if (kpiseg.get("KPISEG10") != null) {
                    projection[9] = kpiseg.get("KPISEG10");
                }
                if (kpiseg.get("KPISEG11") != null) {
                    projection[10] = kpiseg.get("KPISEG11");
                }
                if (kpiseg.get("KPISEG09") != null) {
                    projection[11] = kpiseg.get("KPISEG09");
                }
                if (kpiseg.get("KPISEG07") != null) {
                    projection[12] = kpiseg.get("KPISEG07");
                }
                if (kpiseg.get("KPISEG08") != null) {
                    projection[13] = kpiseg.get("KPISEG08");
                }
                if (kpiseg.get("KPISEG111") != null) {
                    projection[14] = kpiseg.get("KPISEG111");
                }
                if (kpiseg.get("KPISEG23") != null) {
                    projection[15] = kpiseg.get("KPISEG23");
                }

                if(kpiseg.get("KPISEG104") != null){
                    projection[16] = kpiseg.get("KPISEG104"); //projection.put("phones", 1);
                }
                if(kpiseg.get("KPISEG201") != null){
                    projection[17] = kpiseg.get("KPISEG201");//projection.put("web", 1);
                }
                if(kpiseg.get("KPISEG120") != null){
                    projection[18] = kpiseg.get("KPISEG120"); //projection.put("rankingNumber", 1)
                }
                if(kpiseg.get("KPISEG103") != null){
                    projection[19] = kpiseg.get("KPISEG103");//projection.put("page_url", 1);
                }
                if(kpiseg.get("KPISEG121") != null){
                    projection[20] = kpiseg.get("KPISEG121");//projection.put("name", 1);
                }
                if(kpiseg.get("KPISEG122") != null){
                    projection[21] = kpiseg.get("KPISEG122");//projection.put("reviews", 1);
                }
                if(kpiseg.get("KPISEG204") != null){
                    projection[22] = kpiseg.get("KPISEG204");// projection.put("rating", 1);
                }
                if(kpiseg.get("KPISEG32") != null){
                    projection[23] = kpiseg.get("KPISEG32");//projection.put("claimBusiness", 1);
                }
            }

            if (basicDBObject.get("marketgooResponse") != null &&
                    ((BasicDBObject) basicDBObject.get("marketgooResponse")).get("data") != null &&
                    ((BasicDBObject) ((BasicDBObject) basicDBObject.get("marketgooResponse")).get("data")).get("attributes") != null) {

                BasicDBObject marketgooResponse = (BasicDBObject) basicDBObject.get("marketgooResponse");
                BasicDBObject data = (BasicDBObject) marketgooResponse.get("data");
                BasicDBObject attributes = (BasicDBObject) data.get("attributes");

                if (attributes.get("timings") != null &&
                        ((BasicDBObject) attributes.get("timings")).get("mobile") != null) {
                    BasicDBObject timings = (BasicDBObject) attributes.get("timings");
                    BasicDBObject mobile = (BasicDBObject) timings.get("mobile");
                    if (mobile.get("width") != null) {
                        projection[24] = mobile.get("width");
                    }
                    if (mobile.get("height") != null) {
                        projection[25] = mobile.get("height");
                    }
                }

                if (attributes.get("results") != null &&
                        ((BasicDBObject) attributes.get("results")).get("technologies") != null) {

                    BasicDBObject results = (BasicDBObject) attributes.get("results");
                    BasicDBObject technologies = (BasicDBObject) results.get("technologies");


                    if (technologies.get("ecommerce") != null) {
                        BasicDBObject ecommerce = (BasicDBObject) technologies.get("ecommerce");
                        if (ecommerce.get("checkout_link") != null) {
                            projection[26] = ecommerce.get("checkout_link");
                        }
                        if (ecommerce.get("booking_type") != null) {
                            projection[27] = ecommerce.get("booking_type");
                        }
                    }

                    if (technologies.get("social_links") != null) {
                        BasicDBObject social_links = (BasicDBObject) technologies.get("social_links");
                        if (social_links.get("facebook") != null) {
                            projection[28] = social_links.get("facebook");
                        }
                        if (social_links.get("twitter") != null) {
                            projection[29] = social_links.get("twitter");
                        }
                        if (social_links.get("instagram") != null) {
                            projection[30] = social_links.get("instagram");
                        }
                        if (social_links.get("linkedin") != null) {
                            projection[31] = social_links.get("linkedin");
                        }
                    }
                }

                if (attributes.get("keywords") != null) {
                    BasicDBList keywords = (BasicDBList) attributes.get("keywords");
                    int count=0;
                    for (int i = 0; i < keywords.size(); i++) {
                        BasicDBObject keyword = (BasicDBObject) keywords.get(i);
                        String word = (String) keyword.get("keyword");
                        Integer ranking = (Integer) keyword.get("ranking");
                        projection[32 + count] = word;
                        count++;
                        projection[32 + count] = ranking;
                        count++;
                    }
                }
            }
            if (basicDBObject.get("timestamp") != null) {
                projection[52] = basicDBObject.get("timestamp");
            }
        }

        return projection;
    }
}
