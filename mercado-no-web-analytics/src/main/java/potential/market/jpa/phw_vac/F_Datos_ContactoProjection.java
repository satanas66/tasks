package potential.market.jpa.phw_vac;

import automation.factory.Logger;
import jpa.BdcMesagge;
import jpa.entity.phw_vac.F_Datos_Contacto;
import jpa.metamodel.phw_vac.F_Datos_Contacto_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla F_DATOS_CONTACTO
 */
public class F_Datos_ContactoProjection {

    private static Logger LOG = Logger.getLogger(F_Datos_ContactoProjection.class);

    private EntityManager entityManager;

    public F_Datos_ContactoProjection(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    /**
     * Método que obtiene todos los códigos de clientes de la tabla F_DATOS_CONTACTO
     * @return
     */
    public List<Integer> findAllClientCodes() {
        List<Integer> clientCodes = null;
        try {
            LOG.info("Buscando todos los códigos de clientes de la base de datos BDC..");
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
            Root<F_Datos_Contacto> root = criteriaQuery.from(F_Datos_Contacto.class);
            criteriaQuery.select(
                    root.get(F_Datos_Contacto_.co_cliente)
            );
            clientCodes = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            LOG.error(BdcMesagge.GENERIC_ERROR + e.getMessage());
        }
        return clientCodes;
    }

    /**
     * Método que obtiene una proyección con los campos:
     * nc_nif
     * cc_nom_empre
     * no_comer
     * tx_actvad
     * de_prov
     * tx_loca_apa
     * direccion
     * co_post_cto
     * tx_ccaa
     * telefono1
     * telefono2
     * telefono3
     * telefono4
     * telefono5
     * co_actvad_pral
     * @param clientCode
     * @return proyección
     */
    public Object[] getProjectionFromF_Datos_ContactoToMuestraMadreByClientCode(Integer clientCode) {
        Object[] projection = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<F_Datos_Contacto> root = criteriaQuery.from(F_Datos_Contacto.class);
            criteriaQuery.multiselect(
                    root.get(F_Datos_Contacto_.nc_nif),
                    root.get(F_Datos_Contacto_.cc_nom_empre),
                    root.get(F_Datos_Contacto_.no_comer),
                    root.get(F_Datos_Contacto_.tx_actvad),
                    root.get(F_Datos_Contacto_.de_prov),
                    root.get(F_Datos_Contacto_.tx_loca_apa),
                    root.get(F_Datos_Contacto_.direccion),
                    root.get(F_Datos_Contacto_.co_post_cto),
                    root.get(F_Datos_Contacto_.tx_ccaa),
                    root.get(F_Datos_Contacto_.telefono1),
                    root.get(F_Datos_Contacto_.telefono2),
                    root.get(F_Datos_Contacto_.telefono3),
                    root.get(F_Datos_Contacto_.telefono4),
                    root.get(F_Datos_Contacto_.telefono5),
                    root.get(F_Datos_Contacto_.co_actvad_pral)
            );
            criteriaQuery.where(
                    criteriaBuilder.equal(
                            root.get(F_Datos_Contacto_.co_cliente), clientCode)
            );
            projection = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(BdcMesagge.NO_RESULT + clientCode + ": " + e.getMessage());
            } else {
                LOG.error(BdcMesagge.GENERIC_ERROR + clientCode + ": " + e.getMessage());
            }
        }
        return projection;
    }
}
