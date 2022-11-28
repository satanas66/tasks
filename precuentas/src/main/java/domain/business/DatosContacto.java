package domain.business;

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

    private String telefono_num;

    private String website;

    private String domain;

    public Integer getCo_cliente() {
        return co_cliente;
    }

    public void setCo_cliente(Integer co_cliente) {
        this.co_cliente = co_cliente;
    }

    public String getTelefono_num() {
        return telefono_num;
    }

    public void setTelefono_num(String telefono_num) {
        this.telefono_num = (Utils.evaluateString(telefono_num)) ? telefono_num : "";
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

    public List<Object> getKpisDatosContacto() {
        List<Object> result = new ArrayList<>();
        result.add(getCo_cliente());
        result.add(getTelefono_num());
        result.add(getWebsite());
        result.add(getDomain());
        return result;
    }
}
