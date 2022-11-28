package mongo.webVisibilityKpisMapping;

import automation.factory.Logger;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

/**
 * @author Edwin Patricio Arévalo Angulo
 * <p>
 * Clase encargada de recoger los valores almacenados en la colección WEBVISIBILITYKPISMAPPINGS
 */
public class WebVisibilityKpisMappingProjection {

    private static Logger LOG = Logger.getLogger(WebVisibilityKpisMappingProjection.class);

    private DBCollection dbCollection;

    private BasicDBObject query;

    private BasicDBObject projection;

    private DBCursor dbCursor;

    public WebVisibilityKpisMappingProjection(DBCollection dbCollection) {
        this.dbCollection = dbCollection;
    }

    public void instanceProjection() {
        projection = new BasicDBObject();
        projection.put("_id", 0);
    }

    public BasicDBList findDependentIndicesOnKPISEG(String kpiCode) {
        BasicDBList result = null;
        try {
            query = new BasicDBObject();
            query.put("code", kpiCode);

            instanceProjection();
            projection.put("dependentIndicesOn", 1);

            dbCursor = dbCollection.find(query, projection);

            if (dbCursor.hasNext()) {
                BasicDBObject basicDBObject = (BasicDBObject) dbCursor.next();
                result = (BasicDBList) basicDBObject.get("dependentIndicesOn");
            }
        } catch (Exception e) {
            LOG.error("Ha ocurrido un error al obtener el cursor: " + e.getMessage());
        }
        return result;
    }
}
