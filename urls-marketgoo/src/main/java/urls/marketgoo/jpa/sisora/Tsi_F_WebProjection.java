package urls.marketgoo.jpa.sisora;

import automation.factory.Logger;
import jpa.BipaMessage;
import jpa.SisoraMessage;
import jpa.entity.sisora.Tsi_F_Web;
import jpa.metamodel.sisora.Tsi_F_Web_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla TSI_F_WEB
 */
public class Tsi_F_WebProjection {

    private static Logger LOG = Logger.getLogger(Tsi_F_WebProjection.class);

    private EntityManager entityManager;

    public Tsi_F_WebProjection(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    /**
     * Metodo que devuelve una URL dado un código de cliente
     * @param clientCode
     * @return url
     */
    public String findProjectionEWeb(Integer clientCode) {
        String result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Tsi_F_Web> root = criteriaQuery.from(Tsi_F_Web.class);
            criteriaQuery.select(
                    root.get(Tsi_F_Web_.di_url)
            );
            criteriaQuery.where(
                    criteriaBuilder.equal(
                            root.get(Tsi_F_Web_.co_cliente), clientCode)
            );
            result = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(SisoraMessage.NO_RESULT + clientCode + ": " + e.getMessage());
            } else {
                LOG.error(SisoraMessage.GENERIC_ERROR + clientCode + ": " + e.getMessage());
            }
        }
        return result;
    }

    /**
     * Método que devuelve el origen de una URL dado un código de cliente
     * @param co_cliente
     * @return
     */
    public String getOrigenUrlByCoCliente(Integer co_cliente){
        String result = null;
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Tsi_F_Web> root = criteriaQuery.from(Tsi_F_Web.class);
            criteriaQuery.select(root.get(Tsi_F_Web_.co_origen));
            criteriaQuery.where(
                    criteriaBuilder.equal(root.get(Tsi_F_Web_.co_cliente), co_cliente)
            );
            result = entityManager.createQuery(criteriaQuery).getSingleResult();

        }catch(Exception e){
            if (e instanceof NoResultException) {
                LOG.info(SisoraMessage.NO_RESULT +co_cliente + ": " + e.getMessage());
            } else {
                LOG.error(SisoraMessage.GENERIC_ERROR + co_cliente + ": " + e.getMessage());
            }
        }
        return result;
    }

    /**
     * Metodo que encuentra una URL dado un codigo de cliente
     *
     * @param clientCode
     * @return url
     */
    public String findDiUrl(String clientCode) {
        String diUrl = null;
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> query = builder.createQuery(String.class);
            Root<Tsi_F_Web> root = query.from(Tsi_F_Web.class);
            query.select(root.get(Tsi_F_Web_.di_url));
            query.where(builder.equal(root.get(Tsi_F_Web_.co_cliente), Long.parseLong(clientCode)));
            diUrl = entityManager.createQuery(query).getSingleResult();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(BipaMessage.NO_RESULT + clientCode + ": " + e.getMessage());
            } else {
                LOG.error(BipaMessage.GENERIC_ERROR + clientCode + ": " + e.getMessage());
            }
        }
        return diUrl;
    }
}
