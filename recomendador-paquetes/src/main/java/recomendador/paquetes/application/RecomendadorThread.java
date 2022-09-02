package recomendador.paquetes.application;

import automation.factory.Logger;
import automation.factory.Utils;
import recomendador.paquetes.domain.business.RecomendadorPaquetes;
import recomendador.paquetes.domain.mapper.RecomendadorPaquetesMapper;
import recomendador.paquetes.mongo.webVisibilityAnalytics.WebVisibilityAnalyticsProjection;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 */
public class RecomendadorThread extends Thread{

    private static Logger LOG = Logger.getLogger(RecomendadorThread.class);

    private String path;

    private String nameFile;

    private List<String> clientCodes;

    private WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection;

    public RecomendadorThread(String nombreHilo) {
        super(nombreHilo);
    }

    public void instancePatameters(String path, String nameFile) {
        this.path = path;
        this.nameFile = nameFile;
    }

    public void instanceListClientCodes(List<String> clientCodes) {
        this.clientCodes = clientCodes;
    }

    public void instanceMongoProjection(WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection) {
        this.webVisibilityAnalyticsProjection = webVisibilityAnalyticsProjection;
    }

    @Override
    public void run() {
        constructProjection();
    }

    private void constructProjection(){
        LOG.info("Ejecutando el " + this.getName());
        List<RecomendadorPaquetes> list = new ArrayList<>();
        for (String clientCode : clientCodes) {
            try {
                Object[] projection = webVisibilityAnalyticsProjection.findProjectionToRecomendadorByClientCode(clientCode);
                if(projection != null){
                    RecomendadorPaquetesMapper recomendadorPaquetesMapper = new RecomendadorPaquetesMapper();
                    RecomendadorPaquetes recomendadorPaquetes = recomendadorPaquetesMapper.setKpisRecomendadorPaquetes(new RecomendadorPaquetes(), projection);
                    list.add(recomendadorPaquetes);break;
                }
            } catch (Exception e) {
                LOG.error("Ha ocurrido un error durante la consulta del cliente: " + clientCode);
            }
        }
        /**
         * Generación de fichero csv
         */
        LOG.info("Generando de fichero csv en el hilo "+this.getName());
        generaCsv(list, path, nameFile);
        LOG.info("Fichero csv generado por el hilo "+this.getName());

        LOG.info("Fin de la ejecución del hilo " + this.getName());
    }

    /**
     * Método que genera el fichero csv con los KPIS necesarios para generar la Recomenación de paquetes
     * @param values
     * @param path
     * @param fileName
     */
    private void generaCsv(List<RecomendadorPaquetes> values, String path, String fileName) {
        if (!Utils.evaluateList(values)) {
            try {
                File f = new File(path + fileName);
                FileWriter fileWriter = new FileWriter(f);
                fileWriter.write("CO_CLIENTE,RANKING_NUMBER,REVIWS,WEBSITE,CLAIM_BUSINESS," +
                        "NO_HTTPS,NO_SITEMAPS,NO_ROBOTS_TXT,MENOS_2_LINKS_ENTRANTES,NUM_KW_TOP_10," +
                        "NUM_KW_DETECTADAS,MAS_5SEG_CARGA_MOVIL,NO_RESPONSIVE,POCO_CONTENIDO,ERRORES_EN_ALT," +
                        "ERRORES_EN_TITLE,NUMERO_DE_PAGINAS,ECOMMERCE_CHECKOUT_LINK,SOCIAL_LINKS_FACEBOOK" + "\r\n");

                for (int i = 0; i < values.size(); i++) {
                    RecomendadorPaquetes recomendadorPaquetes = values.get(i);
                    fileWriter.write(recomendadorPaquetes.getToString() + "\r\n");
                }
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                LOG.error("Ha ocurrido un error al crear el fichero " + fileName + ": " + e.getMessage());
            }
        }
    }
}
