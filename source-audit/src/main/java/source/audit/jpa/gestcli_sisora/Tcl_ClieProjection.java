package source.audit.jpa.gestcli_sisora;

import automation.factory.Logger;
import jpa.GestcliSisoraMessage;
import jpa.entity.gestcli_sisora.*;
import jpa.metamodel.gestcli_sisora.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla TSI_ACTVAD
 */
public class Tcl_ClieProjection {

    private static Logger LOG = Logger.getLogger(Tcl_ClieProjection.class);

    private EntityManager entityManager;

    public Tcl_ClieProjection(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Método que encuentra los códigos de clientes que han sido procesados por el proceso scrapping
     *      ORIGENES: codeSource
     *      * 0001: octoparse
     *      * 0004: scrappinghub
     *      * 0006: datacentric
     *      * 0007: facebook
     *      * 0008: listanegocio
     *      * @return lista de códigos
     *        => SELECT CO_CLIENTE
     *         FROM TCL_CLIE t0, TCL_PCOVINFA t1 , tap_infadi t2, tap_valinfa t3
     *         WHERE t0.co_empresa = t1.co_empresa AND  t0.pg_pconex_comer = t1.pg_pconex AND  t0.cr_baja_reg IS NULL AND t1.cr_baja_reg IS NULL AND
     *             t1.co_inf_adi = t2.co_inf_adi AND t1.co_inf_adi = t3.co_inf_adi AND t1.cv_inf_adi = t3.cv_inf_adi AND
     *             t1.co_inf_adi = '0160' AND  t1.cv_inf_adi='000X';<=
     * @param codeSource, código de origen del dato
     * @param operationType, tipo de operación que ha sufrido el dato
     * @return lista de códigos de clientes
     */
    public List<Integer> getClientCodesSegmented(String codeSource, String operationType){
        List<Integer> result = null;
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
            Root<Tcl_Clie> root0 = criteriaQuery.from(Tcl_Clie.class);
            Root<Tcl_Pcovinfa> root1 = criteriaQuery.from(Tcl_Pcovinfa.class);
            Root<Tap_Infadi> root2 = criteriaQuery.from(Tap_Infadi.class);
            Root<Tap_Valinfa> root3 = criteriaQuery.from(Tap_Valinfa.class);

            criteriaQuery.select(root0.get(Tcl_Clie_.co_cliente)).distinct(true);;
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.greaterThan(root0.get(Tcl_Clie_.co_cliente), 0),
                            criteriaBuilder.equal(root0.get(Tcl_Clie_.co_empresa), root1.get(Tcl_Pcovinfa_.co_empresa)),
                            criteriaBuilder.equal(root0.get(Tcl_Clie_.pg_pconex_comer), root1.get(Tcl_Pcovinfa_.pg_pconex)),
                            criteriaBuilder.isNull(root0.get(Tcl_Clie_.cr_baja_reg)),
                            criteriaBuilder.isNull(root1.get(Tcl_Pcovinfa_.cr_baja_reg)),
                            criteriaBuilder.equal(root1.get(Tcl_Pcovinfa_.co_inf_adi), root2.get(Tap_Infadi_.co_inf_adi)),
                            criteriaBuilder.equal(root1.get(Tcl_Pcovinfa_.co_inf_adi), root3.get(Tap_Valinfa_.co_inf_adi)),
                            criteriaBuilder.equal(root1.get(Tcl_Pcovinfa_.cv_inf_adi), root3.get(Tap_Valinfa_.cv_inf_adi)),
                            criteriaBuilder.equal(root1.get(Tcl_Pcovinfa_.co_inf_adi), operationType),
                            criteriaBuilder.equal(root1.get(Tcl_Pcovinfa_.cv_inf_adi), codeSource)
                    )
            );
            criteriaQuery.orderBy(criteriaBuilder.desc(root0.get(Tcl_Clie_.co_cliente)));
            result = entityManager.createQuery(criteriaQuery).getResultList();
        }catch (Exception e){
            if (e instanceof NoResultException) {
                LOG.info(GestcliSisoraMessage.NO_RESULT +": " + e.getMessage());
            } else {
                LOG.error(GestcliSisoraMessage.GENERIC_ERROR + ": " + e.getMessage());
            }
        }
        return result;
    }

    /**
     * Método que encuentra todas las altas producidas por el proceso scrapping
     * id_provee_orig_inf => 056 octoparse
     * id_provee_orig_inf => 041 datacentric     *
     * @return lista de códigos de clientes y fechas creación y modificación de registros
       SELECT co_cliente, fe_crea_reg, fe_modi_reg
       FROM TCL_CLIE t1 , TCL_LGCRUINFPCONEX t2
       WHERE t1.co_empresa = t2.co_empresa AND
             t1.pg_pconex_comer = t2.pg_conexion AND
             t1.cr_baja_reg IS NULL AND t2.cr_baja_reg IS NULL
      -- altas de scrapping
      AND t2.ct_MOV = 'A'  -- alta de direccion
      AND t2.ct_entorno = 'XG' -- alta desde cruce generico
      AND t2.id_provee_orig_inf like '%056%';
     */
    public List<Object[]> getRegistrationByScrappingProcess(){
        List<Object[]> result = null;
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<Tcl_Clie> root1 = criteriaQuery.from(Tcl_Clie.class);
            Root<Tcl_Lgcruinfpconex> root2 = criteriaQuery.from(Tcl_Lgcruinfpconex.class);
            criteriaQuery.multiselect(
                    root1.get(Tcl_Clie_.co_cliente),
                    root1.get(Tcl_Clie_.fe_crea_reg),
                    root1.get(Tcl_Clie_.fe_modi_reg)).distinct(true);
            criteriaQuery.where(
                criteriaBuilder.and(
                        criteriaBuilder.greaterThan(root1.get(Tcl_Clie_.co_cliente), 0),
                        criteriaBuilder.equal(root1.get(Tcl_Clie_.co_empresa), root2.get(Tcl_Lgcruinfpconex_.co_empresa)),
                        criteriaBuilder.equal(root1.get(Tcl_Clie_.pg_pconex_comer), root2.get(Tcl_Lgcruinfpconex_.pg_conexion)),
                        criteriaBuilder.isNull(root1.get(Tcl_Clie_.cr_baja_reg)),
                        criteriaBuilder.isNull(root2.get(Tcl_Lgcruinfpconex_.cr_baja_reg)),
                        criteriaBuilder.equal(root2.get(Tcl_Lgcruinfpconex_.ct_mov), "A"),
                        criteriaBuilder.equal(root2.get(Tcl_Lgcruinfpconex_.ct_entorno), "XG"),
                        criteriaBuilder.or(
                                criteriaBuilder.like(root2.get(Tcl_Lgcruinfpconex_.id_provee_orig_inf), "%056%"),
                                criteriaBuilder.like(root2.get(Tcl_Lgcruinfpconex_.id_provee_orig_inf), "%041%")
                        )
                )
            );
            criteriaQuery.orderBy(criteriaBuilder.desc(root1.get(Tcl_Clie_.fe_crea_reg)));
            result = entityManager.createQuery(criteriaQuery).getResultList();
        }catch (Exception e){
            if (e instanceof NoResultException) {
                LOG.info(GestcliSisoraMessage.NO_RESULT +": " + e.getMessage());
            } else {
                LOG.error(GestcliSisoraMessage.GENERIC_ERROR + ": " + e.getMessage());
            }
        }
        return result;
    }

    /**
     * Método que encuentra todas las modificaciones producidas por el proceso scrapping
     * @return lista de códigos de clientes y fechas creación y modificación de registros
     * SELECT co_cliente, fe_crea_reg, fe_modi_reg
     * FROM tcl_clie t1 , tcl_pcovinfa  t2
     * -- join
     * WHERE t1.co_empresa = t2.co_empresa AND t1.pg_pconex_comer = t2.pg_pconex
     * AND t1.cr_baja_reg IS NULL AND t2.cr_baja_reg IS NULL
     * -- inf adicional de modificacion por scraping
     * AND t2.co_inf_adi = '0161'
     *
     */
    public List<Object[]> getModificationByScrappingProcess(){
        List<Object[]> result = null;
        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<Tcl_Clie> root1 = criteriaQuery.from(Tcl_Clie.class);
            Root<Tcl_Pcovinfa> root2 = criteriaQuery.from(Tcl_Pcovinfa.class);
            criteriaQuery.multiselect(
                    root1.get(Tcl_Clie_.co_cliente),
                    root1.get(Tcl_Clie_.fe_crea_reg),
                    root1.get(Tcl_Clie_.fe_modi_reg)).distinct(true);
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.greaterThan(root1.get(Tcl_Clie_.co_cliente), 0),
                            criteriaBuilder.equal(root1.get(Tcl_Clie_.co_empresa), root2.get(Tcl_Pcovinfa_.co_empresa)),
                            criteriaBuilder.equal(root1.get(Tcl_Clie_.pg_pconex_comer), root2.get(Tcl_Pcovinfa_.pg_pconex)),
                            criteriaBuilder.isNull(root1.get(Tcl_Clie_.cr_baja_reg)),
                            criteriaBuilder.isNull(root2.get(Tcl_Pcovinfa_.cr_baja_reg)),
                            criteriaBuilder.equal(root2.get(Tcl_Pcovinfa_.co_inf_adi), "0161")
                    )
            );
            criteriaQuery.orderBy(criteriaBuilder.desc(root1.get(Tcl_Clie_.fe_crea_reg)));
            result = entityManager.createQuery(criteriaQuery).getResultList();
        }catch (Exception e){
            if (e instanceof NoResultException) {
                LOG.info(GestcliSisoraMessage.NO_RESULT +": " + e.getMessage());
            } else {
                LOG.error(GestcliSisoraMessage.GENERIC_ERROR + ": " + e.getMessage());
            }
        }
        return result;
    }
}
