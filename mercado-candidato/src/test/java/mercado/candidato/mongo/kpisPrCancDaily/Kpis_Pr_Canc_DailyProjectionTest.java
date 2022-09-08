package mercado.candidato.mongo.kpisPrCancDaily;

import com.mongodb.DBCollection;
import mongo.ConnectionMongo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Kpis_Pr_Canc_DailyProjectionTest {

    private static final String COLLECTION_NAME = "kpis_pr_canc_daily";

    private static ConnectionMongo connectionMongo;

    private static DBCollection dbCollection;

    private static Kpis_Pr_Canc_DailyProjection kpis_pr_canc_dailyProjection;

    @BeforeAll
    public static void init(){
        connectionMongo = new ConnectionMongo("PRO");
        dbCollection = connectionMongo.getDBCollection(COLLECTION_NAME);
        kpis_pr_canc_dailyProjection = new Kpis_Pr_Canc_DailyProjection(dbCollection);
    }

    @AfterAll
    public static void end(){
        connectionMongo.endConnection();
    }

    @Test
    public void getPrCancByClienCode() {
        Double result = kpis_pr_canc_dailyProjection.getPrCancByClienCode(129346);
        assertThat(result).isNotNull().isEqualTo(10.905);
    }
}