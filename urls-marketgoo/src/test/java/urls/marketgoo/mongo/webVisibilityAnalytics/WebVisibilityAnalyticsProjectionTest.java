package urls.marketgoo.mongo.webVisibilityAnalytics;

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
        connectionMongo = new ConnectionMongo();
        dbCollection = connectionMongo.getDBCollection(COLLECTION_NAME);
        webVisibilityAnalyticsProjection = new WebVisibilityAnalyticsProjection(dbCollection);
    }

    @AfterAll
    public static void end(){
        connectionMongo.endConnection();
    }

    @Test
    public void evaluateURLSendToMarketgoo() {
        boolean exist = webVisibilityAnalyticsProjection.evaluateURLSendToMarketgoo("15554165");
        /**
         * Existe el cliente y existe el campo marketgoo
         */
        assertThat(exist).isTrue();
        exist = webVisibilityAnalyticsProjection.evaluateURLSendToMarketgoo("15585656");
        /**
         * Existe el cliente y no existe el campo marketgoo
         */
        assertThat(exist).isTrue();
    }
}