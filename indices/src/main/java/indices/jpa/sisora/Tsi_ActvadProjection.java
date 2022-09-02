package indices.jpa.sisora;

import automation.factory.Logger;
import jpa.SisoraMessage;
import jpa.entity.sisora.Tsi_Actvad;
import jpa.metamodel.sisora.Tsi_Actvad_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 * <p>
 * Clase encargada de recoger los valores almacenados en la tabla TSI_ACTVAD
 */
public class Tsi_ActvadProjection{

    private static Logger LOG = Logger.getLogger(Tsi_ActvadProjection.class);

    private EntityManager entityManager;

    public Tsi_ActvadProjection(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Método que busca una proyección con los códigos de sector y códigos de actividad agrupados por códigos de actividad ordenados de mayor a menor
     *
     * @return lista de
     */
    public List<Object[]> getAllCoActvadGroupByCoSector() {
        List<Object[]> result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<Tsi_Actvad> root = criteriaQuery.from(Tsi_Actvad.class);
            criteriaQuery.multiselect(
                    root.get(Tsi_Actvad_.co_sector),
                    root.get(Tsi_Actvad_.co_actvad)
            );
            criteriaQuery.groupBy(
                    root.get(Tsi_Actvad_.co_sector),
                    root.get(Tsi_Actvad_.co_actvad)
            );
            criteriaQuery.orderBy(
                    criteriaBuilder.desc(
                            root.get(Tsi_Actvad_.co_sector)
                    )
            );
            result = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(SisoraMessage.NO_RESULT + ": " + e.getMessage());
            } else {
                LOG.error(SisoraMessage.GENERIC_ERROR + ": " + e.getMessage());
            }
        }
        return result;
    }
}
