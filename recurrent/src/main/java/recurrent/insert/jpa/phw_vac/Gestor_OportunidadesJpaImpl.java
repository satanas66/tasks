package recurrent.insert.jpa.phw_vac;

import automation.factory.Logger;
import jpa.entity.phw_vac.Gestor_Oportunidades;
import recurrent.insert.domain.business.GestorOportunidades;
import recurrent.insert.domain.mapper.GestorOportunidadesMapper;

import javax.persistence.EntityManager;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla GESTOR_OPORTUNIDADES
 */
public class Gestor_OportunidadesJpaImpl implements Gestor_OportunidadesJpa {

    private static Logger LOG = Logger.getLogger(Gestor_OportunidadesJpaImpl.class);

    private EntityManager entityManager;

    public Gestor_OportunidadesJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void insertGestor_Oportunidades(String line) {
        GestorOportunidadesMapper mapper = new GestorOportunidadesMapper();
        GestorOportunidades gestorOportunidades = mapper.getGestorOportunidadesFromString(line);
        Gestor_Oportunidades entity = mapper.getGestor_OportunidadesFromString(gestorOportunidades);
        try{
            entityManager.persist(entity);
        }catch (Exception e){
            LOG.error("Ha ocurrido un error al actualizar el registro para el cliente ficticio: "+entity.getCo_cliente());
        }
    }
}
