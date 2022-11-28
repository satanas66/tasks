package mercado.potencial.domain.business;

import automation.factory.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase de negocio en la que se evalua y recoge los KPIS relacionados con los datos de contacto
 */
public class DatosContacto {

    private Integer clientCode;

    private String telefono_num;

    private String website;

    private String domain;

    public String getTelefono_num() {
        return telefono_num;
    }

    public void setTelefono_num(String telefono_num) {
        this.telefono_num = (Utils.evaluateString(telefono_num)) ? telefono_num : "";
    }

    public Integer getClientCode() {
        return clientCode;
    }

    public void setClientCode(Integer clientCode) {
        this.clientCode = clientCode;
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
        result.add(getClientCode());
        result.add(getTelefono_num());
        result.add(getWebsite());
        result.add(getDomain());
        return result;
    }

}
