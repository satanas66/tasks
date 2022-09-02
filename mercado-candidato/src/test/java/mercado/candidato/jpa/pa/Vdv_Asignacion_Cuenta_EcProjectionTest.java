package mercado.candidato.jpa.pa;

import jpa.ConnectionJpa;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Vdv_Asignacion_Cuenta_EcProjectionTest {

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Vdv_Asignacion_Cuenta_EcProjection vdv_asignacion_cuenta_ecProjection;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getPAEntityManager();
        vdv_asignacion_cuenta_ecProjection = new Vdv_Asignacion_Cuenta_EcProjection(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.close();
    }

    @Test
    public void getCodVendedorAndCanalByClientCode() {
        Object[] result = vdv_asignacion_cuenta_ecProjection.getCodVendedorAndCanalByClientCode(267583821);
        assertThat(result.length).isEqualTo(2);
        assertThat(result[0]).isEqualTo("5252");
        assertThat(result[1]).isEqualTo("AC");
    }
}