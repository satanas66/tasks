package jpa.entity.pa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "tdv_categoria_producto")
public class Tdv_Categoria_Producto {

    @Id
    @Column(name = "id_producto")
    private String id_producto;

    @Column(name = "id_objeto")
    private String id_objeto;

    @Column(name = "no_categoria_producto_3")
    private String no_categoria_producto_3;
}
