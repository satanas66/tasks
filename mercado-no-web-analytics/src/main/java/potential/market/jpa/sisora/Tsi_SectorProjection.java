package potential.market.jpa.sisora;

import automation.factory.Logger;
import jpa.SisoraMessage;
import jpa.entity.sisora.Tsi_Sector;
import jpa.metamodel.sisora.Tsi_Sector_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla TSI_SECTOR
 */
public class Tsi_SectorProjection {

    private static Logger LOG = Logger.getLogger(Tsi_SectorProjection.class);

    private EntityManager entityManager;

    public Tsi_SectorProjection(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    /**
     * Método que devuelve el texto principal de un sector dado su código de sector
     * @param sectorCode
     * @return
     */
    public String getSectorTextBySectorCode(String sectorCode) {
        String result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Tsi_Sector> sector = criteriaQuery.from(Tsi_Sector.class);
            criteriaQuery.select(sector.get(Tsi_Sector_.tx_sector));
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(sector.get(Tsi_Sector_.co_sector), sectorCode),
                            criteriaBuilder.equal(sector.get(Tsi_Sector_.co_subSector), "000"),
                            criteriaBuilder.equal(sector.get(Tsi_Sector_.co_ssubSector), "000")
                    )
            );
            result = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(SisoraMessage.NO_RESULT+ sectorCode + ": " + e.getMessage());
            } else {
                LOG.error(SisoraMessage.GENERIC_ERROR + sectorCode + ": " + e.getMessage());
            }
        }
        return result;
    }
}
