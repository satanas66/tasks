package mercado.candidato.application;

import automation.factory.Logger;
import automation.factory.xlsx.Excel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 */
public class ExecuteMercadoCandidato {

    private static Logger LOG = Logger.getLogger(ExecuteMercadoCandidato.class);

    /**
     * Previa a la ejecución del proceso se deben tener en cuenta que deben existir en el directorio resources los siguientes ficheros actualizados:
     * 1. actividades_a_excluir.txt (Solicitar a Rubén Coll el último actualizado)
     * 2. email_hunter.csv (Solicitar a Juan Blasco el último actualizado)
     * 3. ultima_visita.csv (Solicitar a Javier Muñoz el último actualizado) => \\ph.inet\datos\pce\EDWIN\CLICKS acum.csv
     *
     * @param arg
     */
    public static void main(String[] arg) {
        MercadoCandidato mercadoCandidato = new MercadoCandidato();
        execution(10, mercadoCandidato, mercadoCandidato.FILENAME_BIPA);
//        combineFiles();
//        combineSheets();
    }

    public static void execution(int threadNumber, MercadoCandidato mercadoCandidato, String fileName) {
        if (mercadoCandidato.isContinuaProceso()) {
            List<List<Integer>> clientCodes = mercadoCandidato.listsForExecutionByThreads(threadNumber, mercadoCandidato.PATH, fileName);
            setUp(mercadoCandidato, clientCodes);
        } else {
            mercadoCandidato.closureOfConnections();
        }
    }

    public static void setUp(MercadoCandidato mercadoCandidato, List<List<Integer>> clientCodes) {
        List<MercadoCandidatoThread> hilos = new ArrayList<>();
        for (int i = 0; i < clientCodes.size(); i++) {
            MercadoCandidatoThread hilo = new MercadoCandidatoThread(" Hilo" + (i + 1));
            hilo.instancePatameters(mercadoCandidato.PATH + "mercado-candidato/", mercadoCandidato.FILENAME_MERCADO_CANDIDATO + (i + 1) + mercadoCandidato.EXTENSION);
            hilo.instancePhysycalResource(mercadoCandidato.getMapaEmailHunter(), mercadoCandidato.getActividadesExcluir(), mercadoCandidato.getMapaVisitas());
            hilo.instanceOracleProjection(
                    mercadoCandidato.getF_datos_contactoProjection(),
                    mercadoCandidato.getF_impagoProjection(),
                    mercadoCandidato.getGestor_oportunidadesProjection(),
                    mercadoCandidato.getTsf_accountProjection(),
                    mercadoCandidato.getTsf_taskProjection(),
                    mercadoCandidato.getTsi_actvadProjection(),
                    mercadoCandidato.getTsi_emprvcaractProjection(),
                    mercadoCandidato.getTsi_f_webProjection(),
                    mercadoCandidato.getTsi_sectorProjection(),
                    mercadoCandidato.getEstacionalidad_actvProjection(),
                    mercadoCandidato.getTsf_csord__service__cProjection()
            );
            hilo.instanceMySqlProjection(mercadoCandidato.getTdv_oportunidades_por_clienteProjection(),
                    mercadoCandidato.getTdv_tramo_historico_alianzaProjection(),
                    mercadoCandidato.getThv_actividad_comercial_consProjection(),
                    mercadoCandidato.getVhv_cuotas_mes_v2Projection(),
                    mercadoCandidato.getThv_actividad_comercial_cons_v2Projection(),
                    mercadoCandidato.getVdv_asignacion_cuenta_ecProjection(),
                    mercadoCandidato.getTdv_usuarioProjection(),
                    mercadoCandidato.getTdp_yext_publicacionProjection()
            );
            hilo.instanceMongoProjection(mercadoCandidato.getWebVisibilityAnalyticsProjection(),
                    mercadoCandidato.getBi_recomendatorProjection(),
                    mercadoCandidato.getKpis_ActividadProjection(),
                    mercadoCandidato.getKpis_calculadosProjection(),
                    mercadoCandidato.getKpis_pr_canc_dailyProjection()
            );
            hilo.instanceListClientCodes(clientCodes.get(i));
            hilos.add(hilo);
        }
        hilos.forEach(Thread::start);
    }

    public static void combineFiles() {
        LOG.info("Inicio de la combinación de los ficheros MERCADO_CANDIDATO_X...");
        String path = "C:/tasks/mercado-candidato/src/main/resources/mercado-candidato/";
        Excel.mergeExcelDocuments(path, "MERCADO_CANDIDATO_", "MERCADO_CANDIDATO.xlsx");
        LOG.info("Final de la combinación de los ficheros MERCADO_CANDIDATO_X.");
    }

    private static void combineSheets() {
        LOG.info("Inicio de la combinación de las hojas del fichero MERCADO_CANDIDATO_X...");
        String path = "C:/tasks/mercado-candidato/src/main/resources/mercado-candidato/";
        Excel.combineSheets(path, "MERCADO_CANDIDATO.xlsx", "MERCADO_CANDIDATO_DEST.xlsx");
        LOG.info("Final de la combinación de las hojas del fichero MERCADO_CANDIDATO_X.");

    }
}
