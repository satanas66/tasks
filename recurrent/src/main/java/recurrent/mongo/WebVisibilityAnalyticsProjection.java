package recurrent.mongo;

import automation.factory.Logger;
import automation.factory.Utils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import recurrent.domain.WebVisibilityAnalyticsMJD;

import java.util.ArrayList;
import java.util.List;

public class WebVisibilityAnalyticsProjection implements WebVisibilityAnalyticsMJD {

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
     * Método que actualiza o crea el KPISEG404
     * @param identifier, código del cliente
     * @param value, valor del KPI
     */
    @Override
    public void updateKPISEG404ByIdentifier(String identifier, Integer value) {
        try {
            query = new BasicDBObject();
            query.put("identifier", identifier);

            projection = new BasicDBObject();
            projection.put("_id", 1);

            dbCursor = dbCollection.find(query);

            while (dbCursor.hasNext()) {
                BasicDBObject basicDBObject = (BasicDBObject) dbCursor.next();
                ObjectId objectId = (ObjectId) basicDBObject.get("_id");

                query = new BasicDBObject();
                query.put("_id", objectId);

                BasicDBObject updateFields = new BasicDBObject();
                updateFields.put("kpiseg.KPISEG404", value);

                BasicDBObject setQuery = new BasicDBObject();
                setQuery.put("$set", updateFields);

                dbCollection.update(query, setQuery);

                LOG.info("Cliente  " + identifier + " actualizado correctamente");
            }
        } catch (Exception e) {
            LOG.error("Ha ocurrido un error buscando las keywords para el cliente: " + identifier + " " + e.getMessage());
        }
    }

    /**
     * Método que actualiza o crea el KPISEG404
     * @param identifier, código del cliente
     * @param value, valor del KPI
     */
    @Override
    public void updateKPISEG400ByIdentifier(String identifier, String value) {
        try {
            query = new BasicDBObject();
            query.put("identifier", identifier);

            projection = new BasicDBObject();
            projection.put("_id", 1);
            projection.put("kpiseg", 1);

            dbCursor = dbCollection.find(query, projection);

            while (dbCursor.hasNext()) {
                BasicDBObject basicDBObject = (BasicDBObject) dbCursor.next();

                if (basicDBObject.get("kpiseg") != null) {
                    BasicDBObject kpiseg = (BasicDBObject) basicDBObject.get("kpiseg");

                    if (kpiseg.get("KPISEG400") == null) {
                        ObjectId objectId = (ObjectId) basicDBObject.get("_id");

                        query = new BasicDBObject();
                        query.put("_id", objectId);

                        BasicDBObject updateFields = new BasicDBObject();
                        updateFields.put("kpiseg.KPISEG400", value);

                        BasicDBObject setQuery = new BasicDBObject();
                        setQuery.put("$set", updateFields);

                        dbCollection.update(query, setQuery);

                        LOG.info("Cliente  " + identifier + " actualizado correctamente");
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Ha ocurrido un error buscando las keywords para el cliente: " + identifier + " " + e.getMessage());
        }
    }

    /**
     * Método encargado de la búsqueda de KPIS asociados a la generación de recomendador de paquetes
     */
    @Override
    public List<String> findAllClientCodesWithEcommerceCheckoutLink() {
        List<String> result = new ArrayList<>();
        try {
            instanceProjection();

            query = new BasicDBObject();

            projection = new BasicDBObject();
            projection.put("identifier", 1);
            projection.put("marketgooResponse", 1);

            dbCursor = dbCollection.find(query, projection);
            String value;
            while (dbCursor.hasNext()) {
                try{
                    BasicDBObject basicDBObject = (BasicDBObject) dbCursor.next();
                    value = String.valueOf(basicDBObject.get("identifier"));
                    if(Utils.isNumericString(value)){
                        if (basicDBObject.get("marketgooResponse") != null &&
                                ((BasicDBObject) basicDBObject.get("marketgooResponse")).get("data") != null &&
                                ((BasicDBObject) ((BasicDBObject) basicDBObject.get("marketgooResponse")).get("data")).get("attributes") != null) {
                            BasicDBObject attributes = (BasicDBObject) ((BasicDBObject) ((BasicDBObject) basicDBObject.get("marketgooResponse")).get("data")).get("attributes");
                            if (attributes.get("results") != null && ((BasicDBObject) attributes.get("results")).get("technologies") != null) {
                                BasicDBObject ecommerce = (BasicDBObject) ((BasicDBObject) ((BasicDBObject) attributes.get("results")).get("technologies")).get("ecommerce");
                                if (ecommerce != null){
                                    if(ecommerce.get("checkout_link") != null) {
                                        value=value+";"+ecommerce.get("checkout_link");
                                        result.add(value);
                                        LOG.info("Encontrado =>  "+value);
                                    }
                                }
                            }
                        }
                    }
                }catch (Exception e){

                }
            }
        } catch (Exception e) {
            LOG.error("Ha ocurrido un error al obtener el cursor: " + e.getMessage());
        }
        return result;
    }

    @Override
    public void deleteRegisterByIdentifier(String identifier) {
        LOG.info("Elimando registro: "+identifier);
        query = new BasicDBObject();
        query.put("identifier", identifier);
        WriteResult wr = dbCollection.remove(query);
        if(wr.getN() == 1){
            LOG.info("Registro "+identifier+" eliminado correctamente");
        }
    }
}
