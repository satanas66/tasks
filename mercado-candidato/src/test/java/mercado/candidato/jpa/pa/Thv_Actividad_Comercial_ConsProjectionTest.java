package mercado.candidato.jpa.pa;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Thv_Actividad_Comercial_ConsProjectionTest {

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Thv_Actividad_Comercial_ConsProjection thv_actividad_comercial_consProjection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getPAEntityManager();
        thv_actividad_comercial_consProjection = new Thv_Actividad_Comercial_ConsProjection(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.close();
    }

    @Test
    void findUltimaAnotacionLlamada() {

        Date result = thv_actividad_comercial_consProjection.findUltimaAnotacionLlamada(249052457);
        assertThat(result).isNotNull();
    }
}