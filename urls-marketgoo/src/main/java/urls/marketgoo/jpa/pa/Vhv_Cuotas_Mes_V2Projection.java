package urls.marketgoo.jpa.pa;

import automation.factory.Logger;
import automation.factory.Utils;
import jpa.BipaMessage;
import jpa.entity.pa.Vhv_Cuotas_Mes_V2;
import jpa.metamodel.pa.Vhv_Cuotas_Mes_V2_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 * <p>
 * Clase encargada de recoger los valores generados en la vista VHV_CUOTAS_MES_V2 *
 */
public class Vhv_Cuotas_Mes_V2Projection {

    private static Logger LOG = Logger.getLogger(Vhv_Cuotas_Mes_V2Projection.class);

    private EntityManager entityManager;

    public Vhv_Cuotas_Mes_V2Projection(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Método que busca un cliente potencial que no tenga web
     *
     * @param clientCode, parámetro a evaluar
     * @return clientCode
     */
    public String findPotentialClient(String clientCode) {
        String result = null;
        try {
            List<Integer> months = Utils.getCurrentMonthsAndPreviousMonth();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> query = criteriaBuilder.createQuery(String.class);
            Root<Vhv_Cuotas_Mes_V2> root = query.from(Vhv_Cuotas_Mes_V2.class);
            /**
             * Primero: busco si el cliente está vivo para el mes actual
             */
            query.select(root.get(Vhv_Cuotas_Mes_V2_.id_producto));
            query.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.co_cliente), clientCode),
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_mes), months.get(0))
                    )
            );
            List<String> idsProducto = entityManager.createQuery(query).getResultList();
            /**
             * Si el tamaño de idsProducto es 0 lo tengo que buscar en el mes anterior
             */
            if (idsProducto.size() == 0) {
                /**
                 * Segundo: busco si el cliente está vivo para el mes anterior
                 */
                query.select(root.get(Vhv_Cuotas_Mes_V2_.id_producto));
                query.where(
                        criteriaBuilder.and(
                                criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.co_cliente), clientCode),
                                criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_mes), months.get(1))
                        )
                );
                idsProducto = entityManager.createQuery(query).getResultList();
                /**
                 * Si el tamaño de idsProducto es 0 quiere decir que es un cliente potencial (no vivo) y devuelvo el clientCode
                 */
                if (idsProducto.size() == 0) {
                    result = clientCode;
                } else {
                    /**
                     * En este punto quiere decir que el cliente está vivo. Se verifica que no contenga WEB
                     */
                    result = idsProducto.contains("WEM") ? null : clientCode;
                }
            } else {
                /**
                 * En este punto quiere decir que el cliente está vivo. Se verifica que no contenga WEB
                 */
                result = idsProducto.contains("WEM") ? null : clientCode;
            }
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(BipaMessage.NO_RESULT + clientCode + ": " + e.getMessage());
            } else {
                LOG.error(BipaMessage.GENERIC_ERROR + clientCode + ": " + e.getMessage());
            }
        }
        return result;
    }
}
