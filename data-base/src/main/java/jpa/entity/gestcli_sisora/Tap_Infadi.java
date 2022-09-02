package jpa.entity.gestcli_sisora;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="TAP_INFADI")
public class Tap_Infadi {

    @Id
    @Column(name="CO_INF_ADI")
    private String co_inf_adi;
}
