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
import java.util.List;

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
    public Integer findActivityCodeByclientCode(Integer clientCode) {
        Integer result = null;
        try {
            LOG.info(BdcMesagge.SEARCH_CLIENT + clientCode);
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
            Root<F_Datos_Contacto> root = criteriaQuery.from(F_Datos_Contacto.class);
            criteriaQuery.select(
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

    /**
     * Método que obtiene una proyección con los campos: nombre de cliente, nombre comercial, actividad, dirección
     * localidad, provincia, código postal, comunidad autonoma, teléfono1, email, nif, código de empresa,
     * código de la actividad principal y rating crediticio
     * @param clientCode
     * @return proyección
     */
    public String getProjectionFromF_Datos_ContactoToMuestraMadreByClientCode(Integer clientCode) {
         String result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<F_Datos_Contacto> root = criteriaQuery.from(F_Datos_Contacto.class);
            criteriaQuery.multiselect(
                    root.get(F_Datos_Contacto_.tx_loca_apa),
                    root.get(F_Datos_Contacto_.de_prov),
                    root.get(F_Datos_Contacto_.co_ccaa),
                    root.get(F_Datos_Contacto_.tx_ccaa),
                    root.get(F_Datos_Contacto_.co_actvad_pral),
                    root.get(F_Datos_Contacto_.tx_actvad)

            );
            criteriaQuery.where(
                    criteriaBuilder.equal(
                            root.get(F_Datos_Contacto_.co_cliente), clientCode)
            );
            Object[] projection = entityManager.createQuery(criteriaQuery).getSingleResult();
            result = getValuesFromProjection(projection);
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(BdcMesagge.NO_RESULT + clientCode + ": " + e.getMessage());
            } else {
                LOG.error(BdcMesagge.GENERIC_ERROR + clientCode + ": " + e.getMessage());
            }
        }
        return result;
    }

    private String getValuesFromProjection(Object[] projection){
        String result = (projection[0] != null)?String.valueOf(projection[0]):"";
        for(int i=1; i<projection.length; i++){
            if(projection[i] != null){
                result = result+";"+projection[i];
            }else{
                result = result+";";
            }
        }
        return result;
    }

    /**
     * Método que obtiene una proyección con los campos: nombre de cliente, nombre comercial, actividad, dirección
     * localidad, provincia, código postal, comunidad autonoma, teléfono1, email, nif, código de empresa,
     * código de la actividad principal y rating crediticio
     * @param clientCodes
     * @return proyección
     */
    public String getProjectionFromF_Datos_ContactoToMuestraMadreByListClientCode(List<Integer> clientCodes) {
        String result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<F_Datos_Contacto> root = criteriaQuery.from(F_Datos_Contacto.class);
            criteriaQuery.multiselect(
                    root.get(F_Datos_Contacto_.tx_loca_apa),
                    root.get(F_Datos_Contacto_.de_prov),
                    root.get(F_Datos_Contacto_.co_ccaa),
                    root.get(F_Datos_Contacto_.tx_ccaa),
                    root.get(F_Datos_Contacto_.co_actvad_pral),
                    root.get(F_Datos_Contacto_.tx_actvad)

            );
            criteriaQuery.where(
                    criteriaBuilder.in(root.get(F_Datos_Contacto_.co_cliente)).in(clientCodes)
            );
            List<Object[]> projection = entityManager.createQuery(criteriaQuery).getResultList();
            result = projection.get(0).toString();/**/
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(BdcMesagge.NO_RESULT +": " + e.getMessage());
            } else {
                LOG.error(BdcMesagge.GENERIC_ERROR +": " + e.getMessage());
            }
        }
        return result;
    }
}
