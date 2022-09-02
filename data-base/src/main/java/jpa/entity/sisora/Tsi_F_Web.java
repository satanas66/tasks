package jpa.entity.sisora;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="TSI_F_WEB")
public class Tsi_F_Web {

    @Id
    @Column(name="CO_CLIENTE")
    private Integer co_cliente;

    @Column(name="DI_URL")
    private String di_url;

    @Column(name="CO_ORIGEN")
    private String co_origen;
}
