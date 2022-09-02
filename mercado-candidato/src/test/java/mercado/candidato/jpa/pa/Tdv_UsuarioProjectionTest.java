package mercado.candidato.jpa.pa;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Tdv_UsuarioProjectionTest {

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Tdv_UsuarioProjection tdv_usuarioProjection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getPAEntityManager();
        tdv_usuarioProjection = new Tdv_UsuarioProjection(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.close();
    }

    @Test
    public void findEmployeeInformation() {
        Object[] result = tdv_usuarioProjection.findEmployeeInformation("2502");
        assertThat(result).isNotNull();
        assertThat(result[0]).isNotNull().isEqualTo("Marta Garcia Fortes");
        assertThat((result[1])).isNotNull().isEqualTo("VENDEDOR VD ORENSE");
    }
}