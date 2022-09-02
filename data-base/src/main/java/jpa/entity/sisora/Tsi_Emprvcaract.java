package jpa.entity.sisora;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="TSI_EMPRVCARACT")
public class Tsi_Emprvcaract {

    @Id
    @Column(name="CO_EMPRESA")
    private Integer co_empresa;

    @Id
    @Column(name="CO_CARACT")
    private String co_caract;

    @Id
    @Column(name="CV_CARACT")
    private String cv_caract;

    @Column(name="NU_VCARACT_EMPR")
    private Integer nu_vcaract_empr;

    @Column(name="CR_BAJA_REG")
    private String cr_baja_reg;
}
