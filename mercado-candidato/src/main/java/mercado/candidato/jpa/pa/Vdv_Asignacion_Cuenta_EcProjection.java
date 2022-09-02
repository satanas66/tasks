package mercado.candidato.jpa.pa;

import automation.factory.Logger;
import jpa.BipaMessage;
import jpa.entity.pa.Vdv_Asignacion_Cuenta_Ec;
import jpa.metamodel.pa.Vdv_Asignacion_Cuenta_Ec_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores generados en la vista VDV_ASIGNACION_CUENTA_EC
 */
public class Vdv_Asignacion_Cuenta_EcProjection {

    private static Logger LOG = Logger.getLogger(Vdv_Asignacion_Cuenta_EcProjection.class);

    private EntityManager entityManager;

    public Vdv_Asignacion_Cuenta_EcProjection(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Método que busca los códigos de vendedor y canal dado un código de cliente
     * @param clientCode
     * @return
     */
    public Object[] getCodVendedorAndCanalByClientCode(Integer clientCode){
        Object[] projection = null;
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<Vdv_Asignacion_Cuenta_Ec> root = criteriaQuery.from(Vdv_Asignacion_Cuenta_Ec.class);
            criteriaQuery.multiselect(
                    root.get(Vdv_Asignacion_Cuenta_Ec_.co_vend_cliente),
                    root.get(Vdv_Asignacion_Cuenta_Ec_.co_canal)
            );
            criteriaQuery.where(
                    criteriaBuilder.equal(
                            root.get(Vdv_Asignacion_Cuenta_Ec_.co_cliente), clientCode)
            );
            projection = entityManager.createQuery(criteriaQuery).getSingleResult();
        }catch (Exception e){
            if (e instanceof NoResultException) {
                LOG.info(BipaMessage.NO_RESULT + clientCode + ": " + e.getMessage());
            } else {
                LOG.error(BipaMessage.GENERIC_ERROR + clientCode + ": " + e.getMessage());
            }
        }
        return projection;
    }
}
