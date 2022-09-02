package mercado.candidato.jpa.gestford;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Tsf_Csord__Service__CProjectionTest {

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Tsf_Csord__Service__CProjection tsf_csord__service__cProjection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getGestfordEntityManager();
        tsf_csord__service__cProjection = new Tsf_Csord__Service__CProjection(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.close();
    }

    @Test
    public void findIdByIdServicio() {
        String result = tsf_csord__service__cProjection.findIdByIdServicio("a1U3V0000009UBuUAM");
        assertThat(result).isNotNull().isEqualTo("S01165833");
    }
}