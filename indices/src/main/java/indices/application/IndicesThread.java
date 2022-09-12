package indices.application;

import automation.factory.Logger;
import automation.factory.txt.Text;
import indices.jpa.phw_vac.F_Datos_ContactoProjection;
import indices.jpa.sisora.Tsi_ActvadProjection;
import indices.mongo.webVisibilityAnalytics.WebVisibilityAnalyticsProjection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Edwin Patricio Arévalo Angulo
 */
public class IndicesThread extends Thread {

    private static Logger LOG = Logger.getLogger(IndicesThread.class);

    private String path;

    private String nameFile;

    private List<Integer> clientCodes;

    private F_Datos_ContactoProjection f_datos_contactoProjection;

    private Tsi_ActvadProjection tsi_actvadProjection;

    private WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection;

    public IndicesThread(String nombreHilo) {
        super(nombreHilo);
    }

    public void instancePatameters(String path, String nameFile) {
        this.path = path;
        this.nameFile = nameFile;
    }

    public void instanceListClientCodes(List<Integer> clientCodes) {
        this.clientCodes = clientCodes;
    }

    public void instanceOracleProjection(F_Datos_ContactoProjection f_datos_contactoProjection,
                                         Tsi_ActvadProjection tsi_actvadProjection) {
        this.f_datos_contactoProjection = f_datos_contactoProjection;
        this.tsi_actvadProjection = tsi_actvadProjection;
    }

    public void instanceMongoProjection(WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection) {
        this.webVisibilityAnalyticsProjection = webVisibilityAnalyticsProjection;
    }

    @Override
    public void run() {
        searchInformationFromOracleAndMongoDB();
    }

    /**
     * Método encargado de buscar el código de actividad, valor de la posición GMB, Número de keyword en Top10 para la posterior
     *  construcción de un fichero csv que aglutinará todos los resultados.
     *  Por cada línea se escribirá en el siguiente orden => CO_CLIENTE, CO_ACTIVIDAD, KEYWORD_TOP10, POSICION_GMB
     */
    private void searchInformationFromOracleAndMongoDB() {
        LOG.info("Ejecutando el " + this.getName());
        List<String> values = new ArrayList<>();
        for (Integer clientCode : clientCodes) {
            try {
                Integer co_actvad = f_datos_contactoProjection.findActivityCodeByclientCode(clientCode);
                if (co_actvad != null) {
                    Object[] webVisibilityAnalyticsValues = webVisibilityAnalyticsProjection.getKpis303And23FromWebVisibilityAnalytics(String.valueOf(clientCode));
                    String cadena = clientCode + "," + co_actvad + "," + webVisibilityAnalyticsValues[0] + "," + webVisibilityAnalyticsValues[1];
                    values.add(cadena);
                }
            } catch (Exception e) {
                LOG.error("Ha ocurrido un error durante la consulta del cliente: " + clientCode);
            }
        }
        LOG.info("Generando de fichero csv en el hilo " + this.getName());
        Text.generateTxtFileWithStrings(values, path, nameFile);
        LOG.info("Fin de la ejecución del hilo " + this.getName());
    }
}
