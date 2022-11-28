import application.Precuentas;
import application.PrecuentasThread;
import automation.factory.Logger;

import java.util.ArrayList;
import java.util.List;

public class PrecuentasExecute {
    private static Logger LOG = Logger.getLogger(PrecuentasExecute.class);
    /**
     * Previa a la ejecución del proceso se deben tener en cuenta e cosas:
     * a) El proceso Recomendador de paquetes se debe lanzar la última semana del mes actual o cómo muy tarde la primera semana del mes siguiente
     * b) Deben existir en el directorio resources los siguientes ficheros actualizados:
     * 1. actividades_a_excluir.txt (Solicitar a Rubén Coll el último actualizado)
     * 2. email_hunter.csv (Solicitar a Juan Blasco el último actualizado)
     * 3. client_and_resource_codes_before_deletion.csv (Son los códigos de clientes actuales y eliminados de la colección scrapping)
     *
     * @param arg
     */
    public static void main(String[] arg) {
        Precuentas precuentas = new Precuentas();
        precuentas.generateFileListsToExecution(1);
        execution(precuentas);
    }

    private static void execution(Precuentas precuentas) {
        if (precuentas.isContinuaProceso()) {
            List<List<Integer>> clientCodes = precuentas.getClientCodes("clientes_ficticios1.txt");
            setUp(precuentas, clientCodes);
        } else {
            precuentas.closureOfConnections();
        }
    }

    private static void setUp(Precuentas precuentas, List<List<Integer>> clientCodes){
        List<PrecuentasThread> hilos = new ArrayList<>();
        for (int i = 0; i < clientCodes.size(); i++) {
            PrecuentasThread hilo = new PrecuentasThread(" Hilo" + (i + 1));
            hilo.instancePatameters("C:/tasks/precuentas/src/main/resources/", "PRECUENTA_" + (i + 1) + ".xlsx");
            hilo.instanceMongoProjection(precuentas.getWebVisibilityAnalyticsProjection());
            hilo.instaceBasicBDLists(precuentas.getKpiseg400(), precuentas.getKpiseg401());
            hilo.instanceListClientCodes(clientCodes.get(i));
            hilos.add(hilo);
        }
        hilos.forEach(Thread::start);
    }
}
