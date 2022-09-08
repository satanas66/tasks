package mercado.candidato.mongo.kpisCalculados;

import com.mongodb.DBCollection;
import mongo.ConnectionMongo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Kpis_CalculadosProjectionTest {

    private static final String COLLECTION_NAME = "kpis_calculados";

    private static ConnectionMongo connectionMongo;

    private static DBCollection dbCollection;

    private static Kpis_CalculadosProjection kpis_calculadosProjection;

    @BeforeAll
    public static void init(){
        connectionMongo = new ConnectionMongo("PRO");
        dbCollection = connectionMongo.getDBCollection(COLLECTION_NAME);
        kpis_calculadosProjection = new Kpis_CalculadosProjection(dbCollection);
    }

    @AfterAll
    public static void end(){
        connectionMongo.endConnection();
    }

    @Test
    public void getProjectionKpisCalculadosByClientCode() {
        Object[] result = kpis_calculadosProjection.getProjectionKpisCalculadosByClientCode(216);
        assertThat(result).isNotNull();
        assertThat(result.length).isNotZero().isEqualTo(6);
    }
}