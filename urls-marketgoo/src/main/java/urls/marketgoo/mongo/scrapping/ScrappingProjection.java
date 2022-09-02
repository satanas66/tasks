package urls.marketgoo.mongo.scrapping;

import automation.factory.Logger;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import mongo.ScrappingMessage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la colección WEBVISIBILITYANALYTICS
 */
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

    /**
     * Método que permite obtener un cursor cuya proyección devuelva únicamente los códigos de clientes (clienCode)
     *
     * @return
     */
    public List<String> findAllClientCodesWithoutRepetition() {
        Set<String> values = new HashSet<>();
        try {

            instanceProjection();

            query = new BasicDBObject();

            projection.put("clientCode", 1);//Muestra el id del cliente

            dbCursor = dbCollection.find(query, projection);

            while (dbCursor.hasNext()) {
                BasicDBObject basicDBObject = (BasicDBObject) dbCursor.next();
                if(null != basicDBObject.get("clientCode") && (Integer) basicDBObject.get("clientCode") > 0){
                    Integer clientCode = (Integer) basicDBObject.get("clientCode");
                    values.add(String.valueOf(clientCode));
                }
            }
        } catch (Exception e) {
            LOG.error(ScrappingMessage.GENERIC_ERROR + e.getMessage());
        }
        return new ArrayList<>(values);
    }


}
