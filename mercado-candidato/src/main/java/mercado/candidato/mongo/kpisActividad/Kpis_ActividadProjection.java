package mercado.candidato.mongo.kpisActividad;

import automation.factory.Logger;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import mongo.Kpis_ActividadMessage;

import java.util.NoSuchElementException;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la colección KPIS_ACTIVIDAD
 */
public class Kpis_ActividadProjection {

    private static Logger LOG = Logger.getLogger(Kpis_ActividadProjection.class);

    private DBCollection dbCollection;

    private BasicDBObject query;

    private BasicDBObject projection;

    private BasicDBObject sort;

    private DBCursor dbCursor;

    public Kpis_ActividadProjection(DBCollection dbCollection){
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
    public Object[] getProjectionKpisActividadByCoActvad(String co_actvad){
        Object[] result = null;
        try{
            LOG.info(Kpis_ActividadMessage.PROJECTION_BUILD+" para el código de actividad "+co_actvad);

            instanceProjection();

            query = new BasicDBObject();
            query.put("CO_ACTVAD", co_actvad);

            projection.put("CT_MERCLIE", 1);
            projection.put("TIPO_BS", 1);

            dbCursor = dbCollection.find(query, projection);

            while(dbCursor.hasNext()){
                result = new Object[2];

                BasicDBObject dbObject = (BasicDBObject) dbCursor.next();
                if(dbObject.get("CT_MERCLIE") != null){
                    result[0] = dbObject.get("CT_MERCLIE");
                }
                if(dbObject.get("TIPO_BS") != null){
                    result[1] = dbObject.get("TIPO_BS");
                }
            }
        }catch (Exception e){
            if (e instanceof NoSuchElementException) {
                LOG.info(Kpis_ActividadMessage.NO_RESULT + e.getMessage());
            } else {
                LOG.error(Kpis_ActividadMessage.GENERIC_ERROR + e.getMessage());
            }
        }
        return result;
    }
}
