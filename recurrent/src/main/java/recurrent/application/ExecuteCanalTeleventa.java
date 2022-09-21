package recurrent.application;

import automation.factory.Logger;
import recurrent.tasks.CancelacionesTeleventaTasks;

public class ExecuteCanalTeleventa {

    private static Logger LOG = Logger.getLogger(ExecuteCanalTeleventa.class);

    public static void main(String[] args) {
        LOG.info("Empieza la búsqueda de datos...");
        CancelacionesTeleventaTasks cancelacionesTeleventaTasks = new CancelacionesTeleventaTasks();
        cancelacionesTeleventaTasks.searchInformationToTelesalesChannel();
        cancelacionesTeleventaTasks.closureOfConnections();
        LOG.info("Fin de la búsqueda de datos");
    }
}
