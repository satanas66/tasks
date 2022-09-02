package jpa.entity.sisora;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="TSI_SECTOR")
public class Tsi_Sector {

    @Id
    @Column(name="CO_SECTOR")
    private String co_sector;

    @Column(name="CO_SUBSECTOR")
    private String co_subSector;

    @Column(name="CO_SSUBSECTOR")
    private String co_ssubSector;

    @Column(name="TX_SECTOR")
    private String tx_sector;

    @Column(name="CR_BAJA_REG")
    private String cr_baja_reg;
}
