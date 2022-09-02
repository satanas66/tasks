package jpa.entity.pa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="tdp_yext_localizacion")
public class Tdp_Yext_Localizacion {

    @Id
    @Column(name="id_localizacion")
    private String id_localizacion;

    @Column(name="co_servicio")
    private String co_servicio;
}
