package indices.jpa.phw_vac;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import java.util.Arrays;
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
    public void findActivityCodeByclientCode() {
        Integer result = f_datos_contactoProjection.findActivityCodeByclientCode(213382476);
        assertThat(result).isNotNull();
    }

    @Test
    public void getProjectionFromF_Datos_ContactoToMuestraMadreByClientCode() {
        String result = f_datos_contactoProjection.getProjectionFromF_Datos_ContactoToMuestraMadreByClientCode(213382476);
        assertThat(result).isNotEmpty();
    }

    @Test
    void getProjectionFromF_Datos_ContactoToMuestraMadreByListClientCode() {
        List<Integer> clientCodes = Arrays.asList(198314551, 198314494, 198314304, 198314296, 198314247);
        f_datos_contactoProjection.getProjectionFromF_Datos_ContactoToMuestraMadreByListClientCode(clientCodes);
    }
}