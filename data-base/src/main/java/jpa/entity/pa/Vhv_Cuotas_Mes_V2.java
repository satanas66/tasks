package jpa.entity.pa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="vhv_cuotas_mes_v2")
public class Vhv_Cuotas_Mes_V2 {

    @Id
    @Column(name="id_servicio")
    private String id_servicio;

    @Column(name="id_oportunidad")
    private String id_oportunidad;

    @Column(name="co_cliente")
    private String co_cliente;

    @Column(name="id_mes")
    private Integer id_mes;

    @Column(name="id_producto")
    private String id_producto;

    @Column(name="id_objeto")
    private String id_objeto;

    @Column(name="importe")
    private Double importe;

    @Column(name="cp3")
    private String cp3;
}
