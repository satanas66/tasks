package mercado.candidato.mongo.webVisibilityAnalytics;

import com.mongodb.DBCollection;
import mongo.ConnectionMongo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class WebVisibilityAnalyticsProjectionTest {

    private static final String COLLECTION_NAME = "webVisibilityAnalytics";

    private static ConnectionMongo connectionMongo;

    private static DBCollection dbCollection;

    private static WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection;

    @BeforeAll
    public static void init(){
        connectionMongo = new ConnectionMongo();
        dbCollection = connectionMongo.getDBCollection(COLLECTION_NAME);
        webVisibilityAnalyticsProjection = new WebVisibilityAnalyticsProjection(dbCollection);
    }

    @AfterAll
    public static void end(){
        connectionMongo.endConnection();
    }

    @Test
    public void getProjectionFromWebVisibilityAnalytics() {
        Object[] result = webVisibilityAnalyticsProjection.getProjectionFromWebVisibilityAnalytics("6931349");
        assertThat(result).isNotNull();

    }
}