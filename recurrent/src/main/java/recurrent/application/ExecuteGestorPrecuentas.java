package recurrent.application;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import recurrent.jpa.phw_vac.Gestor_PrecuentasJpa;
import recurrent.tasks.GestorPrecuentasTasks;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class ExecuteGestorPrecuentas {

    private static Logger LOG = Logger.getLogger(ExecuteGestorPrecuentas.class);

    private static final String PATH = "C:/tasks/recurrent/src/main/resources/update/";

    private static EntityManager entityManager;

    public static void main(String[] args) {
        update();
    }

    private static void update() {
        LOG.info("Empieza la actualización de la tabla GESTOR_PRECUENTAS...");

        List<String> lines = Utils.generateListFromFile(PATH, "plantilla_update_gestor_precuentas_20092022_3.csv");
        List<String> errorLines = new ArrayList<>();

        int count = 0;
        GestorPrecuentasTasks gestorPrecuentasTasks = new GestorPrecuentasTasks();
        entityManager = gestorPrecuentasTasks.getEntityManager();
        entityManager.getTransaction().begin();
        Gestor_PrecuentasJpa gestor_precuentasJpa = gestorPrecuentasTasks.getGestor_precuentasJpa();

        for (String line : lines) {
            try {
                boolean result = gestor_precuentasJpa.updateGestorPrecuentasByClienteFicticioAndEstadoReg(line);
                if (!result) {
                    errorLines.add(line);
                }
                count++;
                if (count == 370) {
                    count = 0;
                    entityManager.getTransaction().commit();
                    gestorPrecuentasTasks.closureOfConnections();
                    Thread.sleep(5 * 1000);
                    gestorPrecuentasTasks = new GestorPrecuentasTasks();
                    entityManager = gestorPrecuentasTasks.getEntityManager();
                    entityManager.getTransaction().begin();
                    gestor_precuentasJpa = gestorPrecuentasTasks.getGestor_precuentasJpa();
                }
            } catch (Exception e) {
                LOG.info("Ha ocurrido un error con la línea: "+line+"=>"+e.getMessage());
            }
        }
        entityManager.getTransaction().commit();
        gestorPrecuentasTasks.closureOfConnections();
        Text.generateTxtFileWithStrings(errorLines, PATH, "errores_14092022.csv");

        LOG.info("Fin de la actualización de la tabla GESTOR_PRECUENTAS...");
    }


}
