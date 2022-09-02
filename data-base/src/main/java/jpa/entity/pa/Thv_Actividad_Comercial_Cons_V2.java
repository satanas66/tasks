package jpa.entity.pa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="thv_actividad_comercial_cons_v2")
public class Thv_Actividad_Comercial_Cons_V2 {

    @Id
    @Column(name="id_actividad")
    private String id_actividad;

    @Column(name="tipo_registro")
    private String tipo_registro;

    @Column(name="CT_SYSTEMMODSTAMP_ORIG")
    private Date ct_systemmodstamp_orig;

    @Column(name="fe_hora_actividad")
    private Date fe_hora_actividad; //fe_ult_llamada

    @Column(name="subtipo_actividad")
    private String subtipo_actividad;//tipo_ult_llamada

    @Column(name="origen_llamada")
    private String origen_llamada;//origen_ult_llamada

    @Column(name="id_usuario")
    private String id_usuario;//usuario_ult_llamada

    @Column(name="in_util")
    private Integer in_util;//in_util_ult_llamada

    @Column(name="duracion_llamada")
    private Integer duracion_llamada;//duracion_ult_llamada

    @Column(name="co_cliente")
    private Integer co_cliente;

    @Column(name="id_mes")
    private Integer id_mes;
}
