package mercado.candidato.jpa.sisora;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

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
    public void findCtMerClieByCoActvad() {
        String result = tsi_actvadProjection.findCtMerClieByCoActvad(85);
        assertThat(result).isNotNull().isNotEmpty();
    }
}