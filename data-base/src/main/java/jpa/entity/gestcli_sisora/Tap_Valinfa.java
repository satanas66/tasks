package jpa.entity.gestcli_sisora;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="TPA_VALINFA")
public class Tap_Valinfa {

    @Id
    @Column(name="CO_INF_ADI")
    private String co_inf_adi;

    @Column(name="CV_INF_ADI")
    private String cv_inf_adi;
}
