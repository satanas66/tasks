package mercado.potencial.domain.business;

import automation.factory.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase de negocio en la que se evalua y recoge los KPIS relacionados con la morfología pyme
 */
public class MorfologiaPyme {

    private Integer empleados;

    private Integer empleado_impl;

    private String ventas;

    private String rating_crediticio;

    private Date createddate;

    private String rgpd;

    private String accountId;

    public Integer getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Integer empleados) {
        this.empleados = empleados;
    }

    public Integer getEmpleado_impl() {
        return empleado_impl = (null == this.empleados || this.empleados==0)?1:this.empleados;
    }

    public String getVentas() {
        return ventas;
    }

    public void setVentas(String ventas) {
        this.ventas = (Utils.evaluateString(ventas))?ventas:"";
    }

    public String getRating_crediticio() {
        return rating_crediticio;
    }

    public void setRating_crediticio(String rating_crediticio) {
        this.rating_crediticio = (Utils.evaluateString(rating_crediticio))?rating_crediticio:"";
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public String getRgpd() {
        return rgpd;
    }

    public void setRgpd(String rgpd) {
        this.rgpd = (Utils.evaluateString(rgpd))?rgpd:"";
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public List<Object> getKpisMorfologiaPyme(){
        List<Object> result = new ArrayList<>();
        result.add(getEmpleados());
        result.add(getEmpleado_impl());
        result.add(getVentas());
        result.add(getRating_crediticio());
        result.add(getCreateddate());
        result.add(getRgpd());
        return result;
    }
}
