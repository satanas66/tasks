package potential.market.application;

import automation.factory.Logger;
import automation.factory.xlsx.Excel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 */
public class ExecuteMercadoNoWebAnalytics {

    private static Logger LOG = Logger.getLogger(ExecuteMercadoNoWebAnalytics.class);

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
//        MercadoNoWebAnalytics mercadoNoWebAnalytics = new MercadoNoWebAnalytics();
//        execution(10, mercadoNoWebAnalytics);
//        combineFiles();
//        combineSheets();
    }

    private static void execution(int threadNumber, MercadoNoWebAnalytics mercadoNoWebAnalytics) {
        if (mercadoNoWebAnalytics.isContinuaProceso()) {
            List<List<Integer>> clientCodes = mercadoNoWebAnalytics.listsForExecutionByThreads(threadNumber);
            setUp(mercadoNoWebAnalytics, clientCodes);
        } else {
            mercadoNoWebAnalytics.closureOfConnections();
        }
    }

    private static void setUp(MercadoNoWebAnalytics mercadoNoWebAnalytics, List<List<Integer>> clientCodes) {
        List<MercadoNoWebAnalyticsThread> hilos = new ArrayList<>();
        for (int i = 0; i < clientCodes.size(); i++) {
            MercadoNoWebAnalyticsThread hilo = new MercadoNoWebAnalyticsThread(" Hilo" + (i + 1));
            hilo.instancePatameters(mercadoNoWebAnalytics.PATH, mercadoNoWebAnalytics.FILENAME_MERCADO_POTENCIAL + (i + 1) + mercadoNoWebAnalytics.EXTENSION);
                 hilo.instanceOracleProjection(
                    mercadoNoWebAnalytics.getF_datos_contactoProjection(),
                    mercadoNoWebAnalytics.getTsi_actvadProjection(),
                    mercadoNoWebAnalytics.getTsi_f_webProjection(),
                    mercadoNoWebAnalytics.getTsi_sectorProjection()
            );
            hilo.instanceListClientCodes(clientCodes.get(i));
            hilos.add(hilo);
        }
        hilos.forEach(Thread::start);
    }

    public static void combineFiles() {
        LOG.info("Inicio de la combinación de los ficheros MERCADO_NO_WEB_ANALYTICS...");
        String path = "C:/tasks/mercado-no-web-analytics/src/main/resources/";
        Excel.mergeExcelDocuments(path, "MERCADO_NO_WEB_ANALYTICS_", "MERCADO_NO_WEB_ANALYTICS.xlsx");
        LOG.info("Final de la combinación de los ficheros MERCADO_POTENCIAL_X.");
    }

    private static void combineSheets() {
        LOG.info("Inicio de la combinación de las hojas del fichero MERCADO_NO_WEB_ANALYTICS...");
        String path = "C:/tasks/mercado-no-web-analytics/src/main/resources/";
        Excel.combineSheets(path, "MERCADO_NO_WEB_ANALYTICS.xlsx", "MERCADO_NO_WEB_ANALYTICS_DEST.xlsx");
        LOG.info("Final de la combinación de las hojas del fichero MERCADO_POTENCIAL_X.");

    }

}
