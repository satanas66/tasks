package mercado.potencial.domain.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase de negocio en la que se evalua y recoge los KPIS que auditoran los registros
 */
public class Auditoria {

    private Integer co_cliente;

    private String origen_dato;

    private String tipo_operacion;

    private Date fe_creacion;

    private Date fe_modificacion;

    public Integer getCo_cliente() {
        return co_cliente;
    }

    public void setCo_cliente(Integer co_cliente) {
        this.co_cliente = co_cliente;
    }

    public String getOrigen_dato() {
        return origen_dato;
    }

    public void setOrigen_dato(String origen_dato) {
        this.origen_dato = origen_dato;
    }

    public String getTipo_operacion() {
        return tipo_operacion;
    }

    public void setTipo_operacion(String tipo_operacion) {
        this.tipo_operacion = tipo_operacion;
    }

    public Date getFe_creacion() {
        return fe_creacion;
    }

    public void setFe_creacion(Date fe_creacion) {
        this.fe_creacion = fe_creacion;
    }

    public Date getFe_modificacion() {
        return fe_modificacion;
    }

    public void setFe_modificacion(Date fe_modificacion) {
        this.fe_modificacion = fe_modificacion;
    }

    public List<Object> getKpisAuditoria(){
        List<Object> result = new ArrayList<>();
        result.add(getCo_cliente());
        result.add(getFe_creacion());
        result.add(getFe_modificacion());
        result.add(getOrigen_dato());
        result.add(getTipo_operacion());
        return result;
    }

    private String fe_creacion_s;

    private String fe_modificacion_s;

    public String getFe_creacion_s() {
        return fe_creacion_s;
    }

    public void setFe_creacion_s(String fe_creacion_s) {
        this.fe_creacion_s = fe_creacion_s;
    }

    public String getFe_modificacion_s() {
        return fe_modificacion_s;
    }

    public void setFe_modificacion_s(String fe_modificacion_s) {
        this.fe_modificacion_s = fe_modificacion_s;
    }

    public String auditoriaValue(){
        return getCo_cliente()+";"+getFe_creacion_s()+";"+getFe_modificacion_s()+";"+getOrigen_dato()+";"+getTipo_operacion();
    }
}
