package mercado.candidato.jpa.pa;

import automation.factory.Logger;
import jpa.BipaMessage;
import jpa.entity.pa.Tdv_Rol_Usuario;
import jpa.entity.pa.Tdv_Usuario;
import jpa.metamodel.pa.Tdv_Rol_Usuario_;
import jpa.metamodel.pa.Tdv_Usuario_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores generados en las tablas TDV_USUARIO y TDV_ROL_USUARIO *
 */
public class Tdv_UsuarioProjection {

    private static Logger LOG = Logger.getLogger(Tdv_UsuarioProjection.class);

    private EntityManager entityManager;

    public Tdv_UsuarioProjection(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    /**
     * Método encargado de obtener la información del empleado/vendedor de los productos empresariales
     *
     * @param co_vendedor, código del vendedor
     * @return información del vendedor
     */
    public Object[] findEmployeeInformation(String co_vendedor){
        Object[] result = null;
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<Tdv_Usuario> root1 = criteriaQuery.from(Tdv_Usuario.class);
            Root<Tdv_Rol_Usuario> root2 = criteriaQuery.from(Tdv_Rol_Usuario.class);

            criteriaQuery.multiselect(
                    root1.get(Tdv_Usuario_.no_usuario),
                    root2.get(Tdv_Rol_Usuario_.no_rol_usuario)
            );

            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root1.get(Tdv_Usuario_.co_vendedor), co_vendedor),
                            criteriaBuilder.equal(root1.get(Tdv_Usuario_.id_rol_usuario), root2.get(Tdv_Rol_Usuario_.id_rol_usuario))
                    )
            );

            criteriaQuery.orderBy(
                    criteriaBuilder.desc(
                            root1.get(Tdv_Usuario_.ct_systemmodstamp))
            );
            result = entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
        }catch (Exception e){
            if (e instanceof NoResultException) {
                LOG.info(BipaMessage.NO_RESULT + co_vendedor + ": " + e.getMessage());
            } else {
                LOG.error(BipaMessage.GENERIC_ERROR + co_vendedor + ": " + e.getMessage());
            }
        }
        return result;
    }
}
