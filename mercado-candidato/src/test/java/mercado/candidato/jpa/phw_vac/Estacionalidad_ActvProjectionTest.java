package mercado.candidato.jpa.phw_vac;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class Estacionalidad_ActvProjectionTest {
    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Estacionalidad_ActvProjection estacionalidad_actvProjection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getPHWVACEntityManager();
        estacionalidad_actvProjection = new Estacionalidad_ActvProjection(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.close();
    }

    @Test
    public void getEstacionalidadByClientCode() {
        String result = estacionalidad_actvProjection.getEstacionalidadByClientCode(2);
        assertThat(result).isNotEmpty().isEqualTo("MAR* MAY*");
    }
}