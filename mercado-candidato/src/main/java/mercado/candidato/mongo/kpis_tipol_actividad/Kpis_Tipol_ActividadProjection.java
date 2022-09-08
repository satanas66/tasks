package mercado.candidato.mongo.kpis_tipol_actividad;

import automation.factory.Logger;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import mercado.candidato.mongo.kpisActividad.Kpis_ActividadProjection;
import mongo.Kpis_ActividadMessage;
import mongo.Kpis_Tipol_ActividadMessage;

import java.util.NoSuchElementException;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la colección KPIS_TIPOL_ACTIVIDAD
 */
public class Kpis_Tipol_ActividadProjection {

    private static Logger LOG = Logger.getLogger(Kpis_Tipol_ActividadProjection.class);

    private DBCollection dbCollection;

    private BasicDBObject query;

    private BasicDBObject projection;

    private BasicDBObject sort;

    private DBCursor dbCursor;

    public Kpis_Tipol_ActividadProjection(DBCollection dbCollection){
        this.dbCollection =  dbCollection;
    }

    public void instanceProjection() {
        projection = new BasicDBObject();
        projection.put("_id", 0);
    }

    /**
     * Método encargado de devolver una proyección con los valores de ct_merclie y tipo_bs
     * @param co_actvad
     * @return proyección
     */
    public Double getQ_RK_SCOREByCoActvad(String co_actvad){
        Double result = null;
        try{
            LOG.info(Kpis_Tipol_ActividadMessage.PROJECTION_BUILD+" para el código de actividad "+co_actvad);

            instanceProjection();

            query = new BasicDBObject();
            query.put("CO_ACTVAD_PRAL", Double.parseDouble(co_actvad));

            projection.put("Q_RK_SCORE", 1);

            dbCursor = dbCollection.find(query, projection);

            if(dbCursor.hasNext()){
                BasicDBObject dbObject = (BasicDBObject) dbCursor.next();
                result = (Double)dbObject.get("Q_RK_SCORE");
            }
        }catch (Exception e){
            if (e instanceof NoSuchElementException) {
                LOG.info(Kpis_Tipol_ActividadMessage.NO_RESULT + e.getMessage());
            } else {
                LOG.error(Kpis_Tipol_ActividadMessage.GENERIC_ERROR + e.getMessage());
            }
        }
        return result;
    }

}
