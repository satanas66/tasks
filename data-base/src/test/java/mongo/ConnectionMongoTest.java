package mongo;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoCompressor;
import com.mongodb.client.MongoCollection;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ConnectionMongoTest {

    private static ConnectionMongo connectionMongo = null;

    @Test
    public void getMongoClient() {
        connectionMongo = new ConnectionMongo("PRO", 1);
        MongoClient mongoClient = connectionMongo.getMongoClient();
        assertThat(mongoClient).isNotNull();
        connectionMongo.endConnection();

        connectionMongo = new ConnectionMongo("PRE", 1);
        mongoClient = connectionMongo.getMongoClient();
        assertThat(mongoClient).isNotNull();
        connectionMongo.endConnection();

        connectionMongo = new ConnectionMongo("DES", null);
        mongoClient = connectionMongo.getMongoClient();
        assertThat(mongoClient).isNotNull();
        connectionMongo.endConnection();

        connectionMongo = new ConnectionMongo("LOCAL", null);
        mongoClient = connectionMongo.getMongoClientLocal();
        assertThat(mongoClient).isNotNull();
        connectionMongo.endConnectionLocal();
    }


    @Test
    public void getDBCollection() {
        connectionMongo = new ConnectionMongo("PRO", 1);
        DBCollection dbCollection = connectionMongo.getDBCollection("webVisibilityAnalytics");
        assertThat(dbCollection).isNotNull();

        dbCollection = connectionMongo.getDBCollection("bi_recomendator");
        assertThat(dbCollection).isNotNull();

        connectionMongo.endConnection();

        connectionMongo = new ConnectionMongo("PRE", 1);
        dbCollection = connectionMongo.getDBCollection("webVisibilityAnalytics");
        assertThat(dbCollection).isNotNull();

        dbCollection = connectionMongo.getDBCollection("bi_recomendator");
        assertThat(dbCollection).isNotNull();

        connectionMongo.endConnection();


        connectionMongo = new ConnectionMongo("DES", null);
        dbCollection = connectionMongo.getDBCollection("kpis_tipol_actividad");
        assertThat(dbCollection).isNotNull();
        connectionMongo.endConnection();

        connectionMongo = new ConnectionMongo("LOCAL", null);
        MongoCollection mongoCollection = connectionMongo.getDBMongoCollection("mercadoPotencial");
        assertThat(mongoCollection).isNotNull();
        connectionMongo.endConnectionLocal();
    }

    @Test
    public void getMongoClientReplica() {
        connectionMongo = new ConnectionMongo();
        MongoClient mongoClient = connectionMongo.getMongoClient();
        assertThat(mongoClient).isNotNull();
        connectionMongo.endConnection();
    }

    @Test
    public void getDBCollectionReplica() {
        connectionMongo = new ConnectionMongo();
        DBCollection dbCollection = connectionMongo.getDBCollection("webVisibilityAnalytics");
        assertThat(dbCollection).isNotNull();

        dbCollection = connectionMongo.getDBCollection("bi_recomendator");
        assertThat(dbCollection).isNotNull();

        connectionMongo.endConnection();
    }
}
