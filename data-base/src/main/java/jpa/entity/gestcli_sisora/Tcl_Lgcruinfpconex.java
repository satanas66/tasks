package jpa.entity.gestcli_sisora;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="TCL_LGCRUINFPCONEX")
public class Tcl_Lgcruinfpconex {

    @Id
    @Column(name="CO_EMPRESA")
    private Integer co_empresa;

    @Column(name="PG_CONEXION")
    private Integer pg_conexion;

    @Column(name="CR_BAJA_REG")
    private String cr_baja_reg;

    @Column(name="CT_MOV")
    private String ct_mov;

    @Column(name="CT_ENTORNO")
    private String ct_entorno;

    @Column(name="ID_PROVEE_ORIG_INF")
    private String id_provee_orig_inf;//Esto me dice si el proveedor es scrapping
}
