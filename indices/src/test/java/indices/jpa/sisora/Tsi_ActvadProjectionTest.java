package indices.jpa.sisora;

import indices.application.Indices;
import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Tsi_ActvadProjectionTest {

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Tsi_ActvadProjection tsi_actvadProjection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getSisoraEntityManager();
        tsi_actvadProjection = new Tsi_ActvadProjection(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.close();
    }

    @Test
    public void getAllCoActvadGroupByCoSector() {
        List<Object[]> result = tsi_actvadProjection.getAllCoActvadGroupByCoSector();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(5938);
    }

    @Test
    public void generateMapCoSectorAndCoActvads() {
        List<Object[]> projections = tsi_actvadProjection.getAllCoActvadGroupByCoSector();
        Map<String, List<Integer>> result = null;
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(29);

    }
}