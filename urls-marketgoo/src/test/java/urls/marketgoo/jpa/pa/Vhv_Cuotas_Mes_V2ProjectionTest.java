package urls.marketgoo.jpa.pa;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Vhv_Cuotas_Mes_V2ProjectionTest {

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Vhv_Cuotas_Mes_V2Projection vhv_cuotas_mes_v2Projection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getPAEntityManager();
        vhv_cuotas_mes_v2Projection = new Vhv_Cuotas_Mes_V2Projection(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.close();
    }

    @Test
    public void testFindLivingCustomers(){
        String clientCode = vhv_cuotas_mes_v2Projection.findPotentialClient("69099851");
        assertThat(clientCode).isNull();
        clientCode = vhv_cuotas_mes_v2Projection.findPotentialClient("690XX851");
        assertThat(clientCode).isNotNull();
    }
}