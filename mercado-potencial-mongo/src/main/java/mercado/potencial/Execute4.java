package mercado.potencial;

import automation.factory.Logger;
import mercado.potencial.application.MercadoPotencial;
import mercado.potencial.application.MercadoPotencialThread;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 */
public class Execute4 {

    private static Logger LOG = Logger.getLogger(Execute4.class);

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
        MercadoPotencial mercadoPotencial = new MercadoPotencial();
        execution(mercadoPotencial);
    }

    private static void execution(MercadoPotencial mercadoPotencial) {
        if (mercadoPotencial.isContinuaProceso()) {
            List<List<Integer>> clientCodes = mercadoPotencial.getClientCodes("clientCodes4.txt");
            setUp(mercadoPotencial, clientCodes);
        } else {
            mercadoPotencial.closureOfConnections();
        }
    }

    private static void setUp(MercadoPotencial mercadoPotencial, List<List<Integer>> clientCodes) {
        List<MercadoPotencialThread> hilos = new ArrayList<>();
        for (int i = 0; i < clientCodes.size(); i++) {
            MercadoPotencialThread hilo = new MercadoPotencialThread(" Hilo4");
            hilo.instancePatameters("C:/tasks/mercado-potencial-mongo/src/main/resources/", "MERCADO_POTENCIAL_4.xlsx");
            hilo.instanceMongoProjection(mercadoPotencial.getWebVisibilityAnalyticsProjection(),
                    mercadoPotencial.getBi_recomendatorProjection()
            );
            hilo.instaceBasicBDLists(mercadoPotencial.getKpiseg400(), mercadoPotencial.getKpiseg401());
            hilo.instanceListClientCodes(clientCodes.get(i));
            hilos.add(hilo);
        }
        hilos.forEach(Thread::start);
    }
}
