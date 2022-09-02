package indices.jpa.phw_vac;

import automation.factory.Logger;
import jpa.BdcMesagge;
import jpa.entity.phw_vac.F_Datos_Contacto;
import jpa.metamodel.phw_vac.F_Datos_Contacto_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla F_DATOS_CONTACTO
 */
public class F_Datos_ContactoProjection{

    private static Logger LOG = Logger.getLogger(F_Datos_ContactoProjection.class);

    private EntityManager entityManager;

    public F_Datos_ContactoProjection(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Método que obtiene una proyección con los campos: nombre de cliente, nombre comercial, actividad, dirección
     * localidad, provincia, código postal, comunidad autonoma, teléfono1, email, nif, código de empresa,
     * código de la actividad principal y rating crediticio
     * @param clientCode
     * @return proyección
     */
    public Object[] findActivityCodeByclientCode(Integer clientCode) {
        Object[] result = null;
        try {
            LOG.info(BdcMesagge.SEARCH_CLIENT + clientCode);
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<F_Datos_Contacto> root = criteriaQuery.from(F_Datos_Contacto.class);
            criteriaQuery.multiselect(
                    root.get(F_Datos_Contacto_.co_cliente),
                    root.get(F_Datos_Contacto_.co_actvad_pral)
            );
            criteriaQuery.where(
                    criteriaBuilder.equal(
                            root.get(F_Datos_Contacto_.co_cliente), clientCode)
            );
            result = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(BdcMesagge.NO_RESULT + clientCode + ": " + e.getMessage());
            } else {
                LOG.error(BdcMesagge.GENERIC_ERROR + clientCode + ": " + e.getMessage());
            }
        }
        return result;
    }
}
