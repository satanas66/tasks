package jpa.entity.phw_vac;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="F_IMPAGO")
public class F_Impago {

    @Id
    @Column(name="CO_CLIENTE")
    private Integer co_cliente;

    @Column(name="IN_IMPAGO")
    private String in_impago;
}
