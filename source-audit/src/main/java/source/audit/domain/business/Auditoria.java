package source.audit.domain.business;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase de negocio en la que se evalua y recoge los KPIS que auditoran los registros
 */
@Data
public class Auditoria {

    private Integer co_cliente;

    private String origen_dato;

    private String tipo_operacion;

    private Date fe_creacion;

    private Date fe_modificacion;

    public List<Object> getKpisAuditoria(){
        List<Object> result = new ArrayList<>();
        result.add(getCo_cliente());
        result.add(getFe_creacion());
        result.add(getFe_modificacion());
        result.add(getOrigen_dato());
        result.add(getTipo_operacion());
        return result;
    }
}
