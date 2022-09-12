package mercado.potencial.jpa.sisora;

import automation.factory.Logger;
import jpa.SisoraMessage;
import jpa.entity.sisora.Tsi_Caract;
import jpa.entity.sisora.Tsi_Emprvcaract;
import jpa.entity.sisora.Tsi_Vcaract;
import jpa.metamodel.sisora.Tsi_Caract_;
import jpa.metamodel.sisora.Tsi_Emprvcaract_;
import jpa.metamodel.sisora.Tsi_Vcaract_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla TSI_EMPRVCARACT
 */
public class Tsi_EmprvcaractProjection {

    private static Logger LOG = Logger.getLogger(Tsi_EmprvcaractProjection.class);

    private EntityManager entityManager;

    public Tsi_EmprvcaractProjection(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    /**
     * Método que busca el número de empleados de una empresa dado un código de empresa
     * @param co_empresa
     * @return número de empleados
     */
    public Integer finNumeroEmpleadosByCoEmpresa(Integer co_empresa){
        Integer result = null;
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
            Root<Tsi_Emprvcaract> root1 = criteriaQuery.from(Tsi_Emprvcaract.class);
            Root<Tsi_Caract> root2 = criteriaQuery.from(Tsi_Caract.class);
            Root<Tsi_Vcaract> root3 = criteriaQuery.from(Tsi_Vcaract.class);
            criteriaQuery.select(root1.get(Tsi_Emprvcaract_.nu_vcaract_empr));
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root1.get(Tsi_Emprvcaract_.co_caract), root2.get(Tsi_Caract_.co_caract)),
                            criteriaBuilder.equal(root1.get(Tsi_Emprvcaract_.co_caract), root3.get(Tsi_Vcaract_.co_caract)),
                            criteriaBuilder.equal(root1.get(Tsi_Emprvcaract_.cv_caract), root3.get(Tsi_Vcaract_.cv_caract)),
                            criteriaBuilder.equal(root1.get(Tsi_Emprvcaract_.co_caract), "0003"),
                            criteriaBuilder.equal(root1.get(Tsi_Emprvcaract_.co_empresa), co_empresa),
                            criteriaBuilder.equal(root1.get(Tsi_Emprvcaract_.cr_baja_reg), " "),
                            criteriaBuilder.equal(root2.get(Tsi_Caract_.cr_baja_reg), " "),
                            criteriaBuilder.equal(root3.get(Tsi_Vcaract_.cr_baja_reg), " ")
                    )
            );
            result = entityManager.createQuery(criteriaQuery).getSingleResult();
        }catch(Exception e){
            if(e instanceof NoResultException){
                LOG.info(SisoraMessage.NO_RESULT +co_empresa+": "+e.getMessage());
            }else{
                LOG.error(SisoraMessage.GENERIC_ERROR+co_empresa+" : "+e.getMessage());
            }
        }
        return result;
    }
}
