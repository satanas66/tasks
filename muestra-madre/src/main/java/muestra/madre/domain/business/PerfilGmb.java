package muestra.madre.domain.business;

import automation.factory.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase de negocio en la que se evalua y recoge los KPIS relacionados con el perfil gmb
 */
public class PerfilGmb {

    private String page_url;

    private String nombre_google;

    private Integer reviews;

    private Double total_rating;

    private String claim_business;

    private Date timestamp;

    public String getPage_url() {
        return page_url;
    }

    public void setPage_url(String page_url) {
        this.page_url = (Utils.evaluateString(page_url))?page_url:"";
    }

    public String getNombre_google() {
        return nombre_google;
    }

    public void setNombre_google(String nombre_google) {
        this.nombre_google = (Utils.evaluateString(nombre_google))?nombre_google:"";
    }

    public Integer getReviews() {
        return reviews;
    }

    public void setReviews(Integer reviews) {
        this.reviews = reviews;
    }

    public Double getTotal_rating() {
        return total_rating;
    }

    public void setTotal_rating(Double total_rating) {
        this.total_rating = total_rating;
    }

    public String getClaim_business() {
        return claim_business;
    }

    public void setClaim_business(String claim_business) {
        this.claim_business = (Utils.evaluateString(claim_business))?claim_business:"";
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<Object> getKpisPerfilGmb(){
        List<Object> result = new ArrayList<>();
        result.add(getPage_url());
        result.add(getNombre_google());
        result.add(getReviews());
        result.add(getTotal_rating());
        result.add(getClaim_business());
        result.add(getTimestamp());
        return result;
    }
}
