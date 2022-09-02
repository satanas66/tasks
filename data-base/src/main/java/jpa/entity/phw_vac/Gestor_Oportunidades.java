package jpa.entity.phw_vac;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="GESTOR_OPORTUNIDADES")
public class Gestor_Oportunidades {
    @Id
    @Column(name="ID_GESTOR")
    private Integer id_gestor;

    @Column(name="CO_CLIENTE")
    private Integer co_cliente;

    @Column(name="ESTADO_REG")
    private String estado_reg;

    @Column(name="FE_BAJA_REG")
    private Date fe_baja_reg;

    @Column(name="FE_MODI_REG")
    private Date fe_modi_reg;
}
