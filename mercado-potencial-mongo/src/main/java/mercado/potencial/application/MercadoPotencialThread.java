package mercado.potencial.application;

import automation.factory.Logger;
import automation.factory.txt.Text;
import automation.factory.xlsx.Excel;
import com.mongodb.BasicDBList;
import mercado.potencial.domain.business.*;
import mercado.potencial.domain.mapper.*;
import mercado.potencial.mongo.biRecomendador.Bi_RecomendatorProjection;
import mercado.potencial.mongo.webVisibilityAnalytics.WebVisibilityAnalyticsProjection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 */
public class MercadoPotencialThread extends Thread {

    private static Logger LOG = Logger.getLogger(MercadoPotencialThread.class);

    private WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection;

    private Bi_RecomendatorProjection bi_recomendatorProjection;

    private String path;

    private String nameFile;

    private List<Integer> clientCodes;

    private List<Integer> clientCodesInValid;

    private BasicDBList kpiseg400;

    private BasicDBList kpiseg401;

    public MercadoPotencialThread(String nombreHilo) {
        super(nombreHilo);
    }

    public void instancePatameters(String path, String nameFile) {
        this.path = path;
        this.nameFile = nameFile;
    }

    public void instanceListClientCodes(List<Integer> clientCodes) {
        this.clientCodes = clientCodes;
    }

    public void instanceMongoProjection(WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection,
                                        Bi_RecomendatorProjection bi_recomendatorProjection) {
        this.webVisibilityAnalyticsProjection = webVisibilityAnalyticsProjection;
        this.bi_recomendatorProjection = bi_recomendatorProjection;
    }

    public void instaceBasicBDLists(BasicDBList kpiseg400, BasicDBList kpiseg401) {
        this.kpiseg400 = kpiseg400;
        this.kpiseg401 = kpiseg401;
    }

    @Override
    public void run() {
        constructProjection();
    }

    private void constructProjection() {
        LOG.info("Ejecutando el " + this.getName());
        List<List<Object>> total = new ArrayList<>();
        int i = 0;
        clientCodesInValid = new ArrayList<>();
        for (Integer clientCode : clientCodes) {
            try {
                Object[] webVisibilityAnalyticsValues = webVisibilityAnalyticsProjection.getProjectionFromWebVisibilityAnalytics(String.valueOf(clientCode), kpiseg400, kpiseg401);

                DatosContactoMapper datosContactoMapper = new DatosContactoMapper();
                DatosContacto datosContacto = datosContactoMapper.setDatosContactoFromWebVisibilityAnalyticsProjection(new DatosContacto(), webVisibilityAnalyticsValues);
                datosContacto.setClientCode(clientCode);
                /********** Analitica WEB **********/
                AnaliticaWebMapper analiticaWebMapper = new AnaliticaWebMapper();
                AnaliticaWeb analiticaWeb = new AnaliticaWeb();
                analiticaWeb = analiticaWebMapper.setWebVisibilityAnalyticsProjection(analiticaWeb, webVisibilityAnalyticsValues);

                /********** SEO **********/
                SeoMapper seoMapper = new SeoMapper();
                Seo seo = seoMapper.setWebVisibilityAnalyticsProjection(new Seo(), webVisibilityAnalyticsValues);

                /********** Posicionamiento en buscadores **********/
                PosicinamientoBuscadoresMapper posicinamientoBuscadoresMapper = new PosicinamientoBuscadoresMapper();
                PosicionamientoBuscadores posicionamientoBuscadores = new PosicionamientoBuscadores();
                posicionamientoBuscadores = posicinamientoBuscadoresMapper.setWebVisibilityAnalyticsProjection(posicionamientoBuscadores, webVisibilityAnalyticsValues);

                /********** Perfil GMB **********/
                posicionamientoBuscadores = posicinamientoBuscadoresMapper.setRankingNumberFromWebVisibilityAnalyticsProjection(posicionamientoBuscadores, webVisibilityAnalyticsValues);
                PerfilGmbMapper perfilGmbMapper = new PerfilGmbMapper();
                PerfilGmb perfilGmb = new PerfilGmb();
                perfilGmb = perfilGmbMapper.getPerfilGmbFromWebVisibilityAnalyticsProjection(perfilGmb, webVisibilityAnalyticsValues);
                String paquete_recomendado = bi_recomendatorProjection.findPaqueteRecomendadoByClientCode(clientCode);
                Recomendador recomendador = new Recomendador();
                recomendador.setPaquete_recomendado(paquete_recomendado);

                /********** Presencia Digital **********/
                PresenciaDigitalMapper presenciaDigitalMapper = new PresenciaDigitalMapper();
                PresenciaDigital presenciaDigital = presenciaDigitalMapper.getPresenciaDigitalFromWebVisibilityAnalyticsProjection(new PresenciaDigital(), webVisibilityAnalyticsValues);

                List<Object> kpisValues = datosContacto.getKpisDatosContacto();
                kpisValues.addAll(analiticaWeb.getKpisAnaliticaWeb());
                kpisValues.addAll(seo.getKpisSeo());
                kpisValues.addAll(posicionamientoBuscadores.getKpisPosicionamientoBuscadores());
                kpisValues.addAll(perfilGmb.getKpisPerfilGmb());
                kpisValues.addAll(recomendador.getKpisRecomendador());
                kpisValues.addAll(presenciaDigital.getKpisPresenciaDigital());
                total.add(kpisValues);

                i++;
                if (i == 10000) {
                    LOG.info("Escribiendo " + i + " registros...");
                    Excel.writeKPIsAllValues(path, nameFile, total, getKPIsMuestraMadre());
                    LOG.info("Registros escritos correctamente");
                    total = new ArrayList<>();
                    i = 0;
                }
            } catch (Exception e) {
                clientCodesInValid.add(clientCode);
                LOG.error("Ha ocurrido un error durante la consulta del cliente: " + clientCode);
            }
        }
        if (total.size() > 0) {
            LOG.info("Escribiendo últimos " + i + " registros...");
            Excel.writeKPIsAllValues(path, nameFile, total, getKPIsMuestraMadre());
            LOG.info("Registros escritos correctamente");
        }
        if (clientCodesInValid.size() > 0) {
            Text.generateTxtFileWithIntegers(clientCodesInValid, path, this.getName() + "_client_codes_failed.txt");
        }
    }

    /**
     * Método que genera el listado completo de los KPIs de la muestra madre versión 1
     *
     * @return
     */
    private List<String> getKPIsMuestraMadre() {
        return Arrays.asList(
                "CO_CLIENTE", "TELEFONO_NUM", "WEBSITE", "DOMAIN", "EXISTS_URL", "SOCIAL_LINKS_FACEBOOK", "SOCIAL_LINKS_TWITTER",
                "SOCIAL_LINKS_INSTAGRAM", "SOCIAL_LINKS_LINKEDIN", "NUMERO_DE_PAGINAS", "MOBILEWIDTH", "MOBILEHEIGHT",
                "ECOMMERCE_CHECKOUT_LINK", "ECOMMERCE_BOOKING_TYPE", "CMS", "MAS_5SEGS_CARGA_MOVIL",
                "MENOS_2_LINKS_ENTRANTES", "NO_RESPONSIVE", "POCO_CONTENIDO", "ERRORES_EN_ALT", "ERRORES_EN_TITLE", "NO_HTTPS",
                "NO_SITEMAPS", "NO_ROBOTS_TXT", "NUM_DE_KW_DETECTADAS",
                "NUM_KW_EN_TOP_10", "KEYWORD_1", "POSITION_KW_1",
                "KEYWORD_2", "POSITION_KW_2", "KEYWORD_3", "POSITION_KW_3",
                "KEYWORD_4", "POSITION_KW_4", "KEYWORD_5", "POSITION_KW_5", "KEYWORD_6",
                "POSITION_KW_6", "KEYWORD_7", "POSITION_KW_7", "KEYWORD_8", "POSITION_KW_8",
                "KEYWORD_9", "POSITION_KW_9", "KEYWORD_10", "POSITION_KW_10", "RANKING_NUMBER",
                "PAGE_URL", "NOMBRE_GOOGLE", "REVIEWS", "TOTAL_RATING", "CLAIM_BUSINESS",
                "TIMESTAMP", "ACTVAD_GMB",
                "PAQUETE_RECOMENDADO",
                "INDICE_PRESENCIA", "CLAIM_BUSINESS", "REVIEWS", "APARECER_PRIMERA_PAG", "RANNKING_NUMBER",
                "KEYWORD_TOP10", "ERRORES_GRAVES", "ENLACES_ROTOS", "NO_SITEMAP", "NO_ROBOTS", "SITIO_NO_SEGURO",
                "ERRORES_ALT", "ERRORES_TITLE", "POCO_CONTENIDO", "ERRORES_INDEX", "NO_RESPONSIVE", ">5S_CARGA_MOVIL",
                "INDICE_COMPETENCIA", "YEXT/SCAN");
    }
}
