package recurrent.domain.business;

import lombok.Data;

import java.util.Date;

@Data
public class GestorOportunidades {

    private Integer id_gestor;

    private Integer co_cliente;

    private String id_account;

    private String origen_oportunidad;

    private String tipo_oportunidad;

    private String desc_oportunidad;

    private String asig_oportunidad;

    private String id_usuario;

    private String id_camp_salesforce;

    private Date fe_inicio_reg;

    private Date fe_fin_reg;

    private String zoom;

    private String concurrencia;

    private String estado_reg;

    private String estado_reg_salesforce;

    private String estado_account;

    private Integer carga_de_trabajo;

    private Integer prioridad;

    private Integer ranking;

    private String user_crea_reg;

    private String user_modi_reg;

    private Date fe_crea_reg;

    private Date fe_modi_reg;

    private Date fe_baja_reg;

    private String desc_oportunidad_larga;

    private String co_camp;

    private String co_ambi_comer;

    private String territorio;

    private Integer valor_opp;

    private String tx_error;

    private String id_opp;

    private Integer id_opp_serv;

    private String trimestre;

    private String mes_obj;

    private String oferta_valor;

    private Date fe_limite_gestion;

    private String cierre_automatico;

    private String recarga_pre;

    private String canal_inicial;

    private String proveedor_inicial;

    private String asignado_por;

    private String co_sector;

    private String tx_sector;

    private String tx_asignacion;

    private String co_autonomia;

    private String tx_autonomia;
}
