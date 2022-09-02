package recomendador.paquetes.domain.business;

import java.util.Objects;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase de negocio en la que se evalua y recoge los KPIS relacionados con el recomendador de paquetes
 */
public class RecomendadorPaquetes {

    private Integer co_cliente;

    private Integer ranking_number;

    private Integer reviews;

    private String website;

    private String claim_business;

    private String no_https;

    private String no_sitemaps;

    private String no_robots_txt;

    private String menos_2_links_entrantes;

    private Integer num_kw_en_top_10;

    private Integer num_de_kw_detectadas;

    private String mas_5segs_carga_movil;

    private String no_responsive;

    private String poco_contenido;

    private String errores_en_alt;

    private String errores_en_title;

    private Integer numero_de_paginas;

    private String ecommerce_checkout_link;

    private String social_links_facebook;

    public Integer getCo_cliente() {
        return co_cliente;
    }

    public void setCo_cliente(Integer co_cliente) {
        this.co_cliente = co_cliente;
    }

    public Integer getRanking_number() {
        return ranking_number;
    }

    public void setRanking_number(Integer ranking_number) {
        this.ranking_number = ranking_number;
    }

    public Integer getReviews() {
        return reviews;
    }

    public void setReviews(Integer reviews) {
        this.reviews = reviews;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getClaim_business() {
        return claim_business;
    }

    public void setClaim_business(String claim_business) {
        this.claim_business = claim_business;
    }

    public String getNo_https() {
        return no_https;
    }

    public void setNo_https(String no_https) {
        this.no_https = no_https;
    }

    public String getNo_sitemaps() {
        return no_sitemaps;
    }

    public void setNo_sitemaps(String no_sitemaps) {
        this.no_sitemaps = no_sitemaps;
    }

    public String getNo_robots_txt() {
        return no_robots_txt;
    }

    public void setNo_robots_txt(String no_robots_txt) {
        this.no_robots_txt = no_robots_txt;
    }

    public String getMenos_2_links_entrantes() {
        return menos_2_links_entrantes;
    }

    public void setMenos_2_links_entrantes(String menos_2_links_entrantes) {
        this.menos_2_links_entrantes = menos_2_links_entrantes;
    }

    public Integer getNum_kw_en_top_10() {
        return num_kw_en_top_10;
    }

    public void setNum_kw_en_top_10(Integer num_kw_en_top_10) {
        this.num_kw_en_top_10 = num_kw_en_top_10;
    }

    public Integer getNum_de_kw_detectadas() {
        return num_de_kw_detectadas;
    }

    public void setNum_de_kw_detectadas(Integer num_de_kw_detectadas) {
        this.num_de_kw_detectadas = num_de_kw_detectadas;
    }

    public String getMas_5segs_carga_movil() {
        return mas_5segs_carga_movil;
    }

    public void setMas_5segs_carga_movil(String mas_5segs_carga_movil) {
        this.mas_5segs_carga_movil = mas_5segs_carga_movil;
    }

    public String getNo_responsive() {
        return no_responsive;
    }

    public void setNo_responsive(String no_responsive) {
        this.no_responsive = no_responsive;
    }

    public String getPoco_contenido() {
        return poco_contenido;
    }

    public void setPoco_contenido(String poco_contenido) {
        this.poco_contenido = poco_contenido;
    }

    public String getErrores_en_alt() {
        return errores_en_alt;
    }

    public void setErrores_en_alt(String errores_en_alt) {
        this.errores_en_alt = errores_en_alt;
    }

    public String getErrores_en_title() {
        return errores_en_title;
    }

    public void setErrores_en_title(String errores_en_title) {
        this.errores_en_title = errores_en_title;
    }

    public Integer getNumero_de_paginas() {
        return numero_de_paginas;
    }

    public void setNumero_de_paginas(Integer numero_de_paginas) {
        this.numero_de_paginas = numero_de_paginas;
    }

    public String getEcommerce_checkout_link() {
        return ecommerce_checkout_link;
    }

    public void setEcommerce_checkout_link(String ecommerce_checkout_link) {
        this.ecommerce_checkout_link = ecommerce_checkout_link;
    }

    public String getSocial_links_facebook() {
        return social_links_facebook;
    }

    public void setSocial_links_facebook(String social_links_facebook) {
        this.social_links_facebook = social_links_facebook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || this.getClass() != o.getClass()){
            return false;
        }
        RecomendadorPaquetes recomendadorDto = (RecomendadorPaquetes) o;
        if(!Objects.equals(this.getCo_cliente(), recomendadorDto.getCo_cliente())) return false;
        if(!Objects.equals(this.getRanking_number(), recomendadorDto.getRanking_number())) return false;
        if(!Objects.equals(this.getReviews(), recomendadorDto.getReviews())) return false;
        if(!Objects.equals(this.getWebsite(), recomendadorDto.getWebsite())) return true;
        if(!Objects.equals(this.getClaim_business(), recomendadorDto.getClaim_business())) return false;
        if(!Objects.equals(this.getNo_https(), recomendadorDto.getNo_https())) return false;
        if(!Objects.equals(this.getNo_sitemaps(), recomendadorDto.getNo_sitemaps())) return false;
        if(!Objects.equals(this.getNo_robots_txt(), recomendadorDto.getNo_robots_txt())) return false;
        if(!Objects.equals(this.getMenos_2_links_entrantes(), recomendadorDto.getMenos_2_links_entrantes())) return false;
        if(!Objects.equals(this.getNum_kw_en_top_10(), recomendadorDto.getNum_kw_en_top_10())) return false;
        if(!Objects.equals(this.getNum_de_kw_detectadas(), recomendadorDto.getNum_de_kw_detectadas())) return false;
        if(!Objects.equals(this.getMas_5segs_carga_movil(), recomendadorDto.getMas_5segs_carga_movil())) return false;
        if(!Objects.equals(this.getNo_responsive(), recomendadorDto.getNo_responsive())) return false;
        if(!Objects.equals(this.getPoco_contenido(), recomendadorDto.getPoco_contenido())) return false;
        if(!Objects.equals(this.getErrores_en_alt(), recomendadorDto.getErrores_en_alt())) return false;
        if(!Objects.equals(this.getErrores_en_title(), recomendadorDto.getErrores_en_title())) return false;
        if(!Objects.equals(this.getNumero_de_paginas(), recomendadorDto.getNumero_de_paginas())) return false;
        if(!Objects.equals(this.getEcommerce_checkout_link(), recomendadorDto.getEcommerce_checkout_link())) return false;
        if(!Objects.equals(this.getSocial_links_facebook(), recomendadorDto.getSocial_links_facebook())) return false;

        return true;
    }

    public String getToString(){
        return getCo_cliente() + "," + getRanking_number() + "," + getReviews() + "," + getWebsite() + "," +
                getClaim_business() + "," + getNo_https() + "," + getNo_sitemaps() + "," + getNo_robots_txt() + "," +
                getMenos_2_links_entrantes() + "," + getNum_kw_en_top_10() + "," + getNum_de_kw_detectadas() + "," + getMas_5segs_carga_movil() + "," +
                getNo_responsive() + "," + getPoco_contenido() + "," + getErrores_en_alt() + "," + getErrores_en_title() + "," +
                getNumero_de_paginas() + "," + getEcommerce_checkout_link() + "," + getSocial_links_facebook();
    }
}
