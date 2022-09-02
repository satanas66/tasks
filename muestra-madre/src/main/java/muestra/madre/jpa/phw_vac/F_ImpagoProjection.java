package muestra.madre.jpa.phw_vac;

import automation.factory.Logger;
import jpa.BdcMesagge;
import jpa.entity.phw_vac.F_Impago;
import jpa.metamodel.phw_vac.F_Impago_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla F_IMPAGO *
 */
public class F_ImpagoProjection {

    private static Logger LOG = Logger.getLogger(F_ImpagoProjection.class);

    private static final String ESTADO_IMPAGO = "N";

    private EntityManager entityManager;

    public F_ImpagoProjection(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    /**
     * Método que devuelve el estado de pago de un cliente
     * @param clientCode
     * @return estado de pago
     */
    public String findImpagoValue(Integer clientCode){
        String result = null;
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<F_Impago> root = criteriaQuery.from(F_Impago.class);
            criteriaQuery.select(
                    root.get(F_Impago_.in_impago)
            );
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(
                                    root.get(F_Impago_.co_cliente), clientCode),
                            criteriaBuilder.equal(
                                    root.get(F_Impago_.in_impago), ESTADO_IMPAGO)
                    )
            );
            result = entityManager.createQuery(criteriaQuery).getSingleResult();
        }catch(Exception e){
            if(e instanceof NoResultException){
                LOG.info(BdcMesagge.NO_RESULT +clientCode+": "+e.getMessage());
            }else{
                LOG.error(BdcMesagge.GENERIC_ERROR+clientCode+": "+e.getMessage());
            }
        }
        return result;
    }
}
