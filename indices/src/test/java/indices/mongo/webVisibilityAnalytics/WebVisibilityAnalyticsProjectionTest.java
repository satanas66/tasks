package indices.mongo.webVisibilityAnalytics;

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
        connectionMongo = new ConnectionMongo("PRO", 1);
        dbCollection = connectionMongo.getDBCollection(COLLECTION_NAME);
        webVisibilityAnalyticsProjection = new WebVisibilityAnalyticsProjection(dbCollection);
    }

    @AfterAll
    public static void end(){
        connectionMongo.endConnection();
    }

//    @Test
//    public void getKpis303And23FromWebVisibilityAnalytics() {
//        Object[] result = webVisibilityAnalyticsProjection.getKpis303And23FromWebVisibilityAnalytics("270898513");
//        assertThat(result).isNotNull();
//        assertThat(result[0]).isEqualTo(9);
//        assertThat(result[1]).isEqualTo(null);
//    }
//
//    @Test
//    public void getErroresSeo() {
//        Object[] result = webVisibilityAnalyticsProjection.getErroresSeo("270898513");
//        assertThat(result).isNotNull();
//        assertThat(result.length).isNotZero().isEqualTo(13);
//    }

//    public Integer calculateErroresSeo(String clientCode){
//        Object[] projection = getErroresSeo(clientCode);
//        Integer result = 0;
//        for(int i=0; i < projection.length; i++){
//            if(projection[i] != null){
//                result++;
//            }
//        }
//        return result;
//    }
}