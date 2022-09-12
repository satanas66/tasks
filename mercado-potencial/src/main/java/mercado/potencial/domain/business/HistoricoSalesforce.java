package mercado.potencial.domain.business;

import automation.factory.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase de negocio en la que se evalua y recoge los KPIS relacionados con el historico salesforce
 */
public class HistoricoSalesforce {

    private Date ult_anotacion;//tsf_task

    private Date ult_anotacion_llamada;//thv_actividad_comercial_cons

    private String lw;

    private String lo;

    private String ultima_llamada_registrada;//depende de fe_creacion_dmy

    private String lc;

    private String vivo;

    private Integer listingVersion;

    private String actividad_excluir;

    private String alianzas;

    public Date getUlt_anotacion() {
        return ult_anotacion;
    }

    public void setUlt_anotacion(Date ult_anotacion) {
        this.ult_anotacion = ult_anotacion;
    }

    public Date getUlt_anotacion_llamada() {
        return ult_anotacion_llamada;
    }

    public void setUlt_anotacion_llamada(Date ult_anotacion_llamada) {
        this.ult_anotacion_llamada = ult_anotacion_llamada;
    }

    public String getLw() {
        return lw;
    }

    public void setLw(String lw) {
        this.lw = (Utils.evaluateString(lw))?lw:"";
    }

    public String getLo() {
        return lo;
    }

    public void setLo(String lo) {
        this.lo = (Utils.evaluateString(lo))?lo:"";
    }

    public String getUltima_llamada_registrada() {
        return ultima_llamada_registrada;
    }

    public void setUltima_llamada_registrada() {
        this.ultima_llamada_registrada = (Utils.evaluateString(this.lo))?"S":"N";
    }

    public String getLc() {
        return lc;
    }

    public void setLc() {
        this.lc = (Utils.evaluateString(this.lo))?this.lo:"";
    }

    public String getVivo() {
        return vivo;
    }

    public void setVivo(String vivo) {
        this.vivo = (Utils.evaluateString(vivo))?"S":"N";
    }

    public Integer getListingVersion() {
        return listingVersion;
    }

    public void setListingVersion(Integer listingVersion) {
        this.listingVersion = listingVersion;
    }

    public String getActividad_excluir() {
        return actividad_excluir;
    }

    public void setActividad_excluir(List<String> actividades, Integer actividad_excluir) {
        this.actividad_excluir = actividades.contains(actividad_excluir)?"S":"N";
    }

    public String getAlianzas() {
        return alianzas;
    }

    public void setAlianzas(String alianzas) {
        this.alianzas = (Utils.evaluateString(alianzas))?alianzas:"";
    }

    public List<Object> getKpisHistoricoSalesforce() {
        List<Object> result = new ArrayList<>();
        result.add(getUlt_anotacion());
        result.add(getUlt_anotacion_llamada());
        result.add(getLw());
        result.add(getLo());
        result.add(getUltima_llamada_registrada());
        result.add(getLc());
        result.add(getVivo());
        result.add(getListingVersion());
        result.add(getActividad_excluir());
        result.add(getAlianzas());
        return result;
    }
}
