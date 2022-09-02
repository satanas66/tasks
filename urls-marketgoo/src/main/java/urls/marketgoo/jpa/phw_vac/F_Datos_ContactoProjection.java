package urls.marketgoo.jpa.phw_vac;

import automation.factory.Logger;
import jpa.BdcMesagge;
import jpa.entity.phw_vac.F_Datos_Contacto;
import jpa.metamodel.phw_vac.F_Datos_Contacto_;

import javax.persistence.EntityManager;
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
}
