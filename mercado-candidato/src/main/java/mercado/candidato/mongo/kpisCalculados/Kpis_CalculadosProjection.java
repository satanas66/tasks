package mercado.candidato.mongo.kpisCalculados;

import automation.factory.Logger;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import mongo.Kpis_CalculadosMessage;

import java.util.NoSuchElementException;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la colección KPIS_CALCULADOS
 */
public class Kpis_CalculadosProjection {

    private static Logger LOG = Logger.getLogger(Kpis_CalculadosProjection.class);

    private DBCollection dbCollection;

    private BasicDBObject query;

    private BasicDBObject projection;

    private BasicDBObject sort;

    private DBCursor dbCursor;

    public Kpis_CalculadosProjection(DBCollection dbCollection){
        this.dbCollection =  dbCollection;
    }

    public void instanceProjection() {
        projection = new BasicDBObject();
        projection.put("_id", 0);
    }

    /**
     * Método que obtiene una proyección con neg_est, segmento, new_segm, pr_cross_selling, pr_up_selling y ic_pred dado un código de cliente
     * @param co_cliente
     * @return proyección
     */
    public Object[] getProjectionKpisCalculadosByClientCode(Integer co_cliente){
        Object[] result = null;
        try{
            LOG.info(Kpis_CalculadosMessage.PROJECTION_BUILD+" para el código de cliente "+co_cliente);

            instanceProjection();

            query = new BasicDBObject();
            query.put("co_cliente", co_cliente);

            projection.put("neg_est", 1);
            projection.put("segmento", 1);
            projection.put("new_segm", 1);
            projection.put("pr_cross_selling", 1);
            projection.put("pr_up_selling", 1);
            projection.put("ic_pred", 1);

            dbCursor = dbCollection.find(query, projection);
            if (dbCursor.hasNext()){
                result = new Object[6];

                BasicDBObject dbObject = (BasicDBObject) dbCursor.next();
                if(dbObject.get("neg_est") != null){
                    result[0] = dbObject.get("neg_est");
                }
                if(dbObject.get("segmento") != null){
                    result[1] = dbObject.get("segmento");
                }
                if(dbObject.get("new_segm") != null){
                    result[2] = dbObject.get("new_segm");
                }
                if(dbObject.get("pr_cross_selling") != null){
                    result[3] = dbObject.get("pr_cross_selling");
                }
                if(dbObject.get("pr_up_selling") != null){
                    result[4] = dbObject.get("pr_up_selling");
                }
                if(dbObject.get("ic_pred") != null){
                    result[5] = dbObject.get("ic_pred");
                }
            }
        }catch (Exception e){
            if (e instanceof NoSuchElementException) {
                LOG.info(Kpis_CalculadosMessage.NO_RESULT + e.getMessage());
            } else {
                LOG.error(Kpis_CalculadosMessage.GENERIC_ERROR + e.getMessage());
            }        }
        return result;
    }
}
