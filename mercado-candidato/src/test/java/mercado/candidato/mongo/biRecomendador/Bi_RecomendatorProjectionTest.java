package mercado.candidato.mongo.biRecomendador;

import com.mongodb.DBCollection;
import mongo.ConnectionMongo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Bi_RecomendatorProjectionTest {

    private static final String COLLECTION_NAME = "bi_Recomendador";

    private static ConnectionMongo connectionMongo;

    private static DBCollection dbCollection;

    private static Bi_RecomendatorProjection bi_recomendatorProjection;

    @BeforeAll
    public static void init(){
        connectionMongo = new ConnectionMongo();
        dbCollection = connectionMongo.getDBCollection(COLLECTION_NAME);
        bi_recomendatorProjection = new Bi_RecomendatorProjection(dbCollection);
    }

    @AfterAll
    public static void end(){
        connectionMongo.endConnection();
    }

    @Test
    public void findPaqueteRecomendadoByClientCode() {
        String result = bi_recomendatorProjection.findPaqueteRecomendadoByClientCode(1602);
        assertThat(result).isNotEmpty().isEqualTo("Paquete BeeDIGITAL DINAMIZADOR");

    }
}