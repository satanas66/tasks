package jpa.entity.pa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="tdp_yext_publicacion")
public class Tdp_Yext_Publicacion {

    @Id
    @Column(name="id_localizacion")
    private String id_localizacion;

    @Column(name="co_estado_pub")
    private String co_estado_pub;

    @Column(name="co_publisher")
    private String co_publisher;
}
