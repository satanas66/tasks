package mercado.potencial.domain.business;

import automation.factory.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase de negocio en la que se evalua y recoge los KPIS relacionados con la analitica web
 */
public class AnaliticaWeb {

    private String zoom;

    private String exists_url;

    private String social_link_facebook;

    private String social_link_twitter;

    private String social_link_instagram;

    private String social_link_linkedin;

    private Integer numero_de_paginas;

    private Integer mobilewidth;

    private Integer mobileheigth;

    private String ecommerce_checkout_link;

    private String ecommerce_booking_type;

    private String cms;

    private String mas_5segs_carga_movil;

    private String domain;

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = (Utils.evaluateString(zoom))?zoom:"";
    }

    public String getExists_url() {
        return exists_url;
    }

    public void setExists_url(String exists_url) {
        this.exists_url = (Utils.evaluateString(exists_url))?exists_url:"";
    }

    public String getSocial_link_facebook() {
        return social_link_facebook;
    }

    public void setSocial_link_facebook(String social_link_facebook) {
        this.social_link_facebook = (Utils.evaluateString(social_link_facebook))?social_link_facebook:"";
    }

    public String getSocial_link_twitter() {
        return social_link_twitter;
    }

    public void setSocial_link_twitter(String social_link_twitter) {
        this.social_link_twitter = (Utils.evaluateString(social_link_twitter))?social_link_twitter:"";
    }

    public String getSocial_link_instagram() {
        return social_link_instagram;
    }

    public void setSocial_link_instagram(String social_link_instagram) {
        this.social_link_instagram = (Utils.evaluateString(social_link_instagram))?social_link_instagram:"";
    }

    public String getSocial_link_linkedin() {
        return social_link_linkedin;
    }

    public void setSocial_link_linkedin(String social_link_linkedin) {
        this.social_link_linkedin = (Utils.evaluateString(social_link_linkedin))?social_link_linkedin:"";
    }

    public Integer getNumero_de_paginas() {
        return numero_de_paginas;
    }

    public void setNumero_de_paginas(Integer numero_de_paginas) {
        this.numero_de_paginas = numero_de_paginas;
    }

    public Integer getMobilewidth() {
        return mobilewidth;
    }

    public void setMobilewidth(Integer mobilewidth) {
        this.mobilewidth = mobilewidth;
    }

    public Integer getMobileheigth() {
        return mobileheigth;
    }

    public void setMobileheigth(Integer mobileheigth) {
        this.mobileheigth = mobileheigth;
    }

    public String getEcommerce_checkout_link() {
        return ecommerce_checkout_link;
    }

    public void setEcommerce_checkout_link(String ecommerce_checkout_link) {
        this.ecommerce_checkout_link = (Utils.evaluateString(ecommerce_checkout_link))?ecommerce_checkout_link:"";
    }

    public String getEcommerce_booking_type() {
        return ecommerce_booking_type;
    }

    public void setEcommerce_booking_type(String ecommerce_booking_type) {
        this.ecommerce_booking_type = (Utils.evaluateString(ecommerce_booking_type))?ecommerce_booking_type:"";
    }

    public String getCms() {
        return cms;
    }

    public void setCms(String cms) {
        this.cms = (Utils.evaluateString(cms))?cms:"";
    }

    public String getMas_5segs_carga_movil() {
        return mas_5segs_carga_movil;
    }

    public void setMas_5segs_carga_movil(String mas_5segs_carga_movil) {
        this.mas_5segs_carga_movil = (Utils.evaluateString(mas_5segs_carga_movil))?mas_5segs_carga_movil:"";
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<Object> getKpisAnaliticaWeb(){
        List<Object> result = new ArrayList<>();
        result.add(getZoom());
        result.add(getExists_url());
        result.add(getSocial_link_facebook());
        result.add(getSocial_link_twitter());
        result.add(getSocial_link_instagram());
        result.add(getSocial_link_linkedin());
        result.add(getNumero_de_paginas());
        result.add(getMobilewidth());
        result.add(getMobileheigth());
        result.add(getEcommerce_checkout_link());
        result.add(getEcommerce_booking_type());
        result.add(getCms());
        result.add(getMas_5segs_carga_movil());
        return result;
    }
}
