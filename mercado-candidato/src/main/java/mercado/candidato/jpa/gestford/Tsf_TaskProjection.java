package mercado.candidato.jpa.gestford;

import automation.factory.Logger;
import jpa.GestfordMessage;
import jpa.entity.gestford.Tsf_Task;
import jpa.metamodel.gestford.Tsf_Task_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla TSF_TASK *
 */
public class Tsf_TaskProjection {

    private static Logger LOG = Logger.getLogger(Tsf_TaskProjection.class);

    private EntityManager entityManager;

    public Tsf_TaskProjection(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    /**
     * Metodo que obtiene la fecha de la ultima modificacion de una tarea dado un accountId
     * @param accountId
     * @return fecha de la ultima modificacion de la tarea
     */
    public Date findLastModificationETarea(String accountId){
        Date result = null;
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Date> criteriaQuery = criteriaBuilder.createQuery(Date.class);
            Root<Tsf_Task> root = criteriaQuery.from(Tsf_Task.class);
            criteriaQuery.select(
                    criteriaBuilder.greatest(
                            root.get(Tsf_Task_.lastmodifieddate)
                    )
            );
            criteriaQuery.where(
                    criteriaBuilder.equal(
                            root.get(Tsf_Task_.accountid), accountId)
            );
            criteriaQuery.orderBy(
                    criteriaBuilder.desc(
                            root.get(Tsf_Task_.lastmodifieddate)));
            result = entityManager.createQuery(criteriaQuery).getSingleResult();
        }catch(Exception e){
            if(e instanceof NoResultException){
                LOG.info(GestfordMessage.NO_RESULT +accountId+": "+e.getMessage());
            }else{
                LOG.error(GestfordMessage.GENERIC_ERROR +accountId+": "+e.getMessage());
            }
        }
        return result;
    }
}
