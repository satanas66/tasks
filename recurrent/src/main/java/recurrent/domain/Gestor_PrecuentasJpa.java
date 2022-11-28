package recurrent.domain;

import jpa.entity.phw_vac.Gestor_Precuentas;

public interface Gestor_PrecuentasJpa {

    Gestor_Precuentas findByClienteFicticioAndEstadoReg(Integer clienteFicticio, String estadoReg);

    boolean updateGestorPrecuentasByClienteFicticioAndEstadoReg(String line);

    String getProjectionByClienteFicticioAndEstadoReg(Integer phoneNumber, String estadoReg);
}
