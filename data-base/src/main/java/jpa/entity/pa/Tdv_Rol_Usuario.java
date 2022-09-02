package jpa.entity.pa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="tdv_rol_usuario")
public class Tdv_Rol_Usuario {

    @Id
    @Column(name="id_rol_usuario")
    private String id_rol_usuario;

    @Column(name="no_rol_usuario")
    private String no_rol_usuario;
}
