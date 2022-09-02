package mercado.candidato.jpa.pa;

import automation.factory.Logger;
import jpa.BipaMessage;
import jpa.entity.pa.Tdp_Yext_Localizacion;
import jpa.entity.pa.Tdp_Yext_Publicacion;
import jpa.metamodel.pa.Tdp_Yext_Localizacion_;
import jpa.metamodel.pa.Tdp_Yext_Publicacion_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 * <p>
 * Clase encargada de recoger valores generados en la tabla tdp_yext_publicacion
 */
public class Tdp_Yext_PublicacionProjection {

    private static Logger LOG = Logger.getLogger(Vhv_Cuotas_Mes_V2Projection.class);

    private EntityManager entityManager;

    public Tdp_Yext_PublicacionProjection(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<String> findEstadosFacebookAndGMB(String co_servicio) {
        List<String> result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Tdp_Yext_Publicacion> root1 = criteriaQuery.from(Tdp_Yext_Publicacion.class);
            Root<Tdp_Yext_Localizacion> root2 = criteriaQuery.from(Tdp_Yext_Localizacion.class);

            criteriaQuery.select(root1.get(Tdp_Yext_Publicacion_.co_estado_pub));

            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root2.get(Tdp_Yext_Localizacion_.co_servicio), co_servicio),
                            criteriaBuilder.equal(root1.get(Tdp_Yext_Publicacion_.id_localizacion),
                                                  root2.get(Tdp_Yext_Localizacion_.id_localizacion)),
                            criteriaBuilder.or(
                                    criteriaBuilder.equal(root1.get(Tdp_Yext_Publicacion_.co_publisher), "FACEBOOK"),
                                    criteriaBuilder.equal(root1.get(Tdp_Yext_Publicacion_.co_publisher), "GOOGLEMYBUSINESS")
                            )
                    )
            );

            result = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(BipaMessage.NO_RESULT + co_servicio + ": " + e.getMessage());
            } else {
                LOG.error(BipaMessage.GENERIC_ERROR + co_servicio + ": " + e.getMessage());
            }
        }
        return result;
    }
}
