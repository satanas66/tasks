package recomendador.paquetes.mongo.webVisibilityAnalytics;

import com.mongodb.DBCollection;
import mongo.ConnectionMongo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WebVisibilityAnalyticsProjectionTest {

    private static final String COLLECTION_NAME = "webVisibilityAnalytics";

    private static ConnectionMongo connectionMongo;

    private static DBCollection dbCollection;

    private static WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection;

    @BeforeAll
    public static void init(){
        connectionMongo = new ConnectionMongo("PRO");
        dbCollection = connectionMongo.getDBCollection(COLLECTION_NAME);
        webVisibilityAnalyticsProjection = new WebVisibilityAnalyticsProjection(dbCollection);
    }

    @AfterAll
    public static void end(){
        connectionMongo.endConnection();
    }

    @Test
    void testFindProjectionToRecomendadorByClientCode() {

        Object[] result = webVisibilityAnalyticsProjection.findProjectionToRecomendadorByClientCode("10000024");
        assertThat(result).isNotNull();
        assertThat(result[0]).isNotNull();
        assertThat(result[2]).isNotNull();
        assertThat(result[3]).isNotNull();

    }

}