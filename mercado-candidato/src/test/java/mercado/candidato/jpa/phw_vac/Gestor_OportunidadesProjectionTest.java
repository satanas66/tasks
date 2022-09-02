package mercado.candidato.jpa.phw_vac;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Gestor_OportunidadesProjectionTest {

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Gestor_OportunidadesProjection gestor_oportunidadesProjection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getPHWVACEntityManager();
        gestor_oportunidadesProjection = new Gestor_OportunidadesProjection(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.close();
    }

    @Test
    public void findProjectionEGestorOportunidades() {
        Object[] result = gestor_oportunidadesProjection.findProjectionEGestorOportunidades(256991936);
        assertThat(result).isNotNull();
        assertThat(result.length).isNotZero().isEqualTo(2);
    }
}