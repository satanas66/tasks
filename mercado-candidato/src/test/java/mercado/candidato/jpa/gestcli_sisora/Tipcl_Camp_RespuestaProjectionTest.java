package mercado.candidato.jpa.gestcli_sisora;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Tipcl_Camp_RespuestaProjectionTest {

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Tipcl_Camp_RespuestaProjection tipcl_camp_respuestaProjection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getGestcliSisoraEntityManager();
        tipcl_camp_respuestaProjection = new Tipcl_Camp_RespuestaProjection(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.close();
    }

    @Test
    public void getRejectedModificationsByScrappingProcess() {
        List<Object[]> result = tipcl_camp_respuestaProjection.getRejectedModificationsByScrappingProcess();
        assertThat(result).isNotNull();
    }
}