package muestra.madre.mongo.biRecomendador;

import automation.factory.Logger;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import mongo.Bi_RecomendatorMessage;

import java.util.NoSuchElementException;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la colección BI_RECOMENDADOR
 */
public class Bi_RecomendatorProjection {

    private static Logger LOG = Logger.getLogger(Bi_RecomendatorProjection.class);

    private DBCollection dbCollection;

    private BasicDBObject query;

    private BasicDBObject projection;

    private BasicDBObject sort;

    private DBCursor dbCursor;

    public Bi_RecomendatorProjection(DBCollection dbCollection){
        this.dbCollection =  dbCollection;
    }

    public void instanceProjection() {
        projection = new BasicDBObject();
        projection.put("_id", 0);
    }

    /**
     * Método que obtiene el paquete recomendado dado un código de cliente
     * @param clientCode
     * @return
     */
    public String findPaqueteRecomendadoByClientCode(Integer clientCode) {
        String result = "";
        try {
            instanceProjection();

            query = new BasicDBObject();
            query.put("co_cliente", clientCode);

            projection.put("paquete_recomendado", 1);

            sort = new BasicDBObject();
            sort.put("fe_recomm", -1);

            dbCursor = dbCollection.find(query, projection).sort(sort);

            if(dbCursor.hasNext()){
                BasicDBObject basicDBObject = (BasicDBObject) dbCursor.next();
                result = (String) basicDBObject.get("paquete_recomendado");
            }

        } catch (Exception e) {
            if (e instanceof NoSuchElementException) {
                LOG.info(Bi_RecomendatorMessage.NO_RESULT + e.getMessage());
            } else {
                LOG.error(Bi_RecomendatorMessage.GENERIC_ERROR + e.getMessage());
            }
        }
        return result;
    }
}
