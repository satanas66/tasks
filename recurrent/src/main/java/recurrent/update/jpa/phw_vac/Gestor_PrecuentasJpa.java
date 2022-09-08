package recurrent.update.jpa.phw_vac;

import jpa.entity.phw_vac.Gestor_Precuentas;

public interface Gestor_PrecuentasJpa {

    Gestor_Precuentas findByByClienteFicticioAndEstadoReg(Integer clienteFicticio, String estadoReg);

    void updateGestorPrecuentasByClienteFicticioAndEstadoReg(String line);

    void updateGestorPrecuentasByClienteFicticioAndEstadoRegError(String line);
}
