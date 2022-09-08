package recurrent.update.domain.business;

import lombok.Data;

import java.util.Date;

@Data
public class GestorPrecuentas {

    private Integer clienteFicticio;

    private String estadoRegAfter;

    private String accountIdBdc;

    private String estadoRegCurrent;

    private String idUsuario;

    private String idCampSalesforce;

    private Date feInicio;

    private Date feModiReg;

    private String txObservacion;

    private String txError;
}
