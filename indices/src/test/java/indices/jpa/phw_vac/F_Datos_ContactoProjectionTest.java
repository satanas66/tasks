package indices.jpa.phw_vac;

import indices.jpa.sisora.Tsi_ActvadProjection;
import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
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
        Object[] result = f_datos_contactoProjection.findActivityCodeByclientCode(213382476);
        assertThat(result).isNotNull().isEqualTo(1);
    }
}