package recurrent.infraestructure;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import automation.factory.xlsx.Excel;
import com.mongodb.DBCollection;
import mongo.ConnectionMongo;
import mongo.ScrappingMessage;
import recurrent.mongo.ScrappingProjection;
import recurrent.mongo.WebVisibilityAnalyticsProjection;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExecuteScrapping {

    private static Logger LOG = Logger.getLogger(ExecuteScrapping.class);

    private static final String PATH = "C:/tasks/recurrent/src/main/resources/";

    private static ConnectionMongo connectionMongo;

    private static DBCollection scrappingDbCollection;

    private static ScrappingProjection scrappingProjection;

    public static void main(String[] arg) {
        setup();
        searchUrlsGMB();
    }

    public static void setup() {
        connectionMongo = new ConnectionMongo();
        connectionMongo.setMongoClientReplica();
        scrappingDbCollection = connectionMongo.getDBCollection("scrapping");
        scrappingProjection = new ScrappingProjection(scrappingDbCollection);
    }

    public static void searchUrlsGMB(){
        List<String> phones = Utils.generateListFromFile(PATH+"/select/", "precuenta_phones.txt");
        List<String> result = new ArrayList<>();
        for(String phone : phones){
            LOG.info(ScrappingMessage.SEARCH_CLIENT+" "+phone);
            String urlGMB = scrappingProjection.findGMBUrlByPhoneNumber(phone);
            result.add(phone+";"+urlGMB);
            LOG.info(ScrappingMessage.CLIENT_FOUND+" "+phone);
        }
        Text.generateTxtFileWithStrings(result, PATH+"/select/", "PHONES_AND_URLS_GMB.csv");
        connectionMongo.endConnection();
    }
}
