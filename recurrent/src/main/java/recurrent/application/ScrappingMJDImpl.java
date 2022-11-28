package recurrent.application;

import automation.factory.Logger;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import mongo.WebVisibilityAnalyticsMessage;
import recurrent.domain.ScrappingMJD;

import java.util.NoSuchElementException;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la colección Scrapping
 */
public class ScrappingMJDImpl implements ScrappingMJD {

    private static Logger LOG = Logger.getLogger(ScrappingMJDImpl.class);

    private DBCollection dbCollection;

    private BasicDBObject query;

    private BasicDBObject projection;

    private BasicDBObject sort;

    private DBCursor dbCursor;

    public ScrappingMJDImpl(DBCollection dbCollection) {
        this.dbCollection = dbCollection;
    }

    public void instanceProjection() {
        projection = new BasicDBObject();
        projection.put("_id", 0);
    }

    public String getGmbKpisByPhoneNumber(String phoneNumer){
        String result = phoneNumer;
        try {
            LOG.info(WebVisibilityAnalyticsMessage.SEARCH_CLIENT + phoneNumer);
            instanceProjection();

            query = new BasicDBObject();
            query.put("phones", phoneNumer);

            projection = new BasicDBObject();
            projection.put("rankingNumber", 1);
            projection.put("reviews", 1);
            projection.put("rating", 1);
            projection.put("web", 1);
            projection.put("claimBusiness", 1);
            projection.put("temporarilyClosedBanner", 1);
            projection.put("fileName", 1);

            sort = new BasicDBObject();
            sort.put("timestamp", -1);

            dbCursor = dbCollection.find(query, projection).sort(sort).limit(1);
            if(dbCursor.hasNext()){
                BasicDBObject basicDBObject = (BasicDBObject) dbCursor.next();
                if (basicDBObject.get("rankingNumber") != null) {
                    result=result+";"+basicDBObject.get("rankingNumber");
                }else{
                    result=result+";"+"";
                }
                if (basicDBObject.get("reviews") != null) {
                    result=result+";"+basicDBObject.get("reviews");
                }else{
                    result=result+";"+"";
                }
                if (basicDBObject.get("rating") != null) {
                    result=result+";"+basicDBObject.get("rating");
                }else{
                    result=result+";"+"";
                }
                if (basicDBObject.get("web") != null) {
                    result=result+";"+basicDBObject.get("web");
                }else{
                    result=result+";"+"";
                }
                if (basicDBObject.get("claimBusiness") != null) {
                    result=result+";"+basicDBObject.get("claimBusiness");
                }else{
                    result=result+";"+"";
                }
                if (basicDBObject.get("temporarilyClosedBanner") != null) {
                    result=result+";"+basicDBObject.get("temporarilyClosedBanner");
                }else{
                    result=result+";"+"";
                }
                if (basicDBObject.get("fileName") != null) {
                    result=result+";"+basicDBObject.get("fileName");
                }else{
                    result=result+";"+"";
                }
            }else{
                return phoneNumer+";"+""+";"+""+";"+""+";"+""+";" +""+";"+""+";"+"";
            }

        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                LOG.info(WebVisibilityAnalyticsMessage.NO_RESULT + e.getMessage());
            } else {
                LOG.error(WebVisibilityAnalyticsMessage.GENERIC_ERROR + e.getMessage());
            }
        }
        return result;
    }
}
