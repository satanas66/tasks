package indices.mongo.webVisibilityAnalytics;

import automation.factory.Logger;
import automation.factory.Utils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import mongo.WebVisibilityAnalyticsMessage;

import java.util.ArrayList;
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
     * Proyección privada que obtiene todos los errores web dado un código de cliente
     * @param clientCode
     * @return proyección
     */
    public Object[] findKpisToCalculateDigitalPresence(String clientCode){
        LOG.info(WebVisibilityAnalyticsMessage.SEARCH_CLIENT + clientCode);
        Object[] result;
        try{
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
                result = new Object[15];

                DBObject kpiseg = (DBObject) dbObject.get("kpiseg");
                if (kpiseg.get("KPISEG05") != null) {//>3 Enlaces rotos
                    result[0] = Integer.parseInt(String.valueOf(kpiseg.get("KPISEG05")));
                }
                if (kpiseg.get("KPISEG07") != null) {//No sitemap
                    result[1] = Boolean.valueOf(String.valueOf(kpiseg.get("KPISEG07")));
                }
                if (kpiseg.get("KPISEG08") != null) {//No robots txt
                    result[2] = Boolean.valueOf(String.valueOf(kpiseg.get("KPISEG08")));
                }
                if (kpiseg.get("KPISEG09") != null) {//Sitio no seguro
                    result[3] = Boolean.valueOf(String.valueOf(kpiseg.get("KPISEG09")));
                }
                if (kpiseg.get("KPISEG10") != null) {//Errores en ALT
                    result[4] = Boolean.valueOf(String.valueOf(kpiseg.get("KPISEG10")));
                }
                if (kpiseg.get("KPISEG11") != null) {//Errores en title
                    result[5] = Boolean.valueOf(String.valueOf(kpiseg.get("KPISEG11")));
                }
                if (kpiseg.get("KPISEG12") != null) {//Poco contenido
                    result[6] = Boolean.valueOf(String.valueOf(kpiseg.get("KPISEG12")));
                }
                if (kpiseg.get("KPISEG13") != null) {//Errores en index
                    result[7] = Boolean.valueOf(String.valueOf(kpiseg.get("KPISEG13")));
                }
                if (kpiseg.get("KPISEG14") != null) {//>1 sin actualiza
                    result[8] = Boolean.valueOf(String.valueOf(kpiseg.get("KPISEG14")));
                }
                if (kpiseg.get("KPISEG15") != null) {//No responsive
                    result[9] = Boolean.valueOf(String.valueOf(kpiseg.get("KPISEG15")));
                }
                if (kpiseg.get("KPISEG16") != null) {//>5 segs carga móvil
                    result[10] = Boolean.valueOf(String.valueOf(kpiseg.get("KPISEG16")));
                }
                if(kpiseg.get("KPISEG32") != null){//claimBusiness
                    result[11] = Boolean.valueOf(String.valueOf(kpiseg.get("KPISEG32")));
                }
                if (kpiseg.get("KPISEG122") != null) {//reviews
                    result[12] = Utils.getIntegerByString(String.valueOf(kpiseg.get("KPISEG122")));
                }
                if(kpiseg.get("KPISEG302") != null){//rankingNumber
                    result[13] = Integer.parseInt(String.valueOf(kpiseg.get("KPISEG302")));
                }
                if(kpiseg.get("KPISEG23") != null){//keywordtop10
                    result[14] = Integer.parseInt(String.valueOf(kpiseg.get("KPISEG23")));
                }
                resultados.add(result);
            }
            result = getProjection(resultados, 15);
        }catch (Exception e){
            if (e instanceof NoSuchElementException) {
                LOG.info(WebVisibilityAnalyticsMessage.NO_RESULT+ clientCode + ": " + e.getMessage());
            } else {
                LOG.error(WebVisibilityAnalyticsMessage.GENERIC_ERROR + clientCode + " => " + e.getMessage());
            }
            result = new Object[15];
        }
        return result;
    }

    /**
     * Método que obtiene la proyección con mayor elementos.
     * Este cálculo se realiza debido a que existen proyecciones almacenadas con la misma fecha y mismo código de cliente
     * @param projections
     * @return proyeccion
     */
    private Object[] getProjection(List<Object[]> projections, Integer tam){
        Object[] result;
        if (projections.size() == 0) {
            result = new Object[tam];
            return result;
        }
        if (projections.size() == 1) {
            return projections.get(0);
        }

        Object[] uno = projections.get(0);
        Object[] dos = projections.get(1);
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
        return result;
    }
}
