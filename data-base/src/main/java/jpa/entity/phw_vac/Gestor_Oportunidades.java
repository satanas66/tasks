package jpa.entity.phw_vac;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "GESTOR_OPORTUNIDADES")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Gestor_Oportunidades {
    @Id
    @Column(name = "ID_GESTOR")
    private Integer id_gestor;

    @Column(name = "CO_CLIENTE", nullable=false)
    private Integer co_cliente;

    @Column(name = "ID_ACCOUNT", nullable=false)
    private String id_account;

    @Column(name = "ORIGEN_OPORTUNIDAD")
    private String origen_oportunidad;

    @Column(name = "TIPO_OPORTUNIDAD")
    private String tipo_oportunidad;

    @Column(name = "DESC_OPORTUNIDAD")
    private String desc_oportunidad;

    @Column(name = "ASIG_OPORTUNIDAD", nullable=false)
    private String asig_oportunidad;

    @Column(name = "ID_USUARIO")
    private String id_usuario;

    @Column(name = "ID_CAMP_SALESFORCE")
    private String id_camp_salesforce;

    @Column(name = "FE_INICIO_REG", nullable=false)
    private Date fe_inicio_reg;

    @Column(name = "FE_FIN_REG", nullable=false)
    private Date fe_fin_reg;

    @Column(name = "ZOOM")
    private String zoom;

    @Column(name = "CONCURRENCIA")
    private String concurrencia;

    @Column(name = "ESTADO_REG")
    private String estado_reg;

    @Column(name = "ESTADO_REG_SALESFORCE")
    private String estado_reg_salesforce;

    @Column(name = "ESTADO_ACCOUNT")
    private String estado_account;

    @Column(name = "CARGA_DE_TRABAJO")
    private Integer carga_de_trabajo;

    @Column(name = "PRIORIDAD")
    private Integer prioridad;

    @Column(name = "RANKING")
    private Integer ranking;

    @Column(name = "USER_CREA_REG")
    private String user_crea_reg;

    @Column(name = "USER_MODI_REG")
    private String user_modi_reg;

    @Column(name = "FE_CREA_REG")
    private Date fe_crea_reg;

    @Column(name = "FE_MODI_REG")
    private Date fe_modi_reg;

    @Column(name = "FE_BAJA_REG")
    private Date fe_baja_reg;

    @Column(name = "DESC_OPORTUNIDAD_LARGA")
    private String desc_oportunidad_larga;

    @Column(name = "CO_CAMP")
    private String co_camp;

    @Column(name = "CO_AMBI_COMER")
    private String co_ambi_comer;

    @Column(name = "TERRITORIO")
    private String territorio;

    @Column(name = "VALOR_OPP")
    private Integer valor_opp;

    @Column(name = "TX_ERROR")
    private String tx_error;

    @Column(name = "ID_OPP")
    private String id_opp;

    @Column(name = "ID_OPP_SERV")
    private Integer id_opp_serv;

    @Column(name = "TRIMESTRE")
    private String trimestre;

    @Column(name = "MES_OBJ")
    private String mes_obj;

    @Column(name = "OFERTA_VALOR")
    private String oferta_valor;

    @Column(name = "FE_LIMITE_GESTION")
    private Date fe_limite_gestion;

    @Column(name = "CIERRE_AUTOMATICO")
    private String cierre_automatico;

    @Column(name = "RECARGA_PRE")
    private String recarga_pre;

    @Column(name = "CANAL_INICIAL")
    private String canal_inicial;

    @Column(name = "PROVEEDOR_INICIAL")
    private String proveedor_inicial;

    @Column(name = "ASIGNADO_POR")
    private String asignado_por;

    @Column(name = "CO_SECTOR")
    private String co_sector;

    @Column(name = "TX_SECTOR")
    private String tx_sector;

    @Column(name = "TX_ASIGNACION")
    private String tx_asignacion;

    @Column(name = "CO_AUTONOMIA")
    private String co_autonomia;

    @Column(name = "TX_AUTONOMIA")
    private String tx_autonomia;
}