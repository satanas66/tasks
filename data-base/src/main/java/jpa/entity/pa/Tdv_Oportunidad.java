package jpa.entity.pa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="tdv_oportunidad")
public class Tdv_Oportunidad {

    @Id
    @Column(name="ID_OPORTUNIDAD")
    private String id_oportunidad;
}
