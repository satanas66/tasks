package mercado.candidato.domain.business;

import automation.factory.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KpisMercadoCandidato {

    /**
     * Colección KPIS_ACTIVIDAD
     */
    private String ct_merclie;

    private String tipo_bs;

    public String getCt_merclie() {
        return ct_merclie;
    }

    public void setCt_merclie(String ct_merclie) {
        this.ct_merclie = (Utils.evaluateString(ct_merclie)) ? ct_merclie : "";
    }

    public String getTipo_bs() {
        return tipo_bs;
    }

    public void setTipo_bs(String tipo_bs) {
        this.tipo_bs = (Utils.evaluateString(tipo_bs)) ? tipo_bs : "";
    }

    /**
     * Tabla TSI_F_WEB
     */
    private String origen_web;

    public String getOrigen_web() {
        return origen_web;
    }

    public void setOrigen_web(String origen_web) {
        this.origen_web = origen_web;
        if (Utils.evaluateString(origen_web)) {
            this.origen_web = origen_web.equalsIgnoreCase("PRO") ? "H" : "O";
        }
    }

    /**
     * Colección  KPIS_CALCULADOS
     */
    private Double neg_est;

    private String segmento;

    private String new_segm;

    private Double pr_cross_selling;

    private Double pr_up_selling;

    private Double ic_pred;

    public Double getNeg_est() {
        return neg_est;
    }

    public void setNeg_est(Double neg_est) {
        this.neg_est = neg_est;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = (Utils.evaluateString(segmento)) ? segmento : "";
    }

    public String getNew_segm() {
        return new_segm;
    }

    public void setNew_segm(String new_segm) {
        this.new_segm = (Utils.evaluateString(new_segm)) ? new_segm : "";
    }

    public Double getPr_cross_selling() {
        return pr_cross_selling;
    }

    public void setPr_cross_selling(Double pr_cross_selling) {
        this.pr_cross_selling = pr_cross_selling;
    }

    public Double getPr_up_selling() {
        return pr_up_selling;
    }

    public void setPr_up_selling(Double pr_up_selling) {
        this.pr_up_selling = pr_up_selling;
    }

    public Double getIc_pred() {
        return ic_pred;
    }

    public void setIc_pred(Double ic_pred) {
        this.ic_pred = ic_pred;
    }

    /**
     * Colección KPIS_PR_CANC_DAILY
     */
    private Double pr_canc3;

    public Double getPr_canc3() {
        return pr_canc3;
    }

    public void setPr_canc3(Double pr_canc3) {
        this.pr_canc3 = pr_canc3;
    }

    /**
     * MIX_FY22
     */
    private String mix_fy22;

    public String getMix_fy22() {
        return mix_fy22;
    }

    public void setMix_fy22(List<String> listCp3F) {
        this.mix_fy22 = "";
        if(listCp3F != null && listCp3F.size() > 0){
            for(int i=0; i<listCp3F.size(); i++){
                if(i==0){
                    this.mix_fy22 = listCp3F.get(i).substring(2);
                }else{
                    this.mix_fy22 = this.mix_fy22+"-"+listCp3F.get(i).substring(2);
                }
            }
        }
    }


    private Double bee_fy22;

    public Double getBee_fy22() {
        return bee_fy22;
    }

    public void setBee_fy22(Double bee_fy22) {
        this.bee_fy22 = bee_fy22;
    }

    /**
     * MIX
     */
    private String mix;

    public String getMix() {
        return mix;
    }

    public void setMix(List<String> listMix) {
        this.mix = "";
        if(listMix != null && listMix.size() > 0){
            for(int i=0; i<listMix.size(); i++){
                if(i==0){
                    this.mix = listMix.get(i).substring(2);
                }else{
                    this.mix = this.mix+"-"+listMix.get(i).substring(2);
                }
            }
        }
    }

    /**   AQUÍ FALTAn 7 KPIS  */
    private Double pa;
    private Double web;
    private Double web1;
    private Double web2;
    private Double bee;
    private String ok_bee;

    public Double getPa() {
        return pa;
    }

    public void setPa(Double pa) {
        this.pa = pa;
    }

    public Double getWeb() {
        return web;
    }

    public void setWeb(Double web) {
        this.web = web;
    }

    public Double getWeb1() {
        return web1;
    }

    public void setWeb1(Double web1) {
        this.web1 = web1;
    }

    public Double getWeb2() {
        return web2;
    }

    public void setWeb2(Double web2) {
        this.web2 = web2;
    }

    public Double getBee() {
        return bee;
    }

    public void setBee(Double bee) {
        this.bee = bee;
    }

    public String getOk_bee() {
        return ok_bee;
    }

    public void setOk_bee(List<String> estados_publicacion) {
        if(estados_publicacion != null){
            for(String estado : estados_publicacion){
                this.ok_bee = "OK";
                if(estado.contains("WAITING")){
                    this.ok_bee = "EN_PROCESO";
                    break;
                }
            }
        }
    }

    /**
     * Tabla ESTACIONALIDAD
     */
    private String estacionalidad;

    private String alta_estacionalidad;

    public String getEstacionalidad() {
        return estacionalidad;
    }

    public void setEstacionalidad(String estacionalidad) {
        this.estacionalidad = (Utils.evaluateString(estacionalidad)) ? estacionalidad : "";
    }

    public String getAlta_estacionalidad() {
        alta_estacionalidad = "";
        if (Utils.evaluateString(this.estacionalidad)) {
            String[] meses = estacionalidad.split(" ");

            for (int i = 0; i < meses.length; i++) {
                if (meses[i].contains("***")) {
                    alta_estacionalidad = alta_estacionalidad + meses[i] + " ";
                }
            }
        }
        return alta_estacionalidad;
    }

    /** ULT_MES_ADW => Vista VHV_CUOTAS_MES_V2*/

    private Integer ult_mes_adw;

    public Integer getUlt_mes_adw() {
        return ult_mes_adw;
    }

    public void setUlt_mes_adw(Integer ult_mes_adw) {
        this.ult_mes_adw = ult_mes_adw;
    }

    /**
     * Vista VDV_ASIGNACION_CUENTA_EC
     */
    private String co_vend_cliente;//asig_actual_vend

    private String co_canal;//asig_actual_canal

    public String getCo_vend_cliente() {
        return co_vend_cliente;
    }

    public void setCo_vend_cliente(String co_vend_cliente) {
        this.co_vend_cliente = co_vend_cliente;
    }

    public String getCo_canal() {
        return co_canal;
    }

    public void setCo_canal(String co_canal) {
        this.co_canal = co_canal;
    }

    /**  Mapa VISITAS  */
    private String paquete;
    private Integer ult_visita_area_negocio;
    private Integer visita_ult_6_meses;

    public String getPaquete() {
        return paquete;
    }

    public void setPaquete(String paquete) {
        this.paquete = (Utils.evaluateString(paquete))?paquete:"";
    }

    public Integer getUlt_visita_area_negocio() {
        return ult_visita_area_negocio;
    }

    public void setUlt_visita_area_negocio(Integer ult_visita_area_negocio) {
        this.ult_visita_area_negocio = ult_visita_area_negocio;
    }

    public Integer getVisita_ult_6_meses() {
        return visita_ult_6_meses;
    }

    public void setVisita_ult_6_meses(Integer visita_ult_6_meses) {
        this.visita_ult_6_meses = visita_ult_6_meses;
    }

    /**
     * Tabla THV_ACTIVIDAD_COMERCIAL_CONS_V2
     */
    private Date fe_hora_actividad; //fe_ult_llamada

    private String subtipo_actividad;//tipo_ult_llamada

    private String origen_llamada;//origen_ult_llamada

    private String id_usuario;//usuario_ult_llamada

    private Integer in_util;//in_util_ult_llamada

    private Integer duracion_llamada;//duracion_ult_llamada

    private String llamada_2_ult_meses;


    public Date getFe_hora_actividad() {
        return fe_hora_actividad;
    }

    public void setFe_hora_actividad(Date fe_hora_actividad) {
        this.fe_hora_actividad = fe_hora_actividad;
    }

    public String getSubtipo_actividad() {
        return subtipo_actividad;
    }

    public void setSubtipo_actividad(String subtipo_actividad) {
        this.subtipo_actividad = (Utils.evaluateString(subtipo_actividad)) ? subtipo_actividad : "";
    }

    public String getOrigen_llamada() {
        return origen_llamada;
    }

    public void setOrigen_llamada(String origen_llamada) {
        this.origen_llamada = (Utils.evaluateString(origen_llamada)) ? origen_llamada : "";
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = (Utils.evaluateString(id_usuario)) ? id_usuario : "";
    }

    public Integer getIn_util() {
        return in_util;
    }

    public void setIn_util(Integer in_util) {
        this.in_util = in_util;
    }

    public Integer getDuracion_llamada() {
        return duracion_llamada;
    }

    public void setDuracion_llamada(Integer duracion_llamada) {
        this.duracion_llamada = duracion_llamada;
    }

    public String getLlamada_2_ult_meses() {
        return llamada_2_ult_meses;
    }

    public void setLlamada_2_ult_meses(Integer llamada_2_ult_meses) {
        this.llamada_2_ult_meses = (llamada_2_ult_meses != null) ? "SI" : "NO";
    }

    /**
     * Vista VHV_CUOTAS_MES_V2
     */
    private Integer idmes; //mes_max_cuota_susc

    private Double importe;//max_cuota_susc

    public Integer getIdmes() {
        return idmes;
    }

    public void setIdmes(Integer idmes) {
        this.idmes = idmes;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    /**
     * Tabla TDV_USUARIO
     */
    private String co_vendedor;

    private String nombre_vendedor;

    private String rol_vendedor;

    public String getCo_vendedor() {
        return co_vendedor;
    }

    public void setCo_vendedor(String co_vendedor) {
        this.co_vendedor = (Utils.evaluateString(co_vendedor))?"PH0"+co_vendedor:"";
    }

    public String getNombre_vendedor() {
        return nombre_vendedor;
    }

    public void setNombre_vendedor(String nombre_vendedor) {
        this.nombre_vendedor = (Utils.evaluateString(nombre_vendedor))?nombre_vendedor:"";
    }

    public String getRol_vendedor() {
        return rol_vendedor;
    }

    public void setRol_vendedor(String rol_vendedor) {
        this.rol_vendedor = (Utils.evaluateString(rol_vendedor))?rol_vendedor:"";
    }

    public List<Object> getKpisMercadoCandidato(){
        List<Object> result = new ArrayList<>();

        result.add(getCt_merclie());
        result.add(getTipo_bs());

        result.add(getOrigen_web());

        result.add(getNeg_est());
        result.add(getSegmento());
        result.add(getNew_segm());
        result.add(getPr_cross_selling());
        result.add(getPr_up_selling());
        result.add(getIc_pred());

        result.add(getPr_canc3());

        result.add(getMix_fy22());

        result.add(getBee_fy22());

        result.add(getMix());

        result.add(getPa());
        result.add(getWeb());
        result.add(getWeb1());
        result.add(getWeb2());
        result.add(getBee());
        result.add(getOk_bee());

        result.add(getEstacionalidad());
        result.add(getAlta_estacionalidad());

        result.add(getUlt_mes_adw());

        result.add(getCo_vend_cliente());
        result.add(getCo_canal());

        result.add(getPaquete());
        result.add(getUlt_visita_area_negocio());
        result.add(getVisita_ult_6_meses());

        result.add(getFe_hora_actividad());
        result.add(getSubtipo_actividad());
        result.add(getOrigen_llamada());
        result.add(getId_usuario());
        result.add(getIn_util());
        result.add(getDuracion_llamada());
        result.add(getLlamada_2_ult_meses());

        result.add(getIdmes());
        result.add(getImporte());

        result.add(getCo_vendedor());
        result.add(getNombre_vendedor());
        result.add(getRol_vendedor());

        return result;
    }
}
