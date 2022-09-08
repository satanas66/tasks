package mongo;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ConnectionMongoTest {

    private static ConnectionMongo connectionMongo = null;

    @Test
    public void getMongoClient() {
        connectionMongo = new ConnectionMongo("PRO");
        MongoClient mongoClient = connectionMongo.getMongoClient();
        assertThat(mongoClient).isNotNull();
        connectionMongo.endConnection();

        connectionMongo = new ConnectionMongo("DES");
        mongoClient = connectionMongo.getMongoClient();
        assertThat(mongoClient).isNotNull();
        connectionMongo.endConnection();
    }


    @Test
    public void getDBCollection() {
        connectionMongo = new ConnectionMongo("PRO");
        DBCollection dbCollection = connectionMongo.getDBCollection("webVisibilityAnalytics");
        assertThat(dbCollection).isNotNull();

        dbCollection = connectionMongo.getDBCollection("bi_recomendator");
        assertThat(dbCollection).isNotNull();

        connectionMongo.endConnection();

        connectionMongo = new ConnectionMongo("DES");
        dbCollection = connectionMongo.getDBCollection("kpis_tipol_actividad");
        assertThat(dbCollection).isNotNull();
        connectionMongo.endConnection();
    }
}
