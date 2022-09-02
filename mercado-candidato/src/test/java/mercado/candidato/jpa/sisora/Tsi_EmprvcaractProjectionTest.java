package mercado.candidato.jpa.sisora;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Tsi_EmprvcaractProjectionTest {

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Tsi_EmprvcaractProjection tsi_emprvcaractProjection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getSisoraEntityManager();
        tsi_emprvcaractProjection = new Tsi_EmprvcaractProjection(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.close();
    }

    @Test
    public void finNumeroEmpleadosByCoEmpresa() {
        Integer result = tsi_emprvcaractProjection.finNumeroEmpleadosByCoEmpresa(844);
        assertThat(result).isNotNull().isEqualTo(8);
    }
}