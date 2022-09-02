package jpa.entity.pa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "thv_actividad_comercial_cons")
public class Thv_Actividad_Comercial_Cons {

    @Id
    @Column(name="id_actividad")
    private String id_actividad;

    @Column(name="co_cliente")
    private Integer co_cliente;

    @Column(name="fe_hora_actividad")
    private Date fe_hora_actividad;

    @Column(name="tipo_actividad")
    private String tipo_actividad;

    @Column(name="in_util")
    private Integer in_util;
}
