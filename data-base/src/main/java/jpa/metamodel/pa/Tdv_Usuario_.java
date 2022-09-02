package jpa.metamodel.pa;

import jpa.entity.pa.Tdv_Usuario;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-07-29T10:07:26", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Tdv_Usuario.class)
public class Tdv_Usuario_ { 

    public static volatile SingularAttribute<Tdv_Usuario, String> id_usuario;
    public static volatile SingularAttribute<Tdv_Usuario, String> co_vendedor;
    public static volatile SingularAttribute<Tdv_Usuario, String> id_rol_usuario;
    public static volatile SingularAttribute<Tdv_Usuario, String> no_usuario;
    public static volatile SingularAttribute<Tdv_Usuario, Date> ct_systemmodstamp;
}