package jpa.entity.sisora;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="TSI_VCARACT")
public class Tsi_Vcaract {

    @Id
    @Column(name="CO_CARACT")
    private String co_caract;

    @Id
    @Column(name="CV_CARACT")
    private String cv_caract;

    @Column(name="DV_CARACT")
    private String dv_caract;

    @Column(name="CR_BAJA_REG")
    private String cr_baja_reg;
}
