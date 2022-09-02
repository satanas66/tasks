package jpa.entity.gestford;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name="TSF_TASK")
public class Tsf_Task {

    @Id
    @Column(name="ID")
    private String id;

    @Column(name="LASTMODIFIEDDATE")
    private Date lastmodifieddate;

    @Column(name ="ACCOUNTID")
    private String accountid;
}
