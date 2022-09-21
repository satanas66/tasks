package recurrent.jpa.phw_vac;

import jpa.entity.phw_vac.Gestor_Oportunidades;

public interface Gestor_OportunidadesJpa {

    public boolean insertGestor_Oportunidades(String line);

    public Gestor_Oportunidades getGestorOportunidadesByCoClienteAndIdAccount(Integer co_cliente, String id_account);
}
