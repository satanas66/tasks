package jpa.entity.pa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="tdv_oportunidades_por_cliente")
public class Tdv_Oportunidades_Por_Cliente {

    @Id
    @Column(name="CO_CLIENTE")
    private Long co_cliente;

    @Column(name="FE_CREACION_DMY")
    private String fe_creacion_dmy;

    @Column(name="FE_GANADA_DMY")
    private String fe_ganada_dmy;

    @Column(name="CT_SYSTEMMODSTAMP")
    private Date ct_systemmodstamp;
}
