package recurrent.update.jpa.phw_vac;

import automation.factory.Utils;
import jpa.ConnectionJpa;
import jpa.entity.phw_vac.Gestor_Precuentas;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import recurrent.jpa.phw_vac.Gestor_PrecuentasJpa;
import recurrent.jpa.phw_vac.Gestor_PrecuentasJpaImpl;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Gestor_PrecuentasJpaImplTest {

    private static final String PATH = "C:/tasks/recurrent/src/main/resources/update/";

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Gestor_PrecuentasJpa gestor_precuentasJpa;

    @BeforeAll
    public static void init(){
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getPHWVACEntityManager();
        entityManager.getTransaction().begin();
        gestor_precuentasJpa = new Gestor_PrecuentasJpaImpl(entityManager);
    }

    @AfterAll
    public static void end(){
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void findByByClienteFicticioAndEstadoReg() {
        Gestor_Precuentas result = gestor_precuentasJpa.findByByClienteFicticioAndEstadoReg(976976606, "PENDIENTE");
        assertThat(result).isNotNull();
    }

    @Test
    public void updateGestorPrecuentasByClienteFicticioAndEstadoReg() {
        List<String> lines = Utils.generateListFromFile(PATH, "gestor_precuentas.csv");
        for(int i=0; i<lines.size(); i++){
            String line = lines.get(i);
           gestor_precuentasJpa.updateGestorPrecuentasByClienteFicticioAndEstadoReg(line);
        }
    }
}