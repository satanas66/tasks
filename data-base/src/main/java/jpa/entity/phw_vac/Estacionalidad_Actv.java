package jpa.entity.phw_vac;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="ESTACIONALIDAD_ACTV")
public class Estacionalidad_Actv {

    @Id
    @Column(name="CO_ACTIVIDAD")
    private Long co_actividad;

    @Column(name="DE_ACTIVIDAD")
    private String de_actividad;

    @Column(name="ESTACIONALIDAD")
    private String estacionalidad;
}
