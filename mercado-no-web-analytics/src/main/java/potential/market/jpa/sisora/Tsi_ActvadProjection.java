package potential.market.jpa.sisora;

import automation.factory.Logger;
import jpa.SisoraMessage;
import jpa.entity.sisora.Tsi_Actvad;
import jpa.metamodel.sisora.Tsi_Actvad_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla TSI_ACTVAD
 */
public class Tsi_ActvadProjection {

    private static Logger LOG = Logger.getLogger(Tsi_ActvadProjection.class);

    private EntityManager entityManager;

    public Tsi_ActvadProjection(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Método que encuentra el código de un sector dado el código de una actividad
     * @param co_actvad
     * @return sector
     */
    public String findSectorCodeFromTsiActvad(Integer co_actvad) {
        String result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Tsi_Actvad> root = criteriaQuery.from(Tsi_Actvad.class);
            criteriaQuery.select(root.get(Tsi_Actvad_.co_sector));
            criteriaQuery.where(
                    criteriaBuilder.equal(root.get(Tsi_Actvad_.co_actvad), co_actvad)
            );
            result = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(SisoraMessage.NO_RESULT +co_actvad + ": " + e.getMessage());
            } else {
                LOG.error(SisoraMessage.GENERIC_ERROR + co_actvad + ": " + e.getMessage());
            }
        }
        return result;
    }
}
