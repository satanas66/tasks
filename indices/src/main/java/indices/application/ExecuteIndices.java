package indices.application;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 */
public class ExecuteIndices {

    /**
     * Previa a la ejecución del proceso se deben obtener los códigos de los clientes a buscar.     *
     * @param args
     */
    public static void main(String[] args) {
        Indices indices = new Indices();
        int threadNumber = 10;

//        List<IndicesThread> hilos = instanceThreads(indices, threadNumber);
//        hilos.forEach(Thread::start);
//
//        while (hilos.get(0).isAlive() || hilos.get(1).isAlive() || hilos.get(2).isAlive() ||
//                hilos.get(3).isAlive() || hilos.get(4).isAlive() || hilos.get(5).isAlive() ||
//                hilos.get(6).isAlive() || hilos.get(7).isAlive() || hilos.get(8).isAlive() ||
//                hilos.get(9).isAlive());

        indices.searchAndAsociationInformationFromOracle(threadNumber);
        indices.generateFileIndiceVisibilidad(threadNumber);
        indices.closureOfConnections();
    }

    /**
     * Método que instancia una una lista con clases IndiceThread para la generación de proyecciones que aglutinan datos desde Oracle y MongoDB
     * @param indices
     * @param threadNumber
     * @return lista de IndiceThread
     */
    public static List<IndicesThread> instanceThreads(Indices indices, int threadNumber) {
        List<IndicesThread> result = new ArrayList<>();
        List<List<Integer>> clientCodes = indices.listsForExecutionByThreads(threadNumber);
        for (int i = 0; i < clientCodes.size(); i++) {
            IndicesThread hilo = new IndicesThread(" Hilo" + (i + 1));
            hilo.instancePatameters(indices.PATH, indices.PROYECCION + (i + 1) + indices.EXTENSION);
            hilo.instanceOracleProjection(indices.getF_datos_contactoProjection(), indices.getTsi_actvadProjection());
            hilo.instanceMongoProjection(indices.getWebVisibilityAnalyticsProjection());
            hilo.instanceListClientCodes(clientCodes.get(i));
            result.add(hilo);
        }
        return result;
    }
}
