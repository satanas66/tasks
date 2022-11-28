package jpa.entity.phw_vac;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="GESTOR_PRECUENTAS")
@Data
public class Gestor_Precuentas {

    @Id
    @Column(name = "CLIENTE_FICTICIO")
    private Integer clienteFicticio;

    @Column(name = "ACCOUNT_ID_BDC")
    private String accountIdBdc;

    @Column(name = "ESTADO_REG")
    private String estadoReg;

    @Column(name = "ID_USUARIO")
    private String idUsuario;

    @Column(name = "ID_CAMP_SALESFORCE")
    private String idCampSalesforce;

    @Column(name = "FE_INICIO")
    private Date feInicio;

    @Column(name = "FE_MODI_REG")
    private Date feModiReg;

    @Column(name = "TX_OBSERVACION")
    private String txObservacion;

    @Column(name = "TX_ERROR")
    private String txError;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DIRECCION_NO_NORMALIZADA")
    private String direccion_no_normalizada;

    @Column(name = "CO_ACTVAD")
    private String co_actvad;

    @Column(name = "TX_ACTVAD_NO_NORMALIZADA")
    private String tx_actvad_no_normalizada;
}