package jpa.entity.gestcli_sisora;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="TIPCL_CAMP_RESPUESTA" )
public class  Tipcl_Camp_Respuesta {

    @Id
    @Column(name="FE_MOVIMIENTO")
    private Date fe_movimiento;

    @Column(name="ACCOUNT_ID")
    private String account_id;

    @Column(name="CO_CLIENTE")
    private Integer co_cliente;

    @Column(name="FE_INSERCION")
    private Date fe_insercion;

    @Column(name="FE_TRATADO")
    private Date fe_tratado;

    @Column(name="ESTADO_CRUCE")
    private String estado_cruce;

    @Column(name="DESCRIPCION_ESTADO")
    private String descripcion_estado;
}
