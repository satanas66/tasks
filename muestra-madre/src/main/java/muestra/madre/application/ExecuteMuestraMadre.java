package muestra.madre.application;

import automation.factory.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 */
public class ExecuteMuestraMadre {

    private static Logger LOG = Logger.getLogger(ExecuteMuestraMadre.class);

    /**
     * Previa a la ejecución del proceso se deben tener en cuenta e cosas:
     * a) EL proceso Recomendador de paquetes se debe lanzar la última semana del mes actual o cómo muy tarde la primera semana del mes siguiente
     * b) Deben existir en el directorio resources los siguientes ficheros actualizados:
     *      1. actividades_a_excluir.txt (Solicitar a Rubén Coll el último actualizado)
     *      2. email_hunter.csv (Solicitar a Juan Blasco el último actualizado)
     *      3. client_and_resource_codes_before_deletion.csv (Son los códigos de clientes actuales y eliminados de la colección scrapping)
     *
     * @param arg
     */
    public static void main(String[] arg) {
        MuestraMadre muestraMadre = new MuestraMadre();
        execution(10, muestraMadre);
    }

    public static void execution(int threadNumber, MuestraMadre muestraMadre) {
        if (muestraMadre.isContinuaProceso()) {
            List<List<Integer>> clientCodes = muestraMadre.listsForExecutionByThreads(threadNumber);
            setUp(muestraMadre, clientCodes);
        } else {
            muestraMadre.closureOfConnections();
        }
    }

    public static void setUp(MuestraMadre muestraMadre, List<List<Integer>> clientCodes) {
        List<MuestraMadreThread> hilos = new ArrayList<>();
        for (int i = 0; i < clientCodes.size(); i++) {
            MuestraMadreThread hilo = new MuestraMadreThread(" Hilo" + (i + 1));
            hilo.instancePatameters(muestraMadre.PATH + "muestra-madre/", muestraMadre.FILENAME_MUESTRA_MADRE + (i + 1) + muestraMadre.EXTENSION);
            hilo.instanceAuditoria(muestraMadre.getMapaAuditoria());
            hilo.instancePhysycalResource(muestraMadre.getMapaEmailHunter(), muestraMadre.getActividadesExcluir());
            hilo.instanceOracleProjection(
                    muestraMadre.getF_datos_contactoProjection(),
                    muestraMadre.getF_impagoProjection(),
                    muestraMadre.getGestor_oportunidadesProjection(),
                    muestraMadre.getTsf_accountProjection(),
                    muestraMadre.getTsf_taskProjection(),
                    muestraMadre.getTsi_actvadProjection(),
                    muestraMadre.getTsi_emprvcaractProjection(),
                    muestraMadre.getTsi_f_webProjection(),
                    muestraMadre.getTsi_sectorProjection()
            );
            hilo.instanceMySqlProjection(muestraMadre.getTdv_oportunidades_por_clienteProjection(),
                    muestraMadre.getTdv_tramo_historico_alianzaProjection(),
                    muestraMadre.getThv_actividad_comercial_consProjection(),
                    muestraMadre.getVhv_cuotas_mes_v2Projection()
            );
            hilo.instanceMongoProjection(muestraMadre.getWebVisibilityAnalyticsProjection(),
                    muestraMadre.getBi_recomendatorProjection()
            );
            hilo.instanceListClientCodes(clientCodes.get(i));
            hilos.add(hilo);
        }
        hilos.forEach(Thread::start);
    }
}
