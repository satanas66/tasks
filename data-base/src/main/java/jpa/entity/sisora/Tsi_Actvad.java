package jpa.entity.sisora;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "TSI_ACTVAD")
public class Tsi_Actvad {

    @Id
    @Column(name="CO_ACTVAD")
    private Integer co_actvad;

    @Column(name="CO_SECTOR")
    private String co_sector;

    @Column(name="CT_MERCLIE")
    private String ct_merclie;
}
