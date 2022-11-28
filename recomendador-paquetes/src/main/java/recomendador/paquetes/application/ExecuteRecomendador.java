package recomendador.paquetes.application;

import automation.factory.Logger;

import java.util.ArrayList;
import java.util.List;

public class ExecuteRecomendador {

    private static Logger LOG = Logger.getLogger(ExecuteRecomendador.class);

    /**
     * El resultado de este proceso se debe enviar a Javier Muñoz
     * @param arg
     */
    public static void main(String[] arg) {
        /**
         * Previa a la ejecución del proyecto se debe obtener los códigos de clientes que se obtienen ejecutando el proceso muestra madre
         * En la primera parte de la ejecución del fichero muetra madre se generan los códigos de clientes que serán usados en este punto
         */
        LOG.info("Proceso multihilo para la generación de ficheros paquete recomendado lanzado...");
        Recomendador recomendador = new Recomendador();
        execution(10, recomendador);
    }

    public static void execution(int threadNumber, Recomendador recomendador) {
        if (recomendador.isContinuaProceso()) {
            List<List<String>> clientCodes = recomendador.listsForExecutionByThreads(threadNumber);
            setUp(recomendador, clientCodes);
        } else {
            recomendador.closureOfConnections();
        }
    }

    public static void setUp(Recomendador recomendador, List<List<String>> clientCodes) {
        List<RecomendadorThread> hilos = new ArrayList<>();
        for (int i = 0; i < clientCodes.size(); i++) {
            RecomendadorThread hilo = new RecomendadorThread(" Hilo" + (i + 1));
            hilo.instancePatameters(recomendador.PATH, recomendador.FILENAME_RECOMENDADOR_PAQUETES+ (i + 1) + recomendador.EXTENSION);
            hilo.instanceMongoProjection(recomendador.getWebVisibilityAnalyticsProjection());
            hilo.instanceListClientCodes(clientCodes.get(i));
            hilos.add(hilo);
        }
        hilos.forEach(Thread::start);
    }
}
