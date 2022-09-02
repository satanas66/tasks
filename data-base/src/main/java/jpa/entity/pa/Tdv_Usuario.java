package jpa.entity.pa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="tdv_usuario")
public class Tdv_Usuario {

    @Id
    @Column(name="id_usuario")
    private String id_usuario;

    @Column(name="id_rol_usuario")
    private String id_rol_usuario;

    @Column(name="co_vendedor")
    private String co_vendedor;

    @Column(name="no_usuario")
    private String no_usuario;

    @Column(name="ct_systemmodstamp")
    private Date ct_systemmodstamp;
}
