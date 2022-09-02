package muestra.madre.jpa.phw_vac;

import automation.factory.Logger;
import jpa.BdcMesagge;
import jpa.entity.phw_vac.Gestor_Oportunidades;
import jpa.metamodel.phw_vac.Gestor_Oportunidades_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla GESTOR_OPORTUNIDADES *
 */
public class Gestor_OportunidadesProjection {

    private static Logger LOG = Logger.getLogger(Gestor_OportunidadesProjection.class);

    private EntityManager entityManager;

    public Gestor_OportunidadesProjection(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    /**
     * Metodo que genera una proyeccion con el estado y fecha de baja de un registro dado un codigo de cliente
     * @param clientCode
     * @return proyeccion
     */
    public Object[] findProjectionEGestorOportunidades(Integer clientCode){
        Object[] projection = null;
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<Gestor_Oportunidades> root = criteriaQuery.from(Gestor_Oportunidades.class);
            criteriaQuery.multiselect(
                    root.get(Gestor_Oportunidades_.estado_reg),
                    root.get(Gestor_Oportunidades_.fe_baja_reg)
            );
            criteriaQuery.where(
                    criteriaBuilder.equal(
                            root.get(Gestor_Oportunidades_.co_cliente), clientCode)
            );
            criteriaQuery.orderBy(
                    criteriaBuilder.desc(root.get(Gestor_Oportunidades_.fe_modi_reg))
            );
            projection = entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
        }catch(Exception e){
            if(e instanceof NoResultException){
                LOG.info(BdcMesagge.NO_RESULT +clientCode+": "+e.getMessage());
            }else{
                LOG.error(BdcMesagge.GENERIC_ERROR +clientCode+": "+e.getMessage());
            }
        }
        return projection;
    }
}
