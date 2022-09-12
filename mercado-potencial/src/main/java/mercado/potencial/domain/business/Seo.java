package mercado.potencial.domain.business;

import automation.factory.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase de negocio en la que se evalua y recoge los KPIS relacionados con los datos de seo
 */
public class Seo {

    private String menos_2_links_entrantes;

    private String no_responsive;

    private String poco_contenido;

    private String errores_en_alt;

    private String errores_en_title;

    private String no_https;

    private String no_sitemaps;

    private String no_robots_txt;

    private Integer num_de_kw_detectadas;

    public String getMenos_2_links_entrantes() {
        return menos_2_links_entrantes;
    }

    public void setMenos_2_links_entrantes(String menos_2_links_entrantes) {
        this.menos_2_links_entrantes = (Utils.evaluateString(menos_2_links_entrantes))?menos_2_links_entrantes:"";
    }

    public String getNo_responsive() {
        return no_responsive;
    }

    public void setNo_responsive(String no_responsive) {
        this.no_responsive = (Utils.evaluateString(no_responsive))?no_responsive:"";
    }

    public String getPoco_contenido() {
        return poco_contenido;
    }

    public void setPoco_contenido(String poco_contenido) {
        this.poco_contenido = (Utils.evaluateString(poco_contenido))?poco_contenido:"";
    }

    public String getErrores_en_alt() {
        return errores_en_alt;
    }

    public void setErrores_en_alt(String errores_en_alt) {
        this.errores_en_alt = (Utils.evaluateString(errores_en_alt))?errores_en_alt:"";
    }

    public String getErrores_en_title() {
        return errores_en_title;
    }

    public void setErrores_en_title(String errores_en_title) {
        this.errores_en_title = (Utils.evaluateString(errores_en_title))?errores_en_title:"";
    }

    public String getNo_https() {
        return no_https;
    }

    public void setNo_https(String no_https) {
        this.no_https = (Utils.evaluateString(no_https))?no_https:"";
    }

    public String getNo_sitemaps() {
        return no_sitemaps;
    }

    public void setNo_sitemaps(String no_sitemaps) {
        this.no_sitemaps = (Utils.evaluateString(no_sitemaps))?no_sitemaps:"";
    }

    public String getNo_robots_txt() {
        return no_robots_txt;
    }

    public void setNo_robots_txt(String no_robots_txt) {
        this.no_robots_txt = (Utils.evaluateString(no_robots_txt))?no_robots_txt:"";
    }

    public Integer getNum_de_kw_detectadas() {
        return num_de_kw_detectadas;
    }

    public void setNum_de_kw_detectadas(Integer num_de_kw_detectadas) {
        this.num_de_kw_detectadas = num_de_kw_detectadas;
    }

    public List<Object> getKpisSeo(){
        List<Object> result = new ArrayList<>();
        result.add(getMenos_2_links_entrantes());
        result.add(getNo_responsive());
        result.add(getPoco_contenido());
        result.add(getErrores_en_alt());
        result.add(getErrores_en_title());
        result.add(getNo_https());
        result.add(getNo_sitemaps());
        result.add(getNo_robots_txt());
        result.add(getNum_de_kw_detectadas());
        return result;
    }
}
