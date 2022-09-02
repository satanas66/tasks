package urls.marketgoo.jpa.pa;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Thv_Actividad_Comercial_ConsProjectionTest {

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Thv_Actividad_Comercial_ConsProjection thvActividadComercialConsProjection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getPAEntityManager();
        thvActividadComercialConsProjection = new Thv_Actividad_Comercial_ConsProjection(entityManager);
    }

    @Test
    public void findUltimaAnotacionLlamada() {
        Date ultimaLlamada = thvActividadComercialConsProjection.findUltimaAnotacionLlamada("269708673");
        assertThat(ultimaLlamada).isNotNull();
    }

}