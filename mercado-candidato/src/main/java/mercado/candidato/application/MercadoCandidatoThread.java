package mercado.candidato.application;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import automation.factory.xlsx.Excel;
import mercado.candidato.domain.business.*;
import mercado.candidato.domain.mapper.*;
import mercado.candidato.jpa.gestford.Tsf_AccountProjection;
import mercado.candidato.jpa.gestford.Tsf_Csord__Service__CProjection;
import mercado.candidato.jpa.gestford.Tsf_TaskProjection;
import mercado.candidato.jpa.pa.*;
import mercado.candidato.jpa.phw_vac.Estacionalidad_ActvProjection;
import mercado.candidato.jpa.phw_vac.F_Datos_ContactoProjection;
import mercado.candidato.jpa.phw_vac.F_ImpagoProjection;
import mercado.candidato.jpa.phw_vac.Gestor_OportunidadesProjection;
import mercado.candidato.jpa.sisora.Tsi_ActvadProjection;
import mercado.candidato.jpa.sisora.Tsi_EmprvcaractProjection;
import mercado.candidato.jpa.sisora.Tsi_F_WebProjection;
import mercado.candidato.jpa.sisora.Tsi_SectorProjection;
import mercado.candidato.mongo.biRecomendador.Bi_RecomendatorProjection;
import mercado.candidato.mongo.kpisActividad.Kpis_ActividadProjection;
import mercado.candidato.mongo.kpisCalculados.Kpis_CalculadosProjection;
import mercado.candidato.mongo.kpisPrCancDaily.Kpis_Pr_Canc_DailyProjection;
import mercado.candidato.mongo.webVisibilityAnalytics.WebVisibilityAnalyticsProjection;

import java.util.*;

public class MercadoCandidatoThread extends Thread {

    private static Logger LOG = Logger.getLogger(MercadoCandidatoThread.class);

    private String path;

    private String nameFile;

    private Map<String, String> mapaEmailHunter;

    private List<String> actividadesExcluir;

    private Map<String, Visitas> mapaVisitas;

    private List<Integer> clientCodes;

    private List<Integer> clientCodesInValid;

    private F_Datos_ContactoProjection f_datos_contactoProjection;

    private F_ImpagoProjection f_impagoProjection;

    private Gestor_OportunidadesProjection gestor_oportunidadesProjection;

    private Estacionalidad_ActvProjection estacionalidad_actvProjection;

    private Tsf_AccountProjection tsf_accountProjection;

    private Tsf_TaskProjection tsf_taskProjection;

    private Tsf_Csord__Service__CProjection tsf_csord__service__cProjection;

    private Tsi_ActvadProjection tsi_actvadProjection;

    private Tsi_EmprvcaractProjection tsi_emprvcaractProjection;

    private Tsi_F_WebProjection tsi_f_webProjection;

    private Tsi_SectorProjection tsi_sectorProjection;

    private Tdv_Oportunidades_Por_ClienteProjection tdv_oportunidades_por_clienteProjection;

    private Tdv_Tramo_Historico_AlianzaProjection tdv_tramo_historico_alianzaProjection;

    private Thv_Actividad_Comercial_ConsProjection thv_actividad_comercial_consProjection;

    private Vhv_Cuotas_Mes_V2Projection vhv_cuotas_mes_v2Projection;

    private Thv_Actividad_Comercial_Cons_V2Projection thv_actividad_comercial_cons_v2Projection;

    private Vdv_Asignacion_Cuenta_EcProjection vdv_asignacion_cuenta_ecProjection;

    private Tdv_UsuarioProjection tdv_usuarioProjection;

    private Tdp_Yext_PublicacionProjection tdp_yext_publicacionProjection;

    private WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection;

    private Bi_RecomendatorProjection bi_recomendatorProjection;

    private Kpis_ActividadProjection kpis_ActividadProjection;

    private Kpis_CalculadosProjection kpis_calculadosProjection;

    private Kpis_Pr_Canc_DailyProjection kpis_pr_canc_dailyProjection;

    public MercadoCandidatoThread(String nombreHilo) {
        super(nombreHilo);
    }

    public void instancePatameters(String path, String nameFile) {
        this.path = path;
        this.nameFile = nameFile;
    }

    public void instancePhysycalResource(Map<String, String> mapaEmailHunter, List<String> actividadesExcluir, Map<String, Visitas> mapaVisitas) {
        this.mapaEmailHunter = mapaEmailHunter;
        this.actividadesExcluir = actividadesExcluir;
        this.mapaVisitas = mapaVisitas;
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
                                         Tsi_SectorProjection tsi_sectorProjection,
                                         Estacionalidad_ActvProjection estacionalidad_actvProjection,
                                         Tsf_Csord__Service__CProjection tsf_csord__service__cProjection) {

        this.f_datos_contactoProjection = f_datos_contactoProjection;
        this.f_impagoProjection = f_impagoProjection;
        this.gestor_oportunidadesProjection = gestor_oportunidadesProjection;
        this.estacionalidad_actvProjection = estacionalidad_actvProjection;

        this.tsf_accountProjection = tsf_accountProjection;
        this.tsf_taskProjection = tsf_taskProjection;

        this.tsi_actvadProjection = tsi_actvadProjection;
        this.tsi_emprvcaractProjection = tsi_emprvcaractProjection;
        this.tsi_f_webProjection = tsi_f_webProjection;
        this.tsi_sectorProjection = tsi_sectorProjection;
        this.tsf_csord__service__cProjection = tsf_csord__service__cProjection;
    }

    public void instanceMySqlProjection(Tdv_Oportunidades_Por_ClienteProjection tdv_oportunidades_por_clienteProjection,
                                        Tdv_Tramo_Historico_AlianzaProjection tdv_tramo_historico_alianzaProjection,
                                        Thv_Actividad_Comercial_ConsProjection thv_actividad_comercial_consProjection,
                                        Vhv_Cuotas_Mes_V2Projection vhv_cuotas_mes_v2Projection,
                                        Thv_Actividad_Comercial_Cons_V2Projection thv_actividad_comercial_cons_v2Projection,
                                        Vdv_Asignacion_Cuenta_EcProjection vdv_asignacion_cuenta_ecProjection,
                                        Tdv_UsuarioProjection tdv_usuarioProjection,
                                        Tdp_Yext_PublicacionProjection tdp_yext_publicacionProjection) {
        this.tdv_oportunidades_por_clienteProjection = tdv_oportunidades_por_clienteProjection;
        this.tdv_tramo_historico_alianzaProjection = tdv_tramo_historico_alianzaProjection;
        this.thv_actividad_comercial_consProjection = thv_actividad_comercial_consProjection;
        this.vhv_cuotas_mes_v2Projection = vhv_cuotas_mes_v2Projection;

        this.thv_actividad_comercial_cons_v2Projection = thv_actividad_comercial_cons_v2Projection;
        this.vdv_asignacion_cuenta_ecProjection = vdv_asignacion_cuenta_ecProjection;
        this.tdv_usuarioProjection = tdv_usuarioProjection;
        this.tdp_yext_publicacionProjection = tdp_yext_publicacionProjection;
    }

    public void instanceMongoProjection(WebVisibilityAnalyticsProjection webVisibilityAnalyticsProjection,
                                        Bi_RecomendatorProjection bi_recomendatorProjection,
                                        Kpis_ActividadProjection kpis_ActividadProjection,
                                        Kpis_CalculadosProjection kpis_calculadosProjection,
                                        Kpis_Pr_Canc_DailyProjection kpis_pr_canc_dailyProjection) {
        this.webVisibilityAnalyticsProjection = webVisibilityAnalyticsProjection;
        this.bi_recomendatorProjection = bi_recomendatorProjection;
        this.kpis_ActividadProjection = kpis_ActividadProjection;
        this.kpis_calculadosProjection = kpis_calculadosProjection;
        this.kpis_pr_canc_dailyProjection = kpis_pr_canc_dailyProjection;
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
                Object[] vivo = vhv_cuotas_mes_v2Projection.findIdsProductServiceAndOportunidadByClientCode(String.valueOf(clientCode));
                if (vivo != null && Utils.evaluateString((String) vivo[0])) {
                    /********** Historico Salesforce **********/
                    HistoricoSalesforce historicoSalesforce = new HistoricoSalesforce();
                    historicoSalesforce.setVivo((String) vivo[0]);
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
                        morfologiaPyme.setVentas("");//Valor aún por buscar
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

                        /********** KPIS MERCADO CANDIDATO **********/
                        KpisMercadoCandidato kpisMercadoCandidato = new KpisMercadoCandidato();
                        MercadoCandidatoMapper mapper = new MercadoCandidatoMapper();
                        kpisMercadoCandidato.setPaquete((String) vivo[1]);

                        if(mapaVisitas.keySet().contains(String.valueOf(clientCode))){
                            Visitas visitas = mapaVisitas.get(String.valueOf(clientCode));
                            kpisMercadoCandidato.setUlt_visita_area_negocio(visitas.getLastVisitBusinessArea());
                            kpisMercadoCandidato.setVisita_ult_6_meses(visitas.getNumberVisitsLastSixMonths());
                        }

                        Object[] kpis_ActividadValues = kpis_ActividadProjection.getProjectionKpisActividadByCoActvad(String.valueOf(datosContacto.getCo_actvad_pral()));
                        kpisMercadoCandidato = mapper.setKpis_ActividadProjection(kpisMercadoCandidato, kpis_ActividadValues);

                        String origen_web = tsi_f_webProjection.getOrigenUrlByCoCliente(clientCode);
                        kpisMercadoCandidato.setOrigen_web(origen_web);

                        Object[] kpis_CalculadosValues = kpis_calculadosProjection.getProjectionKpisCalculadosByClientCode(clientCode);
                        kpisMercadoCandidato = mapper.setKpis_CalculadosProjection(kpisMercadoCandidato, kpis_CalculadosValues);

                        Double pr_canc3 = kpis_pr_canc_dailyProjection.getPrCancByClienCode(clientCode);
                        kpisMercadoCandidato.setPr_canc3(pr_canc3);

                        List<String> list_cp3F = vhv_cuotas_mes_v2Projection.findCp3F_ByClientCode(clientCode);
                        kpisMercadoCandidato.setMix_fy22(list_cp3F);

                        Double bee_fy22 = vhv_cuotas_mes_v2Projection.sumAmountsToCP3_F_BEEContractedInFiscalPastYear(clientCode);
                        kpisMercadoCandidato.setBee_fy22(bee_fy22);

                        List<Integer> months = Utils.getCurrentMonthsAndPreviousMonth();
                        List<String> list_cp3 = vhv_cuotas_mes_v2Projection.findCp3ActualAndPreviousMonthByClientCode(clientCode, months.get(0), months.get(1));
                        kpisMercadoCandidato.setMix(list_cp3);

                        Double pa = vhv_cuotas_mes_v2Projection.sumAmountsCurrentsContractedProducts(clientCode);
                        kpisMercadoCandidato.setPa(pa);

                        Double web = vhv_cuotas_mes_v2Projection.sumAmountsCurrentsContractedCP3(clientCode, "F_WEB");
                        kpisMercadoCandidato.setWeb(web);

                        Double web1 = vhv_cuotas_mes_v2Projection.sumAmountsCurrentsIdObjetoIsWeb(clientCode, 1);
                        kpisMercadoCandidato.setWeb1(web1);

                        Double web2 = vhv_cuotas_mes_v2Projection.sumAmountsCurrentsIdObjetoIsWeb(clientCode, 2);
                        kpisMercadoCandidato.setWeb2(web2);

                        Double bee = vhv_cuotas_mes_v2Projection.sumAmountsCurrentsContractedCP3(clientCode, "F_BEE");
                        kpisMercadoCandidato.setBee(bee);

                        String co_servicio = tsf_csord__service__cProjection.findIdByIdServicio((String) vivo[2]);
                        List<String> estados_publicacion = tdp_yext_publicacionProjection.findEstadosFacebookAndGMB(co_servicio);
                        kpisMercadoCandidato.setOk_bee(estados_publicacion);

                        String estacionalidad = estacionalidad_actvProjection.getEstacionalidadByClientCode(datosContacto.getCo_actvad_pral());
                        kpisMercadoCandidato.setEstacionalidad(estacionalidad);

                        Integer ult_mes_adw = vhv_cuotas_mes_v2Projection.findIdMesToADWByClienCode(clientCode);
                        kpisMercadoCandidato.setUlt_mes_adw(ult_mes_adw);

                        Object[] vdv_asignacion_cuenta_ecValues = vdv_asignacion_cuenta_ecProjection.getCodVendedorAndCanalByClientCode(clientCode);
                        kpisMercadoCandidato = mapper.setVdv_asignacion_cuenta_ecProjection(kpisMercadoCandidato, vdv_asignacion_cuenta_ecValues);

                        Object[] thv_actividad_comercial_cons_v2Values = thv_actividad_comercial_cons_v2Projection.getProjectionThv_Actividad_Comercial_Cons_V2ByClientCode(clientCode);
                        kpisMercadoCandidato = mapper.setThv_Actividad_Comercial_Cons_v2Projection(kpisMercadoCandidato, thv_actividad_comercial_cons_v2Values);
                        Integer llamada_2_ult_meses = thv_actividad_comercial_cons_v2Projection.getLastCallIntoLastTwoMonths(clientCode);
                        kpisMercadoCandidato.setLlamada_2_ult_meses(llamada_2_ult_meses);

                        Object[] mercanVhv_cuotas_mes_v2Values = vhv_cuotas_mes_v2Projection.findImporteMaxAndIdMesByClientCode(clientCode);
                        kpisMercadoCandidato = mapper.setMercanVhv_cuotas_mes_v2Projection(kpisMercadoCandidato, mercanVhv_cuotas_mes_v2Values);

                        Object[] tdv_usuarioValues = tdv_usuarioProjection.findEmployeeInformation(kpisMercadoCandidato.getCo_vend_cliente());
                        kpisMercadoCandidato = mapper.setTdv_UsuarioProjection(kpisMercadoCandidato, tdv_usuarioValues);
                        kpisMercadoCandidato.setCo_vendedor(kpisMercadoCandidato.getCo_vend_cliente());
                        List<Object> kpisValues = kpisMercadoCandidato.getKpisMercadoCandidato();
                        kpisValues.addAll(datosContacto.getKpisDatosContacto());
                        kpisValues.addAll(historicoSalesforce.getKpisHistoricoSalesforce());
                        kpisValues.addAll(morfologiaPyme.getKpisMorfologiaPyme());
                        kpisValues.addAll(analiticaWeb.getKpisAnaliticaWeb());
                        kpisValues.addAll(seo.getKpisSeo());
                        kpisValues.addAll(posicionamientoBuscadores.getKpisPosicionamientoBuscadores());
                        kpisValues.addAll(perfilGmb.getKpisPerfilGmb());
                        kpisValues.addAll(recomendador.getKpisRecomendador());
                        total.add(kpisValues);

                        i++;
                        if (i == 1) {
                            LOG.info("Escribiendo " + i + " registros...");
                            Excel.writeKPIsAllValues(path, nameFile, total, getKPIsMercadoCandidato());
                            LOG.info("Registros escritos correctamente");
                            total = new ArrayList<>();
                            i = 0;
                            break;
                        }
                    }
                }

            } catch (Exception e) {
                clientCodesInValid.add(clientCode);
                LOG.error("Ha ocurrido un error durante la consulta del cliente: " + clientCode);
            }
        }
        if (total.size() > 0) {
            LOG.info("Escribiendo últimos " + i + " registros...");
            Excel.writeKPIsAllValues(path, nameFile, total, getKPIsMercadoCandidato());
            LOG.info("Registros escritos correctamente");
        }

        if (clientCodesInValid.size() > 0) {
            LOG.info("Obteniendo proyecciones para códigos de clientes que han provocado excepción...");
            int x = 0;
            while (clientCodesInValid.size() > 0) {
                this.clientCodes = clientCodesInValid;
                constructProjection();
                x++;
                if (x == 3) {
                    Text.generateTxtFileWithIntegers(clientCodesInValid, path, this.getName() + "_client_codes_failed.txt");
                    break;
                }
            }
            LOG.info("Se han obtenido las proyecciones para códigos de clientes que han provocado excepción");
        }
    }

    /**
     * Método que genera el listado completo de los KPIs del mercado candidato versión 1
     *
     * @return nombes de kpis
     */
    private List<String> getKPIsMercadoCandidato() {
        return Arrays.asList("CT_MERCLIE", "TIPO_BS", "ORIGEN_WEB", "NEG_EST_HIBU", "SEGMENTO_AGRUPADO_CARTERA_YELL", "NEW_SEGM", "PR_CROSS_SELLING", "PR_UP_SELLING", "IC_PRED", "PR_CANC3",
                "MIX_FY22", "BEE_FY22", "MIX", "PA", "WEB", "WEB1", "WEB2", "BEE", "OK_BEE", "ESTACIONALIDAD",
                "ALTA_ESTACIONALIDAD", "ULT_MES_ADW", "ASIG_ACTUAL_VEND", "ASIG_ACTUAL_CANAL", "PAQUETE", "ULT_VISITA_AREA_NEGOCIO", "VISITA_ULT_6_MESES", "FE_ULT_LLAMADA", "TIPO_ULT_LLAMADA", "ORIGEN_ULT_LLAMADA",
                "USUARIO_ULT_LLAMADA", "IN_UTIL_ULT_LLAMADA", "DURACION_ULT_LLAMADA", "LLAMADA_EN_ULT_2_MESES", "MES_MAX_CUOTA_SUSC", "MAX_CUOTA_SUSC", "CO_EMPLOYEE", "NO_EMPLOYEE", "ROL_EMPLOYEE",
                "CO_CLIENTE", "CC_NOM_EMPRE", "NO_COMER", "TX_ACTVAD",
                "TX_SECTOR", "DIRECCION",
                "TX_LOCA_APA", "DE_PROV", "CO_POST_CTO", "TX_CCAA",
                "TELEFONO1", "TELEFONO_NUM", "EMAIL", "EMAIL_HUNTER", "DI_URL",
                "WEBSITE", "DOMAIN", "IMPAGO", "PERSONA_CONTACTO",
                "ULT_ANOTACION", "ULT_ANOTACION_LLAMADA", "LW", "LO", "ULT_LLAMADA_REGISTRADA", "LC",
                "VIVO", "LISTINGVERSION", "ACTVAD_EXCLUIR", "ALIANZAS",
                "EMPLEADOS", "EMPLEADOS_IMP", "VENTAS", "RATING_CREDITICIO", "FE_CREA_REG_SF",
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
                "TIMESTAMP", "PAQUETE_RECOMENDADO");
    }
}

