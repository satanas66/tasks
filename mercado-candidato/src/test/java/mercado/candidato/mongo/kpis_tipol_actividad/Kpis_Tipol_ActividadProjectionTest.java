package mercado.candidato.mongo.kpis_tipol_actividad;

import com.mongodb.DBCollection;
import mercado.candidato.mongo.kpisActividad.Kpis_ActividadProjection;
import mongo.ConnectionMongo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class Kpis_Tipol_ActividadProjectionTest {

    private static final String COLLECTION_NAME = "kpis_tipol_actividad";

    private static ConnectionMongo connectionMongo;

    private static DBCollection dbCollection;

    private static Kpis_Tipol_ActividadProjection kpis_tipol_actividadProjection;

    @BeforeAll
    public static void init(){
        connectionMongo = new ConnectionMongo("DES");
        dbCollection = connectionMongo.getDBCollection(COLLECTION_NAME);
        kpis_tipol_actividadProjection = new Kpis_Tipol_ActividadProjection(dbCollection);
    }

    @AfterAll
    public static void end(){
        connectionMongo.endConnection();
    }


    @Test
    public void getQ_RK_SCOREByCoActvad() {
        Double result = kpis_tipol_actividadProjection.getQ_RK_SCOREByCoActvad("1");
        assertThat(result).isNotNull().isNotZero();
        assertThat(result).isEqualTo(0.705620714);
    }
}