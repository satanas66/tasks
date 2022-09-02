package mercado.candidato.jpa.gestcli_sisora;

import automation.factory.Logger;
import jpa.GestcliSisoraMessage;
import jpa.entity.gestcli_sisora.Tipcl_Camp_Respuesta;
import jpa.metamodel.gestcli_sisora.Tipcl_Camp_Respuesta_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.Date;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla TIPCL_CAMP_RESPUESTA
 */
public class Tipcl_Camp_RespuestaProjection {

    private static Logger LOG = Logger.getLogger(Tipcl_Camp_RespuestaProjection.class);

    private EntityManager entityManager;

    public Tipcl_Camp_RespuestaProjection(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Método que encuentra todas las modificaciones rechazadas por el proceso scrapping
     *
     * @return lista de códigos de clientes y fechas creación y modificación de registros
     * SELECT co_cliente, fe_insercion, fe_tratado
     * FROM tipcl_camp_respuesta t1 WHERE estado_cruce = '12'
     * AND co_cliente > 0
     * AND t1.fe_movimiento IN ( SELECT MAX(fe_movimiento)
     * FROM tipcl_camp_respuesta t4 WHERE t1.account_id = t4.account_id);
     */
    public List<Object[]> getRejectedModificationsByScrappingProcess() {
        List<Object[]> result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<Tipcl_Camp_Respuesta> root1 = criteriaQuery.from(Tipcl_Camp_Respuesta.class);
            criteriaQuery.multiselect(
                    root1.get(Tipcl_Camp_Respuesta_.co_cliente),
                    root1.get(Tipcl_Camp_Respuesta_.fe_insercion),
                    root1.get(Tipcl_Camp_Respuesta_.fe_tratado)
            ).distinct(true);
            Subquery<Date> subquery = criteriaQuery.subquery(Date.class);
            Root root2 = criteriaQuery.from(Tipcl_Camp_Respuesta.class);
            subquery.select(criteriaBuilder.greatest(root2.get(Tipcl_Camp_Respuesta_.fe_movimiento)));
            subquery.where(criteriaBuilder.equal(root1.get(Tipcl_Camp_Respuesta_.account_id), root2.get(Tipcl_Camp_Respuesta_.account_id)));
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.greaterThan(root1.get(Tipcl_Camp_Respuesta_.co_cliente), 0),
                            criteriaBuilder.equal(root1.get(Tipcl_Camp_Respuesta_.estado_cruce), "12"),
                            criteriaBuilder.in(root1.get(Tipcl_Camp_Respuesta_.fe_movimiento)).value(subquery))
            );
            criteriaQuery.orderBy(
                    criteriaBuilder.desc(root1.get(Tipcl_Camp_Respuesta_.fe_movimiento))
            );
            result = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(GestcliSisoraMessage.NO_RESULT + ": " + e.getMessage());
            } else {
                LOG.error(GestcliSisoraMessage.GENERIC_ERROR + ": " + e.getMessage());
            }
        }
        return result;
    }
}
