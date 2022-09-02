package mercado.candidato.jpa.phw_vac;

import automation.factory.Logger;
import jpa.BdcMesagge;
import jpa.entity.phw_vac.Estacionalidad_Actv;
import jpa.metamodel.phw_vac.Estacionalidad_Actv_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla ESTACIONALIDAD_ACTV
 */
public class Estacionalidad_ActvProjection {

    private static Logger LOG = Logger.getLogger(Estacionalidad_ActvProjection.class);

    private EntityManager entityManager;

    public Estacionalidad_ActvProjection(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    /**
     * Método que encuentra la estacionalidad de una actividad dado un código de actividad
     * @param co_actividad
     * @return
     */
    public String getEstacionalidadByClientCode(Integer co_actividad){
        String result = null;
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Estacionalidad_Actv> root = criteriaQuery.from(Estacionalidad_Actv.class);
            criteriaQuery.select(root.get(Estacionalidad_Actv_.estacionalidad));
            criteriaQuery.where(criteriaBuilder.equal(root.get(Estacionalidad_Actv_.co_actividad), co_actividad));
            result = entityManager.createQuery(criteriaQuery).getSingleResult();
        }catch(Exception e){
            if (e instanceof NoResultException) {
                LOG.info(BdcMesagge.NO_RESULT +co_actividad + ": " + e.getMessage());
            } else {
                LOG.error(BdcMesagge.GENERIC_ERROR + co_actividad + ": " + e.getMessage());
            }
        }
        return result;
    }
}
