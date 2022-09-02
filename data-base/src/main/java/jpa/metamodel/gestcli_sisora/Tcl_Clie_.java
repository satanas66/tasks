package jpa.metamodel.gestcli_sisora;

import jpa.entity.gestcli_sisora.Tcl_Clie;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-07-07T13:18:52", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Tcl_Clie.class)
public class Tcl_Clie_ { 

    public static volatile SingularAttribute<Tcl_Clie, Integer> co_empresa;
    public static volatile SingularAttribute<Tcl_Clie, String> cr_baja_reg;
    public static volatile SingularAttribute<Tcl_Clie, Integer> pg_pconex_comer;
    public static volatile SingularAttribute<Tcl_Clie, Date> fe_crea_reg;
    public static volatile SingularAttribute<Tcl_Clie, Date> fe_modi_reg;
    public static volatile SingularAttribute<Tcl_Clie, Integer> co_cliente;

}