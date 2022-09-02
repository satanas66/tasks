package jpa.entity.gestford;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="TSF_ACCOUNT")
public class Tsf_Account {

    @Id
    @Column(name="ID")
    private String id;

    @Column(name="CO_CLIENTE")
    private Integer co_cliente;

    @Column(name="CREATEDDATE")
    private Date createddate;

    @Column(name="C_RGPD03__C")
    private String rgpd03;

    @Column(name="C_RGPD06__C")
    private String rgpd06;

    @Column(name="C_RGPD07__C")
    private String rgpd07;

    @Column(name="C_CLAVE_UNICA_COMERCIAL__C")
    private String c_clave_unica_comercial_c;

    @Column(name="LASTMODIFIEDDATE")
    private Date lastmodifieddate;
}
