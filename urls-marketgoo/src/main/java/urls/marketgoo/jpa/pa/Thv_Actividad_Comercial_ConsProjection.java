package urls.marketgoo.jpa.pa;

import automation.factory.Logger;
import jpa.BipaMessage;
import jpa.entity.pa.Thv_Actividad_Comercial_Cons;
import jpa.metamodel.pa.Thv_Actividad_Comercial_Cons_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla THV_ACTIVIDAD_COMERCIAL_CONS
 */
public class Thv_Actividad_Comercial_ConsProjection {

    private static Logger LOG = Logger.getLogger(Thv_Actividad_Comercial_ConsProjection.class);

    private static final String LLAMADA = "L";

    private EntityManager entityManager;

    public Thv_Actividad_Comercial_ConsProjection(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    /**
     * Metodo que obtiene la ultima anotacion de llamada dado un codigo de cliente
     *
     * @param clientCode
     * @return fecha de la ultima llamada
     */
    public Date findUltimaAnotacionLlamada(String clientCode) {
        Date projection = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Date> query = criteriaBuilder.createQuery(Date.class);
            Root<Thv_Actividad_Comercial_Cons> root = query.from(Thv_Actividad_Comercial_Cons.class);
            query.select(
                    criteriaBuilder.greatest(
                            root.get(Thv_Actividad_Comercial_Cons_.fe_hora_actividad)
                    )
            );
            query.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(Thv_Actividad_Comercial_Cons_.co_cliente), clientCode),
                            criteriaBuilder.equal(root.get(Thv_Actividad_Comercial_Cons_.tipo_actividad), LLAMADA),
                            criteriaBuilder.greaterThanOrEqualTo(root.get(Thv_Actividad_Comercial_Cons_.in_util), 1)
                    )
            );
            query.orderBy(criteriaBuilder.desc(root.get(Thv_Actividad_Comercial_Cons_.fe_hora_actividad)));
            projection = entityManager.createQuery(query).getSingleResult();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(BipaMessage.NO_RESULT + clientCode + ": " + e.getMessage());
            } else {
                LOG.error(BipaMessage.GENERIC_ERROR + clientCode + ": " + e.getMessage());
            }
        }
        return projection;
    }

}
