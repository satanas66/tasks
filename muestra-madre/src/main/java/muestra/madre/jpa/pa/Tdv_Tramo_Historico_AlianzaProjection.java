package muestra.madre.jpa.pa;

import automation.factory.Logger;
import jpa.BipaMessage;
import jpa.entity.pa.Tdv_Tramo_Historico_Alianza;
import jpa.metamodel.pa.Tdv_Tramo_Historico_Alianza_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla TDV_TRAMO_HISTORICO_ALIANZA
 */
public class Tdv_Tramo_Historico_AlianzaProjection {

    private static Logger LOG = Logger.getLogger(Tdv_Tramo_Historico_AlianzaProjection.class);

    private EntityManager entityManager;

    public Tdv_Tramo_Historico_AlianzaProjection(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    /**
     * Método que busca una alianza dado un código de cliente
     * @param clientCode
     * @return alianza
     */
    public String findAlianzaByClientCode(Integer clientCode){
        String result = null;
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Tdv_Tramo_Historico_Alianza> root = criteriaQuery.from(Tdv_Tramo_Historico_Alianza.class);
            criteriaQuery.select(
                    root.get(Tdv_Tramo_Historico_Alianza_.alianza)
            );
            criteriaQuery.where(
                    criteriaBuilder.equal(
                            root.get(Tdv_Tramo_Historico_Alianza_.co_cliente), clientCode)
            );
            criteriaQuery.orderBy(
                    criteriaBuilder.desc(
                            root.get(Tdv_Tramo_Historico_Alianza_.ct_systemmodstamp)
                    )
            );
            result = entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
        }catch (Exception e){
            if (e instanceof NoResultException) {
                LOG.info(BipaMessage.NO_RESULT + clientCode + ": " + e.getMessage());
            } else {
                LOG.error(BipaMessage.GENERIC_ERROR + clientCode + ": " + e.getMessage());
            }
        }
        return  result;
    }
}
