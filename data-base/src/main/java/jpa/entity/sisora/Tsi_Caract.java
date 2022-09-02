package jpa.entity.sisora;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="TSI_CARACT")
public class Tsi_Caract {

    @Id
    @Column(name="CO_CARACT")
    private String co_caract;

    @Column(name="CR_BAJA_REG")
    private String cr_baja_reg;
}
