package muestra.madre.domain.business;

import automation.factory.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase de negocio en la que se evalua y recoge los KPIS relacionados con los datos de contacto
 */
public class DatosContacto {

    private Integer co_cliente;

    private String cc_nom_empre;

    private String no_comer;

    private String tx_actvad;

    private String tx_sector;

    private String direccion;

    private String tx_loca_apa;

    private String de_prov;

    private String co_post_cto;

    private String tx_ccaa;

    private String telefono1;

    private String telefono_num;

    private String email;

    private String email_hunter;

    private String di_url;

    private String website;

    private String domain;

    private String impago;

    private String persona_contacto;

    private String nc_nif;

    private Integer co_empresa;

    private Integer co_actvad_pral;

    private String rating_crediticio;

    public Integer getCo_cliente() {
        return co_cliente;
    }

    public void setCo_cliente(Integer co_cliente) {
        this.co_cliente = co_cliente;
    }

    public String getCc_nom_empre() {
        return cc_nom_empre;
    }

    public void setCc_nom_empre(String cc_nom_empre) {
        this.cc_nom_empre = (Utils.evaluateString(cc_nom_empre)) ? cc_nom_empre : "";
    }

    public String getNo_comer() {
        return no_comer;
    }

    public void setNo_comer(String no_comer) {
        this.no_comer = (Utils.evaluateString(no_comer)) ? no_comer : "";
    }

    public String getTx_actvad() {
        return tx_actvad;
    }

    public void setTx_actvad(String tx_actvad) {
        this.tx_actvad = (Utils.evaluateString(tx_actvad)) ? tx_actvad : "";
    }

    public String getTx_sector() {
        return tx_sector;
    }

    public void setTx_sector(String tx_sector) {
        this.tx_sector = (Utils.evaluateString(tx_sector)) ? tx_sector : "";
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = (Utils.evaluateString(direccion)) ? direccion : "";
    }

    public String getTx_loca_apa() {
        return tx_loca_apa;
    }

    public void setTx_loca_apa(String tx_loca_apa) {
        this.tx_loca_apa = (Utils.evaluateString(tx_loca_apa)) ? tx_loca_apa : "";
    }

    public String getDe_prov() {
        return de_prov;
    }

    public void setDe_prov(String de_prov) {
        this.de_prov = (Utils.evaluateString(de_prov)) ? de_prov : "";
    }

    public String getCo_post_cto() {
        return co_post_cto;
    }

    public void setCo_post_cto(String co_post_cto) {
        this.co_post_cto = (Utils.evaluateString(co_post_cto)) ? co_post_cto : "";
    }

    public String getTx_ccaa() {
        return tx_ccaa;
    }

    public void setTx_ccaa(String tx_ccaa) {
        this.tx_ccaa = (Utils.evaluateString(tx_ccaa)) ? tx_ccaa : "";
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = (Utils.evaluateString(telefono1)) ? telefono1 : "";
    }

    public String getTelefono_num() {
        return telefono_num;
    }

    public void setTelefono_num(String telefono_num) {
        this.telefono_num = (Utils.evaluateString(telefono_num)) ? telefono_num : "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = (Utils.evaluateString(email)) ? email : "";
    }

    public String getEmail_hunter() {
        return email_hunter;
    }

    public void setEmail_hunter(String email_hunter) {
        this.email_hunter = (Utils.evaluateString(email_hunter)) ? email_hunter : "";
    }

    public String getDi_url() {
        return di_url;
    }

    public void setDi_url(String di_url) {
        this.di_url = (Utils.evaluateString(di_url)) ? di_url : "";
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = (Utils.evaluateString(website)) ? website : "";
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = (Utils.evaluateString(domain)) ? domain : "";
    }

    public String getImpago() {
        return impago;
    }

    public void setImpago(String impago) {
        this.impago = (Utils.evaluateString(impago)) ? impago : "";
    }

    public String getPersona_contacto() {
        return persona_contacto;
    }

    public void setPersona_contacto(String persona_contacto) {
        this.persona_contacto = (Utils.evaluateString(persona_contacto)) ? persona_contacto : "";
    }

    public String getNc_nif() {
        return nc_nif;
    }

    public void setNc_nif(String nc_nif) {
        this.nc_nif = (Utils.evaluateString(nc_nif)) ? nc_nif : "";
    }

    public Integer getCo_empresa() {
        return co_empresa;
    }

    public void setCo_empresa(Integer co_empresa) {
        this.co_empresa = co_empresa;
    }

    public Integer getCo_actvad_pral() {
        return co_actvad_pral;
    }

    public void setCo_actvad_pral(Integer co_actvad_pral) {
        this.co_actvad_pral = co_actvad_pral;
    }

    public String getRating_crediticio() {
        return rating_crediticio;
    }

    public void setRating_crediticio(String rating_crediticio) {
        this.rating_crediticio = (Utils.evaluateString(rating_crediticio)) ? rating_crediticio : "";
    }

    public List<Object> getKpisDatosContacto() {
        List<Object> result = new ArrayList<>();
        result.add(getCo_cliente());
        result.add(getCc_nom_empre());
        result.add(getNo_comer());
        result.add(getTx_actvad());
        result.add(getTx_sector());
        result.add(getDireccion());
        result.add(getTx_loca_apa());
        result.add(getDe_prov());
        result.add(getCo_post_cto());
        result.add(getTx_ccaa());
        result.add(getTelefono1());
        result.add(getTelefono_num());
        result.add(getEmail());
        result.add(getEmail_hunter());
        result.add(getDi_url());
        result.add(getWebsite());
        result.add(getDomain());
        result.add(getImpago());
        result.add(getPersona_contacto());
        return result;
    }

}
