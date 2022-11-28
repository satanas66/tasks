package mongo;

import automation.factory.Logger;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.Arrays;

/**
 * @author Edwin Patricio Arévalo Angulo
 * <p>
 * Clase encargada de realizar la conexión hacia la base de datos Mongo
 */
public class ConnectionMongo {

    private static Logger LOG = Logger.getLogger(ConnectionMongo.class);

    private MongoClient mongoClient;

    private MongoClient mongoClientLocal;

    private String mongoDB;

    private String mongoHost;

    private String mongoPort;

    private String mongoUser;

    private String mongoPassword;

    /**
     * Constructor en el que se
     */
    public ConnectionMongo(String entorno, Integer engine) {
        if ("LOCAL".equalsIgnoreCase(entorno)) {
            getPropertiesDBLOCAL();
            setMongoClientLocal();
        } else {
            if ("PRO".equalsIgnoreCase(entorno)) {
                getPropertiesDBPRO(engine);
            }
            if ("PRE".equalsIgnoreCase(entorno)) {
                getPropertiesDBPRE(engine);
            }
            if ("DES".equalsIgnoreCase(entorno)) {
                getPropertiesDBDES();
            }
            setMongoClient();
        }
    }

    /**
     * Constructor en el que se
     */
    public ConnectionMongo() {
        getPropertiesDBPRO();
        setMongoClientReplica();
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    private void getPropertiesDBDES() {
        this.mongoDB = "esruc1d";
        this.mongoHost = "DELMONG01.v.cp.inet";
        this.mongoPort = "27017";
        this.mongoUser = "gestruc";
        this.mongoPassword = "G3sTuc";
    }

    private void getPropertiesDBPRE(Integer engine) {
        this.mongoDB = "esruc1a";
        this.mongoHost = "spmadqalmong0" + engine + ".intrayell.com";
        this.mongoPort = "27017";
        this.mongoUser = "gestruc";
        this.mongoPassword = "G3strUc";
    }

    private void getPropertiesDBPRO(Integer engine) {
        this.mongoDB = "esruc1p";
        this.mongoHost = "spmadplmong0" + engine + ".intrayell.com";
        this.mongoPort = "27017";
        this.mongoUser = "gestruc";
        this.mongoPassword = "G3strUc";
    }

    private void getPropertiesDBPRO() {
        this.mongoDB = "esruc1p";
        this.mongoPort = "27017";
        this.mongoUser = "gestruc";
        this.mongoPassword = "G3strUc";
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

    /**
     * Método encargado de abrir la conexión hacia la base de datos MongoDB
     */
    public void setMongoClientReplica() {
        try {
            MongoCredential credential = MongoCredential.createScramSha1Credential(mongoUser, mongoDB, mongoPassword.toCharArray());
            ServerAddress serverAddress1 = new ServerAddress("spmadplmong01.intrayell.com", Integer.parseInt(mongoPort));
            ServerAddress serverAddress2 = new ServerAddress("spmadplmong02.intrayell.com", Integer.parseInt(mongoPort));
            ServerAddress serverAddress3 = new ServerAddress("spmadplmong03.intrayell.com", Integer.parseInt(mongoPort));
            this.mongoClient = new MongoClient(Arrays.asList(serverAddress1, serverAddress2, serverAddress3), Arrays.asList(credential));
        } catch (Exception e) {
            LOG.error("Ha ocurrido un error en la conexión a MONGODB " + mongoDB + ": " + e.getMessage());
        }
    }


    public void endConnection() {
        getMongoClient().close();
    }

    /**
     * Método que devuelve el acceso a una colección cuyo nombre es pasado por parámetros
     *
     * @return colección
     */
    public DBCollection getDBCollection(String collectionName) {
        DBCollection result = null;
        try {
            DB db = getMongoClient().getDB(mongoDB);
            result = db.getCollection(collectionName);
        } catch (Exception e) {
            LOG.error(WebVisibilityAnalyticsMessage.ERROR_CONNECTION_DB + mongoDB + ": " + e.getMessage());
        }
        return result;
    }

    public MongoClient getMongoClientLocal() {
        return mongoClientLocal;
    }

    /**
     * Método que recoge los valores almacenados en las variables de entorno para realizar las conexiones a la base de datos
     */
    private void getPropertiesDBLOCAL() {
        this.mongoDB = "tasks";
        this.mongoHost = "localhost";
        this.mongoPort = "27017";
    }


    /**
     * Método encargado de abrir la conexión hacia la base de datos MongoDB
     */
    public void setMongoClientLocal() {
        try {
            this.mongoClientLocal = new MongoClient(new MongoClientURI("mongodb://" + mongoHost + ":" + mongoPort));
        } catch (Exception e) {
            LOG.error("Ha ocurrido un error en la conexión a MONGODB " + mongoDB + ": " + e.getMessage());
        }
    }

    public void endConnectionLocal() {
        getMongoClientLocal().close();
    }

    /**
     * Método que devuelve el acceso a una colección cuyo nombre es pasado por parámetros
     *
     * @return colección
     */
    public MongoCollection getDBMongoCollection(String collectionName) {
        MongoCollection result = null;
        try {
            MongoDatabase mongoDatabase = getMongoClientLocal().getDatabase(mongoDB);
            result = mongoDatabase.getCollection(collectionName);
        } catch (Exception e) {
            LOG.error(WebVisibilityAnalyticsMessage.ERROR_CONNECTION_DB + mongoDB + ": " + e.getMessage());
        }
        return result;
    }
}
