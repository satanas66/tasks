package urls.marketgoo.jpa.sisora;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Tsi_F_WebProjectionTest {

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Tsi_F_WebProjection tsi_f_webProjection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getSisoraEntityManager();
        tsi_f_webProjection = new Tsi_F_WebProjection(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.close();
    }

    @Test
    public void getOrigenUrlByCoCliente() {
        String result = tsi_f_webProjection.getOrigenUrlByCoCliente(59);
        assertThat(result).isNotNull().isNotEmpty().isEqualTo("BDC");
    }
}