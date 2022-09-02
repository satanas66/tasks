package jpa.entity.gestcli_sisora;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="TCL_CLIE")
public class Tcl_Clie {

    @Id
    @Column(name="CO_CLIENTE")
    private Integer co_cliente;

    @Column(name="CO_EMPRESA")
    private Integer co_empresa;

    @Column(name="PG_PCONEX_COMER")
    private Integer pg_pconex_comer;

    @Column(name="CR_BAJA_REG")
    private String cr_baja_reg;

    @Column(name="FE_CREA_REG")
    private Date fe_crea_reg;

    @Column(name="FE_MODI_REG")
    private Date fe_modi_reg;
}
