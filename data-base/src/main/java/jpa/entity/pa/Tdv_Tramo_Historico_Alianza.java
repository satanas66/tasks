package jpa.entity.pa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="tdv_tramo_historico_alianza")
public class Tdv_Tramo_Historico_Alianza {

    @Id
    @Column(name="co_cliente")
    private Integer co_cliente;

    @Column(name="alianza")
    private String alianza;

    @Column(name="ct_systemmodstamp")
    private Date ct_systemmodstamp;
}
