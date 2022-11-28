package recurrent.mongo;

import automation.factory.Logger;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.bson.types.ObjectId;

public class ScrappingProjection {

    private static Logger LOG = Logger.getLogger(ScrappingProjection.class);

    private DBCollection dbCollection;

    private BasicDBObject query;

    private BasicDBObject projection;

    private BasicDBObject sort;

    private DBCursor dbCursor;

    public ScrappingProjection(DBCollection dbCollection) {
        this.dbCollection = dbCollection;
    }

    public void instanceProjection() {
        projection = new BasicDBObject();
        projection.put("_id", 0);
    }

    public String findGMBUrlByPhoneNumber(String phoneNumber){
        String result = "";
        query = new BasicDBObject();
        query.put("phones", phoneNumber);

        projection = new BasicDBObject();
        projection.put("_id", 0);
        projection.put("page_url", 1);

        sort = new BasicDBObject();

        sort = new BasicDBObject();
        sort.put("timestamp", -1);

        dbCursor = dbCollection.find(query, projection).sort(sort);

        while(dbCursor.hasNext()){
            BasicDBObject basicDBObject = (BasicDBObject) dbCursor.next();
            if(basicDBObject.size() >=1 && basicDBObject.get("page_url") != null){
                result = (String) basicDBObject.get("page_url");
                break;
            }
        }

        return result;
    }

}
