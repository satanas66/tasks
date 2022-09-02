package mercado.candidato.jpa.gestford;

import automation.factory.Logger;
import jpa.GestfordMessage;
import jpa.entity.gestford.Tsf_Csord__Service__C;
import jpa.metamodel.gestford.Tsf_Csord__Service__C_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class Tsf_Csord__Service__CProjection {

    private static Logger LOG = Logger.getLogger(Tsf_Csord__Service__CProjection.class);

    private EntityManager entityManager;

    public Tsf_Csord__Service__CProjection(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    /**
     * Método que encuentra el id de un registro que coincida con el idServicio pasado por parámetro
     * @param idServicio
     * @return id
     */
    public String findIdByIdServicio(String idServicio){
        String result = null;
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Tsf_Csord__Service__C> root = criteriaQuery.from(Tsf_Csord__Service__C.class);
            criteriaQuery.select(root.get(Tsf_Csord__Service__C_.c_id_servicio__c));
            criteriaQuery.where(
                    criteriaBuilder.equal(root.get(Tsf_Csord__Service__C_.id), idServicio)
            );
            result = entityManager.createQuery(criteriaQuery).getSingleResult();
        }catch (Exception e){
            if(e instanceof NoResultException){
                LOG.info(GestfordMessage.NO_RESULT +idServicio+": "+e.getMessage());
            }else{
                LOG.error(GestfordMessage.GENERIC_ERROR +idServicio+": "+e.getMessage());
            }
        }
        return result;
    }
}
