package application;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import automation.factory.xlsx.Excel;
import com.mongodb.BasicDBList;
import domain.business.*;
import domain.mapper.*;
import mongo.webVisibilityAnalytics.WebVisibilityAnalyticsProjection;

import java.util.*;

public class PrecuentasThread extends Thread {

    private static Logger LOG = Logger.getLogger(PrecuentasThread.class);

    private WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection;

    private String path;

    private String nameFile;

    private List<Integer> clientesFicticios;

    private List<Integer> clientCodesInValid;

    private BasicDBList kpiseg400;

    private BasicDBList kpiseg401;

    public PrecuentasThread(String nombreHilo) {
        super(nombreHilo);
    }

    public void instancePatameters(String path, String nameFile) {
        this.path = path;
        this.nameFile = nameFile;
    }

    public void instanceListClientCodes(List<Integer> clientesFicticios) {
        this.clientesFicticios = clientesFicticios;
    }

    public void instanceMongoProjection(WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection) {
        this.webVisibilityAnalyticsProjection = webVisibilityAnalyticsProjection;
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
        for (Integer clienteFicticio : clientesFicticios) {
            try {
//                clienteFicticio=931301771;
                DatosContactoMapper datosContactoMapper = new DatosContactoMapper();
                DatosContacto datosContacto = new DatosContacto();
                datosContacto.setCo_cliente(clienteFicticio);

                Object[] webVisibilityAnalyticsValues = webVisibilityAnalyticsProjection.getProjectionFromWebVisibilityAnalytics(String.valueOf(clienteFicticio), kpiseg400, kpiseg401);

                datosContacto = datosContactoMapper.setDomainFromWeVisibilityAnalyticsProjection(datosContacto, webVisibilityAnalyticsValues);

                AnaliticaWebMapper analiticaWebMapper = new AnaliticaWebMapper();
                AnaliticaWeb analiticaWeb = analiticaWebMapper.setWebVisibilityAnalyticsProjection(new AnaliticaWeb(), webVisibilityAnalyticsValues);

                /********** SEO **********/
                SeoMapper seoMapper = new SeoMapper();
                Seo seo = seoMapper.setWebVisibilityAnalyticsProjection(new Seo(), webVisibilityAnalyticsValues);

                /********** Posicionamiento en buscadores **********/
                PosicinamientoBuscadoresMapper posicinamientoBuscadoresMapper = new PosicinamientoBuscadoresMapper();
                PosicionamientoBuscadores posicionamientoBuscadores = posicinamientoBuscadoresMapper.setWebVisibilityAnalyticsProjection(new PosicionamientoBuscadores(), webVisibilityAnalyticsValues);

                /********** Perfil GMB **********/
                posicionamientoBuscadores = posicinamientoBuscadoresMapper.setRankingNumberFromWebVisibilityAnalyticsProjection(posicionamientoBuscadores, webVisibilityAnalyticsValues);
                datosContacto = datosContactoMapper.setDatosContactoFromWebVisibilityAnalyticsProjection(datosContacto, webVisibilityAnalyticsValues);

                PerfilGmbMapper perfilGmbMapper = new PerfilGmbMapper();
                PerfilGmb perfilGmb = new PerfilGmb();
                perfilGmb = perfilGmbMapper.getPerfilGmbFromWebVisibilityAnalyticsProjection(perfilGmb, webVisibilityAnalyticsValues);

                /********** Presencia Digital **********/
                PresenciaDigitalMapper presenciaDigitalMapper = new PresenciaDigitalMapper();
                PresenciaDigital presenciaDigital = presenciaDigitalMapper.getPresenciaDigitalFromWebVisibilityAnalyticsProjection(new PresenciaDigital(), webVisibilityAnalyticsValues);

                List<Object> kpisValues = datosContacto.getKpisDatosContacto();
                kpisValues.addAll(analiticaWeb.getKpisAnaliticaWeb());
                kpisValues.addAll(seo.getKpisSeo());
                kpisValues.addAll(posicionamientoBuscadores.getKpisPosicionamientoBuscadores());
                kpisValues.addAll(perfilGmb.getKpisPerfilGmb());
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
                clientCodesInValid.add(clienteFicticio);
                LOG.error("Ha ocurrido un error durante la consulta del cliente: " + clienteFicticio);
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
                "CLIENTE_FICTICIO", "TELEFONO_NUM", "WEBSITE", "DOMAIN",
                "EXISTS_URL", "SOCIAL_LINKS_FACEBOOK", "SOCIAL_LINKS_TWITTER",
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
                "INDICE_PRESENCIA", "CLAIM_BUSINESS", "REVIEWS", "APARECER_PRIMERA_PAG", "RANNKING_NUMBER",
                "KEYWORD_TOP10", "ERRORES_GRAVES", "ENLACES_ROTOS", "NO_SITEMAP", "NO_ROBOTS", "SITIO_NO_SEGURO",
                "ERRORES_ALT", "ERRORES_TITLE", "POCO_CONTENIDO", "ERRORES_INDEX", "NO_RESPONSIVE", ">5S_CARGA_MOVIL",
                "INDICE_COMPETENCIA", "YEXT/SCAN");
    }
}
