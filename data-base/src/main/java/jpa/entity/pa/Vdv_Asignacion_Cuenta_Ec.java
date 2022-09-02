package jpa.entity.pa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="vdv_asignacion_cuenta_ec")
public class Vdv_Asignacion_Cuenta_Ec {

    @Id
    @Column(name="co_cliente")
    private String co_cliente;

    @Column(name="co_vend_cliente")
    private String co_vend_cliente;

    @Column(name="co_canal")
    private String co_canal;

    @Column(name="no_empleado")
    private String no_empleado;
}
