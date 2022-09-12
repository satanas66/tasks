package mercado.potencial.jpa.pa;

import automation.factory.Logger;
import automation.factory.Utils;
import jpa.BipaMessage;
import jpa.entity.pa.Vhv_Cuotas_Mes_V2;
import jpa.metamodel.pa.Vhv_Cuotas_Mes_V2_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 * <p>
 * Clase encargada de recoger los valores generados en la vista VHV_CUOTAS_MES_V2 *
 */
public class Vhv_Cuotas_Mes_V2Projection {

    private static Logger LOG = Logger.getLogger(Vhv_Cuotas_Mes_V2Projection.class);

    private EntityManager entityManager;

    public Vhv_Cuotas_Mes_V2Projection(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Método que busca un id_producto según su código de cliente
     * (Internamente se verifica el resultado para el mes actual y el anterior)
     *
     * @param clientCode
     * @return identificador del producto
     */
    public String findIdProductByClientCode(String clientCode) {
        String result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Vhv_Cuotas_Mes_V2> root = criteriaQuery.from(Vhv_Cuotas_Mes_V2.class);
            criteriaQuery.select(
                    root.get(Vhv_Cuotas_Mes_V2_.id_producto)
            );
            List<Integer> months = Utils.getCurrentMonthsAndPreviousMonth();
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.co_cliente), clientCode),
                            criteriaBuilder.or(
                                    criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_mes), months.get(0)),
                                    criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_mes), months.get(1))
                            )
                    )
            );
            result = entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(BipaMessage.NO_RESULT + clientCode + ": " + e.getMessage());
            } else {
                LOG.error(BipaMessage.GENERIC_ERROR + clientCode + ": " + e.getMessage());
            }
        }
        return result;
    }
}
