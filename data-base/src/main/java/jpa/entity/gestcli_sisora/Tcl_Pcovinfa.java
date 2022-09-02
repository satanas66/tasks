package jpa.entity.gestcli_sisora;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="TCL_PCOVINFA")
public class Tcl_Pcovinfa {

    @Id
    @Column(name="CO_EMPRESA")
    private Integer co_empresa;

    @Column(name="CO_INF_ADI")
    private String co_inf_adi;

    @Column(name="Cv_INF_ADI")
    private String cv_inf_adi;

    @Column(name="PG_PCONEX")
    private Integer pg_pconex;

    @Column(name="CR_BAJA_REG")
    private String cr_baja_reg;
}
