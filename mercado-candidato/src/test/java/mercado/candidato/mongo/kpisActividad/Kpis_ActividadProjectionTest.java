package mercado.candidato.mongo.kpisActividad;

import com.mongodb.DBCollection;
import mongo.ConnectionMongo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Kpis_ActividadProjectionTest {

    private static final String COLLECTION_NAME = "kpis_actividad";

    private static ConnectionMongo connectionMongo;

    private static DBCollection dbCollection;

    private static Kpis_ActividadProjection kpis_actividadProjection;

    @BeforeAll
    public static void init(){
        connectionMongo = new ConnectionMongo();
        dbCollection = connectionMongo.getDBCollection(COLLECTION_NAME);
        kpis_actividadProjection = new Kpis_ActividadProjection(dbCollection);
    }

    @AfterAll
    public static void end(){
        connectionMongo.endConnection();
    }


    @Test
    public void getProjectionByCoActvad() {
        Object[] result = kpis_actividadProjection.getProjectionKpisActividadByCoActvad("12267");
        assertThat(result).isNotNull();
        assertThat(result.length).isNotZero().isEqualTo(2);
        assertThat(result[0]).isEqualTo("BTB");
        assertThat(result[1]).isEqualTo("Bienes");
    }
}