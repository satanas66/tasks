package recurrent.infraestructure;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import recurrent.domain.Gestor_OportunidadesJpa;
import recurrent.tasks.GestorOportunidadesTasks;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class ExecuteGestorOportunidades {

    private static Logger LOG = Logger.getLogger(ExecuteGestorOportunidades.class);

    private static final String PATH = "C:/tasks/recurrent/src/main/resources/insert/";

    private static EntityManager entityManager;

    public static void main(String[] args) {
        insert();
    }

    private static void insert(){
        LOG.info("Empieza la inserción de datos en la tabla GESTOR_OPORTUNIDADES...");
        List<String> lines = Utils.generateListFromFile(PATH, "carga_oportunidades_18112022.csv");
        List<String> errorLines = new ArrayList<>();

        int count = 0;
        GestorOportunidadesTasks gestorOportunidadesTasks = new GestorOportunidadesTasks();
        entityManager = gestorOportunidadesTasks.getEntityManager();
        entityManager.getTransaction().begin();
        Gestor_OportunidadesJpa gestor_oportunidadesJpa = gestorOportunidadesTasks.getGestor_oportunidadesJpa();

        for(String line : lines){
            try{
                boolean result = gestor_oportunidadesJpa.insertGestor_Oportunidades(line);
                if(!result){
                    errorLines.add(line);
                }
                count++;
                if (count == 370) {
                    count = 0;
                    entityManager.getTransaction().commit();
                    gestorOportunidadesTasks.closureOfConnections();
                    Thread.sleep(5 * 1000);
                    gestorOportunidadesTasks = new GestorOportunidadesTasks();
                    entityManager = gestorOportunidadesTasks.getEntityManager();
                    entityManager.getTransaction().begin();
                    gestor_oportunidadesJpa = gestorOportunidadesTasks.getGestor_oportunidadesJpa();
                }
            }catch (Exception e){
                LOG.info("Ha ocurrido un error con la línea: "+line+"=>"+e.getMessage());
            }
        }
        entityManager.getTransaction().commit();
        gestorOportunidadesTasks.closureOfConnections();
        Text.generateTxtFileWithStrings(errorLines, PATH, "errores_15092022");
        LOG.info("Fin de la inserción de datos en la tabla GESTOR_OPORTUNIDADES.");
    }
}
