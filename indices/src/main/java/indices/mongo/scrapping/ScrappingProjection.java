package indices.mongo.scrapping;

import automation.factory.Logger;
import com.mongodb.*;
import mongo.ScrappingMessage;

import java.util.*;

/**
 * @author Edwin Patricio Arévalo Angulo
 * <p>
 * Clase encargada de recoger los valores almacenados en la colección SCRAPPING
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
     * Método que obtiene todos los códigos de clientes con sus respectivos rankingNumber, ordenados por última fecha de incorporación y sin repetición
     * @return lista de proyecciones
     */
    public List<Object[]> findRankingNumberAndClientCode(){
        LOG.info(ScrappingMessage.SEARCH_DATAS);
        List<Object[]> result = null;
        try{
            instanceProjection();

            BasicDBList or = new BasicDBList();
            or.add(new BasicDBObject("source", "octoparse"));
            or.add(new BasicDBObject("source", "scrappinghub"));
            or.add(new BasicDBObject("source", "datacentric"));
            or.add(new BasicDBObject("source", "facebook"));
            or.add(new BasicDBObject("source", "listanegocio"));

            query = new BasicDBObject();

            projection.put("clientCode", 1);
            projection.put("rankingNumber", 1);

            sort = new BasicDBObject();
            sort.put("timestamp", -1);

            dbCursor = dbCollection.find(query, projection).sort(sort);

            Map<Integer, Object[]> projectionsMap = new LinkedHashMap<>();
            Object[] projection;
            while (dbCursor.hasNext()) {
                DBObject dbObject = dbCursor.next();
                projection = new Object[2];
                Integer clientCode = null;
                if (dbObject.get("clientCode") != null) {
                    clientCode = Integer.parseInt(String.valueOf(dbObject.get("clientCode")));
                    if(clientCode > 0){
                        projection[0] = clientCode;
                    }
                }
                if (dbObject.get("rankingNumber") != null) {//PosicionGMB
                    projection[1] = Integer.parseInt(String.valueOf(dbObject.get("rankingNumber")));
                }
                if(clientCode != null && clientCode > 0 && !projectionsMap.keySet().contains(clientCode) && projection[1] != null && !"null".equals(projection[1])){
                    LOG.info(ScrappingMessage.CLIENT_FOUND+clientCode);
                    projectionsMap.put(clientCode, projection);
                }
            }
            result = new ArrayList<>(projectionsMap.values());
            LOG.info("Se han encontrado "+result.size()+" resultados");
        }catch (Exception e){
            if (e instanceof NoSuchElementException) {
                LOG.info(ScrappingMessage.NO_RESULT + e.getMessage());
            } else {
                LOG.error(ScrappingMessage.GENERIC_ERROR + e.getMessage());
            }
        }
        return result;
    }
}
