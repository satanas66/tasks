package mongo;

import automation.factory.Logger;
import com.mongodb.*;

import java.util.Arrays;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de realizar la conexión hacia la base de datos Mongo
 */
public class ConnectionMongo {

    private static Logger LOG = Logger.getLogger(ConnectionMongo.class);

    private MongoClient mongoClient;

    private String mongoDB;

    private String mongoHost;

    private String mongoPort;

    private String mongoUser;

    private String mongoPassword;

    /**
     * Constructor en el que se
     */
    public ConnectionMongo(){
        getPropertiesDB();
        setMongoClient();
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    /**
     * Método que recoge los valores almacenados en las variables de entorno para realizar las conexiones a la base de datos
     */
    private void getPropertiesDB(){
        this.mongoDB = System.getenv().get("MONGO_DB");
        this.mongoHost =  System.getenv().get("MONGO_HOST");
        this.mongoPort =  System.getenv().get("MONGO_PORT");
        this.mongoUser = System.getenv().get("MONGO_USER");
        this.mongoPassword = System.getenv().get("MONGO_PASSWORD");
    }

    /**
     * Método encargado de abrir la conexión hacia la base de datos MongoDB
     */
    public void setMongoClient() {
        try {
            MongoCredential credential = MongoCredential.createScramSha1Credential(
                    mongoUser,
                    mongoDB,
                    mongoPassword.toCharArray());
            ServerAddress serverAddress = new ServerAddress(
                    mongoHost,
                    Integer.parseInt(mongoPort));
            this.mongoClient = new MongoClient(serverAddress, Arrays.asList(credential));
        } catch (Exception e) {
            LOG.error("Ha ocurrido un error en la conexión a MONGODB " + mongoDB + ": " + e.getMessage());
        }
    }

    public void endConnection(){
        getMongoClient().close();
    }

    /**
     * Método que devuelve el acceso a una colección cuyo nombre es pasado por parámetros
     * @return colección
     */
    public DBCollection getDBCollection(String collectionName){
        DBCollection result = null;
        try{
            DB db = getMongoClient().getDB(mongoDB);
            result = db.getCollection(collectionName);
        }catch(Exception e){
            LOG.error(WebVisibilityAnalyticsMessage.ERROR_CONNECTION_DB + mongoDB + ": " + e.getMessage());
        }
        return result;
    }
}
