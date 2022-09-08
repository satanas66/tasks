package jpa.metamodel.phw_vac;

import jpa.entity.phw_vac.Gestor_Precuentas;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-06-30T10:02:00", comments="EclipseLink-2.7.7.v20200504-rNA")
@StaticMetamodel(Gestor_Precuentas.class)
public class Gestor_Precuentas_ {

	public static volatile SingularAttribute<Gestor_Precuentas, Integer> clienteFicticio;
	public static volatile SingularAttribute<Gestor_Precuentas, String> accountIdBdc;
	public static volatile SingularAttribute<Gestor_Precuentas, String> idCampSalesforce;
	public static volatile SingularAttribute<Gestor_Precuentas, String> idUsuario;
	public static volatile SingularAttribute<Gestor_Precuentas, Date> feModiReg;
	public static volatile SingularAttribute<Gestor_Precuentas, String> txObservacion;

	public static volatile SingularAttribute<Gestor_Precuentas, String> txError;
	public static volatile SingularAttribute<Gestor_Precuentas, String> estadoReg;
	public static volatile SingularAttribute<Gestor_Precuentas, Date> feInicio;

	public static final String CLIENTE_FICTICIO = "clienteFicticio";
	public static final String ACCOUNT_ID_BDC = "accountIdBdc";
	public static final String ID_CAMP_SALESFORCE = "idCampSalesforce";
	public static final String ID_USUARIO = "idUsuario";
	public static final String FE_MODI_REG = "feModiReg";
	public static final String TX_OBSERVACION = "txObservacion";
	public static final String TX_ERROR = "txError";
	public static final String ESTADO_REG = "estadoReg";
	public static final String FE_INICIO = "feInicio";

}

