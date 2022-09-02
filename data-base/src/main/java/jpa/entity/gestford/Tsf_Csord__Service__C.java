package jpa.entity.gestford;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="tsf_csord__service__c")
public class Tsf_Csord__Service__C {

    @Id
    @Column(name="id")
    private String id;

    @Column(name="c_id_servicio__c")
    private String c_id_servicio__c;
}
