package indices.mongo.webVisibilityAnalytics;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import mongo.WebVisibilityAnalyticsMessage;

import java.util.*;

/**
 * @author Edwin Patricio Arévalo Angulo
 * <p>
 * Clase encargada de recoger los valores almacenados en la colección WEBVISIBILITYANALYTICS
 */
public class WebVisibilityAnalyticsProjection {

    private static Logger LOG = Logger.getLogger(WebVisibilityAnalyticsProjection.class);

    private DBCollection webVisibilityAnalyticsDBCollection;

    private BasicDBObject query;

    private BasicDBObject projection;

    private BasicDBObject sort;

    private DBCursor dbCursor;

    public WebVisibilityAnalyticsProjection(DBCollection webVisibilityAnalyticsDBCollection) {
        this.webVisibilityAnalyticsDBCollection = webVisibilityAnalyticsDBCollection;
    }

    public void instanceProjection() {
        projection = new BasicDBObject();
        projection.put("_id", 0);
    }

    public List<String> findAllClientCodesWithUrlTrue() {
        List<String> result = new ArrayList<>();
        try {
            query = new BasicDBObject();
            query.put("marketgooResponse", new BasicDBObject("$exists", true));
            query.put("kpiseg", new BasicDBObject("$exists", true));
            query.put("kpiseg.exists_url", new BasicDBObject("$exists", true));
            query.put("kpiseg.exists_url", true);

            instanceProjection();
            projection.put("identifier", 1);

            dbCursor = webVisibilityAnalyticsDBCollection.find(query, projection);

            Set<String> conjunto = new HashSet<>();
            while (dbCursor.hasNext()) {
                try {
                    BasicDBObject basicDBObject = (BasicDBObject) dbCursor.next();
                    if (basicDBObject.get("identifier") != null && Utils.isNumericString(basicDBObject.get("identifier").toString())) {
                        String clientCode = (String) basicDBObject.get("identifier");
                        conjunto.add(clientCode);
                    }
                } catch (Exception e) {
                    LOG.error(WebVisibilityAnalyticsMessage.GENERIC_ERROR + " => " + e.getMessage());
                }
            }
            result.addAll(conjunto);
        } catch (Exception e) {
            LOG.error("Ha ocurrido un error al obtener el cursor: " + e.getMessage());
        }
        return result;
    }

    private int searchIndexId(String indexId) {
        Map<String, Integer> values = new HashMap<>();
        values.put("indexId1", 0);
        values.put("indexId2", 1);
        values.put("indexId3", 2);
        values.put("indexId4", 3);
        values.put("indexId5", 4);
        values.put("indexId6", 5);
        values.put("indexId7", 6);
        return values.get(indexId);
    }

    public String findDomainByClientCode(String identifier) {
        String result = "";
        try {
            query = new BasicDBObject();
            query.put("identifier", identifier);

            instanceProjection();
            projection.put("domain", 1);

            dbCursor = webVisibilityAnalyticsDBCollection.find(query, projection);

            if (dbCursor.hasNext()) {
                BasicDBObject basicDBObject = (BasicDBObject) dbCursor.next();
                if (basicDBObject.get("domain") != null) {
                    result = (String) basicDBObject.get("domain");
                }
            }
        } catch (Exception e) {
            LOG.error("Ha ocurrido un error al obtener el cursor: " + e.getMessage());
        }
        return result;
    }

    public String findDigitalPresenceIndexToClientCodeWithUrlTrue(String identifier, BasicDBList kpiseg400, BasicDBList kpiseg401) {
        String values = identifier;
        try {
            query = new BasicDBObject();
            query.put("identifier", identifier);

            sort = new BasicDBObject();
            sort.put("timestamp", -1);

            instanceProjection();
            projection.put("kpiseg", 1);

            dbCursor = webVisibilityAnalyticsDBCollection.find(query, projection).sort(sort).limit(1);

            if (dbCursor.hasNext()) {
                try {
                    BasicDBObject basicDBObject = (BasicDBObject) dbCursor.next();
                    if (basicDBObject.get("kpiseg") != null) {
                        BasicDBObject kpiseg = (BasicDBObject) basicDBObject.get("kpiseg");
                        if (kpiseg.get("KPISEG400") != null) {
                            Integer indice = searchIndexId((String) kpiseg.get("KPISEG400"));
                            values = values + ";" + ((BasicDBObject) kpiseg400.get(indice)).get("value");
                        } else {
                            values = values + ";null";
                        }
                        if (kpiseg.get("KPISEG32") != null) {
                            values = values + ";" + kpiseg.get("KPISEG32");
                        } else {
                            values = values + ";null";
                        }
                        if (kpiseg.get("KPISEG122") != null) {
                            values = values + ";" + kpiseg.get("KPISEG122");
                        } else {
                            values = values + ";null";
                        }
                        if (kpiseg.get("KPISEG401") != null) {
                            Integer indice = searchIndexId((String) kpiseg.get("KPISEG401"));
                            values = values + ";" + ((BasicDBObject) kpiseg401.get(indice)).get("value");
                        } else {
                            values = values + ";null";
                        }
                        if (kpiseg.get("KPISEG303") != null) {
                            values = values + ";" + kpiseg.get("KPISEG303");
                        } else {
                            values = values + ";null";
                        }
                        if (kpiseg.get("KPISEG23") != null) {
                            values = values + ";" + kpiseg.get("KPISEG23");
                        } else {
                            values = values + ";null";
                        }
                        if (kpiseg.get("KPISEG402") != null) {
                            values = values + ";" + kpiseg.get("KPISEG402");
                        } else {
                            values = values + ";null";
                        }
                        if (kpiseg.get("KPISEG05") != null) {
                            values = values + ";" + kpiseg.get("KPISEG05");
                        } else {
                            values = values + ";null";
                        }
                        if (kpiseg.get("KPISEG07") != null) {
                            values = values + ";" + kpiseg.get("KPISEG07");
                        } else {
                            values = values + ";null";
                        }
                        if (kpiseg.get("KPISEG08") != null) {
                            values = values + ";" + kpiseg.get("KPISEG08");
                        } else {
                            values = values + ";null";
                        }
                        if (kpiseg.get("KPISEG09") != null) {
                            values = values + ";" + kpiseg.get("KPISEG09");
                        } else {
                            values = values + ";null";
                        }
                        if (kpiseg.get("KPISEG10") != null) {
                            values = values + ";" + kpiseg.get("KPISEG10");
                        } else {
                            values = values + ";null";
                        }
                        if (kpiseg.get("KPISEG11") != null) {
                            values = values + ";" + kpiseg.get("KPISEG11");
                        } else {
                            values = values + ";null";
                        }
                        if (kpiseg.get("KPISEG12") != null) {
                            values = values + ";" + kpiseg.get("KPISEG12");
                        } else {
                            values = values + ";null";
                        }
                        if (kpiseg.get("KPISEG13") != null) {
                            values = values + ";" + kpiseg.get("KPISEG13");
                        } else {
                            values = values + ";null";
                        }
                        if (kpiseg.get("KPISEG15") != null) {
                            values = values + ";" + kpiseg.get("KPISEG15");
                        } else {
                            values = values + ";null";
                        }
                        if (kpiseg.get("KPISEG16") != null) {
                            values = values + ";" + kpiseg.get("KPISEG16");
                        } else {
                            values = values + ";null";
                        }
                        if (kpiseg.get("KPISEG400") == null && (
                                (kpiseg.get("KPISEG32") != null) ||
                                        kpiseg.get("KPISEG122") != null ||
                                        kpiseg.get("KPISEG401") != null ||
                                        kpiseg.get("KPISEG402") != null)) {

                            Boolean seg32 = (kpiseg.get("KPISEG32") != null) ? Boolean.parseBoolean(String.valueOf(kpiseg.get("KPISEG32"))) : null;
                            Integer seg122 = (kpiseg.get("KPISEG122") != null) ? Integer.parseInt(String.valueOf(kpiseg.get("KPISEG122"))) : null;
                            Integer seg401 = null;
                            if (kpiseg.get("KPISEG401") != null) {
                                Integer indice = searchIndexId((String) kpiseg.get("KPISEG401"));
                                seg401 = (Integer) ((BasicDBObject) kpiseg401.get(indice)).get("value");
                            }
                            Integer seg402 = (kpiseg.get("KPISEG402") != null) ? Integer.parseInt(String.valueOf(kpiseg.get("KPISEG402"))) : null;

                            Integer evaluation = null;
                            if (seg32 == null && seg122 == null && seg401 == null && seg402 == null) {
                                evaluation = 0;
                            }
                            if (evaluation == null && ((seg401 != null && seg401 == 5) || seg402 == null || seg122 == 0)) {
                                evaluation = 35;
                            }
                            if (evaluation == null && (seg32 || (seg401 != null && seg401 == 10) || (seg402 != null && seg402 >= 6) || (seg122 != null && seg122 >= 1 && seg122 <= 2))) {
                                evaluation = 40;
                            }
                            if (evaluation == null && ((seg401 != null && seg401 == 25) || (seg402 != null && seg402 >= 4 && seg402 <= 5) || (seg122 != null && seg122 >= 21 && seg122 <= 40))) {
                                evaluation = 55;
                            }
                            if (evaluation == null && ((seg401 != null && seg401 == 45) || (seg402 != null && seg402 == 4) || (seg122 != null && seg122 >= 41 && seg122 <= 80))) {
                                evaluation = 70;
                            }
                            if (evaluation == null && ((seg401 != null && seg401 == 65) || (seg402 != null && seg402 == 2) || (seg122 != null && seg122 >= 81 && seg122 <= 90))) {
                                evaluation = 85;
                            }
                            if (evaluation == null && ((seg401 != null && seg401 == 80) || (seg402 != null && seg402 >= 0 && seg402 <= 1) || (seg122 != null && seg122 > 90))) {
                                evaluation = 90;
                            }
                            if (evaluation != null) {
                                values = values + ";" + evaluation;
                            } else {
                                values = values + ";null";
                            }
                        }
                        if (kpiseg.get("KPISEG404") != null) {
                            values = values + ";" + kpiseg.get("KPISEG404");
                        } else {
                            values = values + ";null";
                        }

                    }
                    LOG.info("CLIENTE ENCONTRADO: " + identifier);

                } catch (Exception e) {
                    LOG.error(WebVisibilityAnalyticsMessage.GENERIC_ERROR + " => " + e.getMessage());
                }
            }
        } catch (Exception e) {
            LOG.error("Ha ocurrido un error al obtener el cursor: " + e.getMessage());
        }
        return values;
    }
}
