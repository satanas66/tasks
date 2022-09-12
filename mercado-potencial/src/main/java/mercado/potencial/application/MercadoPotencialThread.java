package mercado.potencial.application;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import automation.factory.xlsx.Excel;
import mercado.potencial.domain.business.*;
import mercado.potencial.domain.mapper.*;
import mercado.potencial.jpa.gestford.Tsf_AccountProjection;
import mercado.potencial.jpa.gestford.Tsf_TaskProjection;
import mercado.potencial.jpa.pa.Tdv_Oportunidades_Por_ClienteProjection;
import mercado.potencial.jpa.pa.Tdv_Tramo_Historico_AlianzaProjection;
import mercado.potencial.jpa.pa.Thv_Actividad_Comercial_ConsProjection;
import mercado.potencial.jpa.pa.Vhv_Cuotas_Mes_V2Projection;
import mercado.potencial.jpa.phw_vac.F_Datos_ContactoProjection;
import mercado.potencial.jpa.phw_vac.F_ImpagoProjection;
import mercado.potencial.jpa.phw_vac.Gestor_OportunidadesProjection;
import mercado.potencial.jpa.sisora.Tsi_ActvadProjection;
import mercado.potencial.jpa.sisora.Tsi_EmprvcaractProjection;
import mercado.potencial.jpa.sisora.Tsi_F_WebProjection;
import mercado.potencial.jpa.sisora.Tsi_SectorProjection;
import mercado.potencial.mongo.biRecomendador.Bi_RecomendatorProjection;
import mercado.potencial.mongo.webVisibilityAnalytics.WebVisibilityAnalyticsProjection;

import java.util.*;

/**
 * @author Edwin Patricio Arévalo Angulo
 */
public class MercadoPotencialThread extends Thread {

    private static Logger LOG = Logger.getLogger(MercadoPotencialThread.class);

    private F_Datos_ContactoProjection f_datos_contactoProjection;

    private F_ImpagoProjection f_impagoProjection;

    private Gestor_OportunidadesProjection gestor_oportunidadesProjection;

    private Tsf_AccountProjection tsf_accountProjection;

    private Tsf_TaskProjection tsf_taskProjection;

    private Tsi_ActvadProjection tsi_actvadProjection;

    private Tsi_EmprvcaractProjection tsi_emprvcaractProjection;

    private Tsi_F_WebProjection tsi_f_webProjection;

    private Tsi_SectorProjection tsi_sectorProjection;

    private Tdv_Oportunidades_Por_ClienteProjection tdv_oportunidades_por_clienteProjection;

    private Tdv_Tramo_Historico_AlianzaProjection tdv_tramo_historico_alianzaProjection;

    private Thv_Actividad_Comercial_ConsProjection thv_actividad_comercial_consProjection;

    private Vhv_Cuotas_Mes_V2Projection vhv_cuotas_mes_v2Projection;

    private WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection;

    private Bi_RecomendatorProjection bi_recomendatorProjection;

    private String path;

    private String nameFile;

    private Map<Integer, Object[]> mapaAuditoria;

    private Map<String, String> mapaEmailHunter;

    private List<String> actividadesExcluir;

    private List<Integer> clientCodes;

    private List<Integer> clientCodesInValid;



    public MercadoPotencialThread(String nombreHilo) {
        super(nombreHilo);
    }

    public void instancePatameters(String path, String nameFile) {
        this.path = path;
        this.nameFile = nameFile;
    }

    public void instanceAuditoria(Map<Integer, Object[]> mapaAuditoria){
        this.mapaAuditoria = mapaAuditoria;
    }

    public void instancePhysycalResource(Map<String, String> mapaEmailHunter, List<String> actividadesExcluir) {
        this.mapaEmailHunter = mapaEmailHunter;
        this.actividadesExcluir = actividadesExcluir;
    }

    public void instanceListClientCodes(List<Integer> clientCodes) {
        this.clientCodes = clientCodes;
    }

    public void instanceOracleProjection(F_Datos_ContactoProjection f_datos_contactoProjection,
                                         F_ImpagoProjection f_impagoProjection,
                                         Gestor_OportunidadesProjection gestor_oportunidadesProjection,
                                         Tsf_AccountProjection tsf_accountProjection,
                                         Tsf_TaskProjection tsf_taskProjection,
                                         Tsi_ActvadProjection tsi_actvadProjection,
                                         Tsi_EmprvcaractProjection tsi_emprvcaractProjection,
                                         Tsi_F_WebProjection tsi_f_webProjection,
                                         Tsi_SectorProjection tsi_sectorProjection) {

        this.f_datos_contactoProjection = f_datos_contactoProjection;
        this.f_impagoProjection = f_impagoProjection;
        this.gestor_oportunidadesProjection = gestor_oportunidadesProjection;

        this.tsf_accountProjection = tsf_accountProjection;
        this.tsf_taskProjection = tsf_taskProjection;

        this.tsi_actvadProjection = tsi_actvadProjection;
        this.tsi_emprvcaractProjection = tsi_emprvcaractProjection;
        this.tsi_f_webProjection = tsi_f_webProjection;
        this.tsi_sectorProjection = tsi_sectorProjection;
    }

    public void instanceMySqlProjection(Tdv_Oportunidades_Por_ClienteProjection tdv_oportunidades_por_clienteProjection,
                                        Tdv_Tramo_Historico_AlianzaProjection tdv_tramo_historico_alianzaProjection,
                                        Thv_Actividad_Comercial_ConsProjection thv_actividad_comercial_consProjection,
                                        Vhv_Cuotas_Mes_V2Projection vhv_cuotas_mes_v2Projection) {
        this.tdv_oportunidades_por_clienteProjection = tdv_oportunidades_por_clienteProjection;
        this.tdv_tramo_historico_alianzaProjection = tdv_tramo_historico_alianzaProjection;
        this.thv_actividad_comercial_consProjection = thv_actividad_comercial_consProjection;
        this.vhv_cuotas_mes_v2Projection = vhv_cuotas_mes_v2Projection;
    }

    public void instanceMongoProjection(WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection,
                                        Bi_RecomendatorProjection bi_recomendatorProjection) {
        this.webVisibilityAnalyticsProjection = webVisibilityAnalyticsProjection;
        this.bi_recomendatorProjection = bi_recomendatorProjection;
    }

    @Override
    public void run() {
        constructProjection();
        int i=0;
        while(clientCodesInValid.size() > 0){
            clientCodes = clientCodesInValid;
            constructProjection();
            i++;
            if(i==3){
                break;
            }
        }
    }

    private void constructProjection() {
        LOG.info("Ejecutando el " + this.getName());
        List<List<Object>> total = new ArrayList<>();
        int i = 0;
        clientCodesInValid = new ArrayList<>();
        for (Integer clientCode : clientCodes) {
            try {
                String vivo = vhv_cuotas_mes_v2Projection.findIdProductByClientCode(String.valueOf(clientCode));
                if (!Utils.evaluateString(vivo)) {
                    /********** Historico Salesforce **********/
                    HistoricoSalesforce historicoSalesforce = new HistoricoSalesforce();
                    historicoSalesforce.setVivo(vivo);
                    Object[] datosContactoValues = f_datos_contactoProjection.getProjectionFromF_Datos_ContactoToMuestraMadreByClientCode(clientCode);
                    if (datosContactoValues != null) {
                        /********** Datos Contacto **********/
                        DatosContactoMapper datosContactoMapper = new DatosContactoMapper();
                        DatosContacto datosContacto = new DatosContacto();
                        datosContacto.setCo_cliente(clientCode);
                        datosContacto = datosContactoMapper.getDatosContactoFromProjection(datosContacto, datosContactoValues);
                        if (Utils.evaluateString(datosContacto.getEmail())) {
                            String email_hunter = mapaEmailHunter.get(datosContacto.getEmail().toLowerCase());
                            datosContacto.setEmail_hunter(email_hunter);
                        }
                        String di_url = tsi_f_webProjection.findProjectionEWeb(clientCode);
                        datosContacto.setDi_url(di_url);
                        String impago = f_impagoProjection.findImpagoValue(clientCode);
                        datosContacto.setImpago(impago);
                        datosContacto.setPersona_contacto("");//Valor aún por buscar
                        String co_sector = tsi_actvadProjection.findSectorCodeFromTsiActvad(datosContacto.getCo_actvad_pral());
                        List<Object[]> tsiSectorValues = tsi_sectorProjection.getProjectionsFromTsi_SectorToMuestraMadreByCoSector(co_sector);
                        datosContacto = datosContactoMapper.getDatosContactoFromTsiSectorProjection(datosContacto, tsiSectorValues);

                        /********** Morfología de la Pyme **********/
                        MorfologiaPymeMapper morfologiaPymeMapper = new MorfologiaPymeMapper();
                        MorfologiaPyme morfologiaPyme = new MorfologiaPyme();
                        Object[] tsfAccountValues = tsf_accountProjection.findProjectionECuenta(clientCode, datosContacto.getNc_nif());
                        morfologiaPyme = morfologiaPymeMapper.setTsf_AccountProjection(morfologiaPyme, tsfAccountValues);
                        Integer nu_vcaract_empr = tsi_emprvcaractProjection.finNumeroEmpleadosByCoEmpresa(datosContacto.getCo_empresa());
                        morfologiaPyme.setEmpleados(nu_vcaract_empr);
                        morfologiaPyme.setVentas("");////Valor aún por buscar
                        morfologiaPyme.setRating_crediticio(datosContacto.getRating_crediticio());

                        /********** Historico Salesforce **********/
                        HistoricoSalesforceMapper historicoSalesforceMapper = new HistoricoSalesforceMapper();
                        Date ult_anotacion = tsf_taskProjection.findLastModificationETarea(morfologiaPyme.getAccountId());
                        historicoSalesforce.setUlt_anotacion(ult_anotacion);
                        Date ult_anotacion_llamada = thv_actividad_comercial_consProjection.findUltimaAnotacionLlamada(clientCode);
                        historicoSalesforce.setUlt_anotacion_llamada(ult_anotacion_llamada);
                        Object[] tdvOportunidadesPorClienteValues = tdv_oportunidades_por_clienteProjection.findFeGanadaAndFeCreacionFromTdv_Oportunidades_Por_ClienteByClientCode(clientCode);
                        historicoSalesforce = historicoSalesforceMapper.setTdv_Oportunidades_Por_Cliente_Projection(historicoSalesforce, tdvOportunidadesPorClienteValues);
                        historicoSalesforce.setActividad_excluir(actividadesExcluir, datosContacto.getCo_actvad_pral());
                        String alianza = tdv_tramo_historico_alianzaProjection.findAlianzaByClientCode(clientCode);
                        historicoSalesforce.setAlianzas(alianza);
                        /********** Analitica WEB **********/
                        AnaliticaWebMapper analiticaWebMapper = new AnaliticaWebMapper();
                        AnaliticaWeb analiticaWeb = new AnaliticaWeb();
                        Object[] gestorOportunidadesValues = gestor_oportunidadesProjection.findProjectionEGestorOportunidades(clientCode);
                        analiticaWeb = analiticaWebMapper.setZoomFromGestorOportunidadesProjection(analiticaWeb, gestorOportunidadesValues);

                        Object[] webVisibilityAnalyticsValues = webVisibilityAnalyticsProjection.getProjectionFromWebVisibilityAnalytics(String.valueOf(clientCode));
                        datosContacto = datosContactoMapper.setDomainFromWeVisibilityAnalyticsProjection(datosContacto, webVisibilityAnalyticsValues);
                        historicoSalesforce = historicoSalesforceMapper.setListingVersionFromWeVisibilityAnalyticsProjection(historicoSalesforce, webVisibilityAnalyticsValues);
                        analiticaWeb = analiticaWebMapper.setWebVisibilityAnalyticsProjection(analiticaWeb, webVisibilityAnalyticsValues);
                        /********** SEO **********/
                        SeoMapper seoMapper = new SeoMapper();
                        Seo seo = new Seo();
                        seo = seoMapper.setWebVisibilityAnalyticsProjection(seo, webVisibilityAnalyticsValues);
                        /********** Posicionamiento en buscadores **********/
                        PosicinamientoBuscadoresMapper posicinamientoBuscadoresMapper = new PosicinamientoBuscadoresMapper();
                        PosicionamientoBuscadores posicionamientoBuscadores = new PosicionamientoBuscadores();
                        posicionamientoBuscadores = posicinamientoBuscadoresMapper.setWebVisibilityAnalyticsProjection(posicionamientoBuscadores, webVisibilityAnalyticsValues);
                        /********** Perfil GMB **********/
                        posicionamientoBuscadores = posicinamientoBuscadoresMapper.setRankingNumberFromWebVisibilityAnalyticsProjection(posicionamientoBuscadores, webVisibilityAnalyticsValues);
                        datosContacto = datosContactoMapper.setDatosContactoFromWebVisibilityAnalyticsProjection(datosContacto, webVisibilityAnalyticsValues);
                        PerfilGmbMapper perfilGmbMapper = new PerfilGmbMapper();
                        PerfilGmb perfilGmb = new PerfilGmb();
                        perfilGmb = perfilGmbMapper.getPerfilGmbFromWebVisibilityAnalyticsProjection(perfilGmb, webVisibilityAnalyticsValues);
                        String paquete_recomendado = bi_recomendatorProjection.findPaqueteRecomendadoByClientCode(clientCode);
                        Recomendador recomendador = new Recomendador();
                        recomendador.setPaquete_recomendado(paquete_recomendado);
                        /********** Auditoria **********/
                        AuditoriaMapper auditoriaMapper = new AuditoriaMapper();
                        Object[] auditProjection = mapaAuditoria.get(clientCode);
                        Auditoria auditoria = auditoriaMapper.setAuditoriaProjection(new Auditoria(), auditProjection);

                        List<Object> kpisValues = datosContacto.getKpisDatosContacto();
                        kpisValues.addAll(historicoSalesforce.getKpisHistoricoSalesforce());
                        kpisValues.addAll(morfologiaPyme.getKpisMorfologiaPyme());
                        kpisValues.addAll(analiticaWeb.getKpisAnaliticaWeb());
                        kpisValues.addAll(seo.getKpisSeo());
                        kpisValues.addAll(posicionamientoBuscadores.getKpisPosicionamientoBuscadores());
                        kpisValues.addAll(perfilGmb.getKpisPerfilGmb());
                        kpisValues.addAll(recomendador.getKpisRecomendador());
                        kpisValues.addAll(auditoria.getKpisAuditoria());
                        total.add(kpisValues);

                        i++;
                        if (i == 100) {
                            LOG.info("Escribiendo " + i + " registros...");
                            Excel.writeKPIsAllValues(path, nameFile, total, getKPIsMuestraMadre());
                            LOG.info("Registros escritos correctamente");
                            total = new ArrayList<>();
                            i = 0;
                        }
                    }
                }
            } catch (Exception e) {
                clientCodesInValid.add(clientCode);
                LOG.error("Ha ocurrido un error durante la consulta del cliente: " + clientCode);
            }
        }
        if(total.size() > 0){
            LOG.info("Escribiendo últimos " + i + " registros...");
            Excel.writeKPIsAllValues(path, nameFile, total, getKPIsMuestraMadre());
            LOG.info("Registros escritos correctamente");
        }
        if(clientCodesInValid.size() > 0){
            Text.generateTxtFileWithIntegers(clientCodesInValid, path, this.getName()+"_client_codes_failed.txt");
        }
    }

    /**
     * Método que genera el listado completo de los KPIs de la muestra madre versión 1
     *
     * @return
     */
    private List<String> getKPIsMuestraMadre() {
        return Arrays.asList(
                "CO_CLIENTE", "CC_NOM_EMPRE", "NO_COMER", "TX_ACTVAD", "TX_SECTOR", "DIRECCION",
                "TX_LOCA_APA", "DE_PROV", "CO_POST_CTO", "TX_CCAA",
                "TELEFONO1", "TELEFONO_NUM", "EMAIL", "EMAIL_HUNTER", "DI_URL",
                "WEBSITE", "DOMAIN", "IMPAGO", "PERSONA_CONTACTO",
                "ULT_ANOTACION", "ULT_ANOTACION_LLAMADA", "LW", "LO", "ULT_LLAMADA_REGISTRADA", "LC",
                "VIVO", "LISTINGVERSION", "ACTVAD_EXCLUIR", "ALIANZAS",
                "EMPLEADOS", "EMPLEADOS_IMP", "VENTAS", "RATING_CREDITICIO", "FE_CREA_RED_SF",
                "RGPD",
                "ZOOM", "EXISTS_URL", "SOCIAL_LINKS_FACEBOOK", "SOCIAL_LINKS_TWITTER",
                "SOCIAL_LINKS_INSTAGRAM", "SOCIAL_LINKS_LINKEDIN", "NUMERO_DE_PAGINAS", "MOBILEWIDTH", "MOBILEHEIGHT",
                "ECOMMERCE_CHECKOUT_LINK", "ECOMMERCE_BOOKING_TYPE", "CMS", "MAS_5SEGS_CARGA_MOVIL",
                "MENOS_2_LINKS_ENTRANTES",
                "NO_RESPONSIVE", "POCO_CONTENIDO", "ERRORES_EN_ALT", "ERRORES_EN_TITLE", "NO_HTTPS",
                "NO_SITEMAPS", "NO_ROBOTS_TXT", "NUM_DE_KW_DETECTADAS",
                "NUM_KW_EN_TOP_10", "KEYWORD_1", "POSITION_KW_1",
                "KEYWORD_2", "POSITION_KW_2", "KEYWORD_3", "POSITION_KW_3",
                "KEYWORD_4", "POSITION_KW_4", "KEYWORD_5", "POSITION_KW_5", "KEYWORD_6",
                "POSITION_KW_6", "KEYWORD_7", "POSITION_KW_7", "KEYWORD_8", "POSITION_KW_8",
                "KEYWORD_9", "POSITION_KW_9", "KEYWORD_10", "POSITION_KW_10", "RANKING_NUMBER",
                "PAGE_URL", "NOMBRE_GOOGLE", "REVIEWS", "TOTAL_RATING", "CLAIM_BUSINESS",
                "TIMESTAMP", "PAQUETE_RECOMENDADO",
                "CO_CLIENTE", "FE_CRACION", "FE_MODIFICACION", "ORIGEN_DATOS", "TIPO_OPERACION");
    }
}
