package recurrent.application;

import automation.factory.Logger;
import recurrent.tasks.GestorOportunidadesTasks;

public class ExecuteGestorOportunidades {

    private static Logger LOG = Logger.getLogger(ExecuteGestorOportunidades.class);

    private static final String PATH = "C:/tasks/recurrent/src/main/resources/insert/";

    public static void main(String[] args) {
        insert();
    }

    private static void insert(){
        LOG.info("Empieza la actualización de la tabla GESTOR_PRECUENTAS...");
        GestorOportunidadesTasks gestorOportunidadesTasks = new GestorOportunidadesTasks();
        gestorOportunidadesTasks.insertGestorOportunidadesFromFile(PATH, "gestor_oportunidades.csv");
        gestorOportunidadesTasks.closureOfConnections();
        LOG.info("Fin de la actualización de la tabla GESTOR_PRECUENTAS...");
    }
}
