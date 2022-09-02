package mercado.candidato.jpa.pa;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Tdp_Yext_PublicacionProjectionTest {

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Tdp_Yext_PublicacionProjection tdp_yext_publicacionProjection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getPAEntityManager();
        tdp_yext_publicacionProjection = new Tdp_Yext_PublicacionProjection(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.close();
    }

    @Test
    public void findEstadosFacebookAndGMB() {
        List<String> result = tdp_yext_publicacionProjection.findEstadosFacebookAndGMB("S01153078");
        assertThat(result).isNotNull();
    }
}