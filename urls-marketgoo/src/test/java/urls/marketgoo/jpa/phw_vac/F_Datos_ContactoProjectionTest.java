package urls.marketgoo.jpa.phw_vac;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class F_Datos_ContactoProjectionTest {

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static F_Datos_ContactoProjection f_datos_contactoProjection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getPHWVACEntityManager();
        f_datos_contactoProjection = new F_Datos_ContactoProjection(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.close();
    }

    @Test
    public void testFindAllClientCodes() {
        List<Integer> clientCodes = f_datos_contactoProjection.findAllClientCodes();
        assertThat(clientCodes.size()).isNotZero();
    }
}