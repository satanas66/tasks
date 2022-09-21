package jpa.entity.phw_vac;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="F_DATOS_CONTACTO")
public class F_Datos_Contacto {

    @Id
    @Column(name = "CO_CLIENTE")
    private Integer co_cliente;

    @Column(name = "CO_CCAA")
    private String co_ccaa;
    @Column(name = "TX_CCAA")
    private String tx_ccaa;

    @Column(name = "CC_NOM_EMPRE")
    private String cc_nom_empre;
    @Column(name = "NO_COMER")
    private String no_comer;

    @Column(name = "CO_POST_CTO")
    private String co_post_cto;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "TX_LOCA_APA")
    private String tx_loca_apa;
    @Column(name = "DE_PROV")
    private String de_prov;

    @Column(name = "TELEFONO1")
    private String telefono1;
    @Column(name = "TELEFONO2")
    private String telefono2;
    @Column(name = "TELEFONO3")
    private String telefono3;
    @Column(name = "TELEFONO4")
    private String telefono4;
    @Column(name = "TELEFONO5")
    private String telefono5;

    @Column(name = "CO_ACTVAD_PRAL")
    private Integer co_actvad_pral;
    @Column(name = "TX_ACTVAD")
    private String tx_actvad;

    @Column(name = "MOVIL")
    private String movil;
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "RATING_CREDITICIO")
    private String rating_crediticio;

    @Column(name = "NC_NIF")
    private String nc_nif;

    @Column(name = "CO_EMPRESA")
    private Integer co_empresa;
}
