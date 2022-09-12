package mercado.potencial.jpa.pa;

import automation.factory.Logger;
import jpa.BipaMessage;
import jpa.entity.pa.Tdv_Oportunidades_Por_Cliente;
import jpa.metamodel.pa.Tdv_Oportunidades_Por_Cliente_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla TDV_OPORTUNIDADES_POR_CLIENTE *
 */
public class Tdv_Oportunidades_Por_ClienteProjection {

    private static Logger LOG = Logger.getLogger(Tdv_Oportunidades_Por_ClienteProjection.class);

    private EntityManager entityManager;

    public Tdv_Oportunidades_Por_ClienteProjection(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    /**
     * Metodo encargado de encontrar la fecha ganada y la fecha de creación de una oportunidad
     * @param clientCode
     * @return proyección
     */
    public Object[] findFeGanadaAndFeCreacionFromTdv_Oportunidades_Por_ClienteByClientCode(Integer clientCode) {
        Object[] projection = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
            Root<Tdv_Oportunidades_Por_Cliente> root = query.from(Tdv_Oportunidades_Por_Cliente.class);
            query.multiselect(
                    root.get(Tdv_Oportunidades_Por_Cliente_.fe_ganada_dmy),
                    root.get(Tdv_Oportunidades_Por_Cliente_.fe_creacion_dmy)
            );
            query.where(
                    criteriaBuilder.equal(
                            root.get(Tdv_Oportunidades_Por_Cliente_.co_cliente), clientCode)
            );
            query.orderBy(
                    criteriaBuilder.desc(
                            root.get(Tdv_Oportunidades_Por_Cliente_.ct_systemmodstamp))
            );
            projection = entityManager.createQuery(query).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(BipaMessage.NO_RESULT + clientCode + ": " + e.getMessage());
            } else {
                LOG.error(BipaMessage.GENERIC_ERROR + clientCode + ": " + e.getMessage());
            }
        }
        return projection;
    }
}
