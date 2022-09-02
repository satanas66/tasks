package mercado.candidato.jpa.sisora;

import automation.factory.Logger;
import jpa.SisoraMessage;
import jpa.entity.sisora.Tsi_Sector;
import jpa.metamodel.sisora.Tsi_Sector_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

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
     * Método que devuelve una lista de proyecciones de Tsi_Sector asociadas a un código de sector
     * Cada proyección contiene el sector, subsector1 y subsector2
     * @param co_sector
     * @return proyección
     */
    public List<Object[]> getProjectionsFromTsi_SectorToMuestraMadreByCoSector(String co_sector) {
        List<Object[]> result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<Tsi_Sector> sector = criteriaQuery.from(Tsi_Sector.class);
            criteriaQuery.multiselect(
                    sector.get(Tsi_Sector_.tx_sector),
                    sector.get(Tsi_Sector_.co_subSector),
                    sector.get(Tsi_Sector_.co_ssubSector));
            criteriaQuery.where(criteriaBuilder.equal(sector.get(Tsi_Sector_.co_sector), co_sector));
            result= entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(SisoraMessage.NO_RESULT+ co_sector + ": " + e.getMessage());
            } else {
                LOG.error(SisoraMessage.GENERIC_ERROR + co_sector + ": " + e.getMessage());
            }
        }
        return result;
    }
}
