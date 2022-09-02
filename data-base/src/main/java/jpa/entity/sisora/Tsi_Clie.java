package jpa.entity.sisora;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="TSI_CLIE")
public class Tsi_Clie {

    @Id
    @Column(name="CO_CLIENTE")
    private Long co_cliente;

    @Column(name="FE_FIN_VIGEN")
    private Date fe_fin_vigen;

    @Column(name="CR_BAJA_REG")
    private String cr_baja_reg;
}
