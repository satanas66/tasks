//package indices.mongo.scrapping;
//
//import automation.factory.txt.Text;
//import com.mongodb.DBCollection;
//import indices.mongo.webVisibilityAnalytics.WebVisibilityAnalyticsProjection;
//import mongo.ConnectionMongo;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//public class ScrappingProjectionTest {
//
//    private static final String COLLECTION_NAME = "scrapping";
//
//    private static ConnectionMongo connectionMongo;
//
//    private static DBCollection dbCollection;
//
//    private static ScrappingProjection scrappingProjection;
//
//    @BeforeAll
//    public static void init(){
//        connectionMongo = new ConnectionMongo("PRO");
//        dbCollection = connectionMongo.getDBCollection(COLLECTION_NAME);
//        scrappingProjection = new ScrappingProjection(dbCollection);
//    }
//
//    @AfterAll
//    public static void end(){
//        connectionMongo.endConnection();
//    }
//
//    @Test
//    public void testFindRankingNumberAndClientCode() {
//        List<Object[]> result = scrappingProjection.findRankingNumberAndClientCode();
//        assertThat(result).isNotNull();
//        assertThat(result.size()).isNotZero();
//
//        Text.generateCsvFileFromObjectProjection(result, "C:/tasks/indices/src/main/resources/", "clienCodesScrapping2.csv");
//
//        List<String> lineas = new ArrayList<>();
//        for(Object[] array : result){
//            lineas.add(array[0]+","+array[1]);
//        }
//        Text.generateTxtFileWithStrings(lineas, "C:/tasks/indices/src/main/resources/", "clienCodesScrapping1.csv");
//
//    }
//}