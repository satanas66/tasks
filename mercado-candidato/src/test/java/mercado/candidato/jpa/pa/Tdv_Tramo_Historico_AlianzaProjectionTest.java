package mercado.candidato.jpa.pa;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Tdv_Tramo_Historico_AlianzaProjectionTest {

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Tdv_Tramo_Historico_AlianzaProjection tdv_tramo_historico_alianzaProjection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getPAEntityManager();
        tdv_tramo_historico_alianzaProjection = new Tdv_Tramo_Historico_AlianzaProjection(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.close();
    }

    @Test
    public void findAlianzaByClientCode() {
        String result = tdv_tramo_historico_alianzaProjection.findAlianzaByClientCode(2741);
        assertThat(result).isNotNull().isNotEmpty();
    }
}