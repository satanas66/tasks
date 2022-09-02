package mercado.candidato.jpa.pa;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Thv_Actividad_Comercial_Cons_V2ProjectionTest {

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Thv_Actividad_Comercial_Cons_V2Projection thv_actividad_comercial_cons_v2Projection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getPAEntityManager();
        thv_actividad_comercial_cons_v2Projection = new Thv_Actividad_Comercial_Cons_V2Projection(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.close();
    }

    @Test
    public void getProjectionThv_Actividad_Comercial_Cons_V2ByClientCode() {
        Object[] result = thv_actividad_comercial_cons_v2Projection.getProjectionThv_Actividad_Comercial_Cons_V2ByClientCode(271218752);
        assertThat(result).isNotNull();
        assertThat(result.length).isNotZero().isEqualTo(6);
    }

    @Test
    public void getLastCallIntoLastTwoMonths() {
        Integer result = thv_actividad_comercial_cons_v2Projection.getLastCallIntoLastTwoMonths(271218752);
        assertThat(result).isNotNull().isNotZero();
    }
}