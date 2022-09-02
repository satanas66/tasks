package mercado.candidato.jpa.gestford;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Tsf_AccountProjectionTest {

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Tsf_AccountProjection tsf_AccountProjection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getGestfordEntityManager();
        tsf_AccountProjection = new Tsf_AccountProjection(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.close();
    }

    @Test
    public void findProjectionECuenta() {
        Object[] result = tsf_AccountProjection.findProjectionECuenta(13703426, "B96933742");
        assertThat(result).isNotNull();
        assertThat(result.length).isEqualTo(5);
    }
}