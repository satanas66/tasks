package jpa.entity.pa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="tdv_servicio")
public class Tdv_Servicio {

    @Id
    @Column(name="id_servicio")
    private String id_servicio;

    @Column(name="id_oportunidad")
    private String id_oportunidad;

    @Column(name="tipo_pack")
    private String tipo_pack;
}
