package recurrent.update.application;

import automation.factory.Logger;

public class GestorPrecuentasExecute {

    private static Logger LOG = Logger.getLogger(GestorPrecuentasExecute.class);

    public static void main(String[] args) {
        update();
//        updateError();
    }

    private static void update(){
        LOG.info("Empieza la actualización de la tabla GESTOR_PRECUENTAS...");
        GestorPrecuentasTasks gestorPrecuentasTasks = new GestorPrecuentasTasks();
        gestorPrecuentasTasks.updateGestorPrecuentasFromFile();
        gestorPrecuentasTasks.closureOfConnections();
        LOG.info("Fin de la actualización de la tabla GESTOR_PRECUENTAS...");
    }

    private static void updateError(){
        LOG.info("Empieza la actualización de la tabla GESTOR_PRECUENTAS...");
        GestorPrecuentasTasks gestorPrecuentasTasks = new GestorPrecuentasTasks();
        gestorPrecuentasTasks.updateGestorPrecuentasErrorFromFile();
        gestorPrecuentasTasks.closureOfConnections();
        LOG.info("Fin de la actualización de la tabla GESTOR_PRECUENTAS...");
    }
}
