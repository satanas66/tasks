package mercado.candidato.mongo.kpisPrCancDaily;

import automation.factory.Logger;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import mercado.candidato.mongo.kpisActividad.Kpis_ActividadProjection;
import mongo.Kpis_Pr_Canc_DailyMessage;

import java.util.NoSuchElementException;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la colección KPIS_PR_CANC_DAILY
 */
public class Kpis_Pr_Canc_DailyProjection {

    private static Logger LOG = Logger.getLogger(Kpis_ActividadProjection.class);

    private DBCollection dbCollection;

    private BasicDBObject query;

    private BasicDBObject projection;

    private BasicDBObject sort;

    private DBCursor dbCursor;

    public Kpis_Pr_Canc_DailyProjection(DBCollection dbCollection){
        this.dbCollection =  dbCollection;
    }

    public void instanceProjection() {
        projection = new BasicDBObject();
        projection.put("_id", 0);
    }

    /**
     * Método que devuelve el valor pr_canc dado un código de cliente
     * @param clientCode
     * @return pr_canc
     */
    public Double getPrCancByClienCode(Integer clientCode){
        Double result = null;
        try{
            LOG.info(Kpis_Pr_Canc_DailyMessage.PROJECTION_BUILD+" para el código de cliente "+clientCode);

            instanceProjection();

            query = new BasicDBObject();
            query.put("CO_CLIENTE", clientCode);

            projection.put("PR_CANC", 1);

            dbCursor = dbCollection.find(query, projection);

            if(dbCursor.hasNext()){
                BasicDBObject basicDBObject = (BasicDBObject) dbCursor.next();
                if(basicDBObject.get("PR_CANC") != null);
                result = (Double) basicDBObject.get("PR_CANC");
            }
        }catch (Exception e){
            if (e instanceof NoSuchElementException) {
                LOG.info(Kpis_Pr_Canc_DailyMessage.NO_RESULT + e.getMessage());
            } else {
                LOG.error(Kpis_Pr_Canc_DailyMessage.GENERIC_ERROR + e.getMessage());
            }
        }
        return result;
    }
}
