package mercado.potencial.jpa.gestford;

import automation.factory.Logger;
import jpa.GestfordMessage;
import jpa.entity.gestford.Tsf_Account;
import jpa.metamodel.gestford.Tsf_Account_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla TSF_ACCOUNT *
 */
public class Tsf_AccountProjection {

    private static Logger LOG = Logger.getLogger(Tsf_AccountProjection.class);

    private EntityManager entityManager;

    public Tsf_AccountProjection(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    /**
     * Metodo que genera una proyeccion con la fecha de creación, id salesforce y rgpd03, rgpd06, rgpd07
     * @param clientCode
     * @param nif
     * @return proyeccion
     */
    public Object[] findProjectionECuenta(Integer clientCode, String nif){
        Object[] projection = null;
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<Tsf_Account> root = criteriaQuery.from(Tsf_Account.class);
            criteriaQuery.multiselect(
                    root.get(Tsf_Account_.createddate),
                    root.get(Tsf_Account_.id),
                    root.get(Tsf_Account_.rgpd03),
                    root.get(Tsf_Account_.rgpd06),
                    root.get(Tsf_Account_.rgpd07)
            );
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(Tsf_Account_.co_cliente), clientCode),
                            criteriaBuilder.like(root.get(Tsf_Account_.c_clave_unica_comercial_c), "%"+clientCode+"%"),
                            criteriaBuilder.like(root.get(Tsf_Account_.c_clave_unica_comercial_c), "%"+nif+"%")
                    )
            );
            criteriaQuery.orderBy(
                    criteriaBuilder.desc(
                            root.get(Tsf_Account_.lastmodifieddate)));
            projection = entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
        }catch(Exception e){
            if(e instanceof NoResultException){
                LOG.info(GestfordMessage.NO_RESULT +clientCode+": "+e.getMessage());
            }else{
                LOG.error(GestfordMessage.GENERIC_ERROR +clientCode+": "+e.getMessage());
            }
        }
        return projection;
    }
}
