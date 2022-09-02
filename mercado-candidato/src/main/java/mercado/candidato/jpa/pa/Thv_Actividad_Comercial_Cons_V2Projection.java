package mercado.candidato.jpa.pa;

import automation.factory.Logger;
import automation.factory.Utils;
import jpa.BipaMessage;
import jpa.entity.pa.Thv_Actividad_Comercial_Cons_V2;
import jpa.metamodel.pa.Thv_Actividad_Comercial_Cons_V2_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores de la tabla THV_ACTIVIDAD_COMERCIAL_CONS_V2
 */
public class Thv_Actividad_Comercial_Cons_V2Projection {

    private static Logger LOG = Logger.getLogger(Thv_Actividad_Comercial_Cons_V2.class);

    private EntityManager entityManager;

    public Thv_Actividad_Comercial_Cons_V2Projection(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Método que genera una proyección de la actividad comercial (última llamada) dado un código de cliente
     *
     * @param clientCode
     * @return
     */
    public Object[] getProjectionThv_Actividad_Comercial_Cons_V2ByClientCode(Integer clientCode) {
        Object[] projection = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<Thv_Actividad_Comercial_Cons_V2> root = criteriaQuery.from(Thv_Actividad_Comercial_Cons_V2.class);
            criteriaQuery.multiselect(
                    root.get(Thv_Actividad_Comercial_Cons_V2_.fe_hora_actividad),
                    root.get(Thv_Actividad_Comercial_Cons_V2_.subtipo_actividad),
                    root.get(Thv_Actividad_Comercial_Cons_V2_.origen_llamada),
                    root.get(Thv_Actividad_Comercial_Cons_V2_.id_usuario),
                    root.get(Thv_Actividad_Comercial_Cons_V2_.in_util),
                    root.get(Thv_Actividad_Comercial_Cons_V2_.duracion_llamada)
            );
            criteriaQuery.where(criteriaBuilder.equal(root.get(Thv_Actividad_Comercial_Cons_V2_.co_cliente), clientCode));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Thv_Actividad_Comercial_Cons_V2_.ct_systemmodstamp_orig)));
            projection = entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(BipaMessage.NO_RESULT + clientCode + ": " + e.getMessage());
            } else {
                LOG.error(BipaMessage.GENERIC_ERROR + clientCode + ": " + e.getMessage());
            }
        }
        return projection;
    }

    /**
     * Método que devuelve el idmes dado un código de cliente
     * Se devuleve el idmes siempre y cuando se haya llamado al cliente en los dos últimos meses
     * @return
     */
    public Integer getLastCallIntoLastTwoMonths(Integer clientCode){
        Integer result = null;
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
            Root<Thv_Actividad_Comercial_Cons_V2> root = criteriaQuery.from(Thv_Actividad_Comercial_Cons_V2.class);
            List<Integer> months = Utils.getCurrentMonthsAndPreviousMonth();
            criteriaQuery.select(root.get(Thv_Actividad_Comercial_Cons_V2_.id_mes));
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(Thv_Actividad_Comercial_Cons_V2_.co_cliente), clientCode),
                            criteriaBuilder.or(
                                    criteriaBuilder.equal(root.get(Thv_Actividad_Comercial_Cons_V2_.id_mes), months.get(0)),
                                    criteriaBuilder.equal(root.get(Thv_Actividad_Comercial_Cons_V2_.id_mes), months.get(1))
                            )
                    )
            );
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Thv_Actividad_Comercial_Cons_V2_.id_mes)));
            result = entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
        }catch (Exception e){
            if (e instanceof NoResultException) {
                LOG.info(BipaMessage.NO_RESULT + clientCode + ": " + e.getMessage());
            } else {
                LOG.error(BipaMessage.GENERIC_ERROR + clientCode + ": " + e.getMessage());
            }
        }
        return result;
    }
}
