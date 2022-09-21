package recurrent.jpa.phw_vac;

import automation.factory.Logger;
import jpa.BdcMesagge;
import jpa.entity.phw_vac.Gestor_Precuentas;
import jpa.metamodel.phw_vac.Gestor_Precuentas_;
import recurrent.domain.business.GestorPrecuentas;
import recurrent.domain.mapper.GestorPrecuentasMapper;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla GESTOR_OPORTUNIDADES
 */
public class Gestor_PrecuentasJpaImpl implements Gestor_PrecuentasJpa{

    private static Logger LOG = Logger.getLogger(Gestor_PrecuentasJpaImpl.class);

    private EntityManager entityManager;

    public Gestor_PrecuentasJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Gestor_Precuentas findByByClienteFicticioAndEstadoReg(Integer clienteFicticio, String estadoReg) {
        Gestor_Precuentas result = null;
        try{
            LOG.info("Buscando el registro para el cliente ficticio: "+clienteFicticio);
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Gestor_Precuentas> criteriaQuery = criteriaBuilder.createQuery(Gestor_Precuentas.class);
            Root<Gestor_Precuentas> root = criteriaQuery.from(Gestor_Precuentas.class);
            criteriaQuery.select(root);
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(Gestor_Precuentas_.CLIENTE_FICTICIO), clienteFicticio),
                            criteriaBuilder.equal(root.get(Gestor_Precuentas_.ESTADO_REG), estadoReg)
                    )
            );
            result = entityManager.createQuery(criteriaQuery).getSingleResult();
            LOG.info("Se ha encontrado el registro para el cliente ficticio: "+clienteFicticio);
        }catch (Exception e){
            if(e instanceof NoResultException){
                LOG.info(BdcMesagge.NO_RESULT +clienteFicticio+": "+e.getMessage());
            }else{
                LOG.error(BdcMesagge.GENERIC_ERROR+clienteFicticio+": "+e.getMessage());
            }
        }
        return result;
    }

    @Override
    public boolean updateGestorPrecuentasByClienteFicticioAndEstadoReg(String line) {
        boolean result = true;
        GestorPrecuentasMapper mapper = new GestorPrecuentasMapper();
        GestorPrecuentas gestorPrecuentas = mapper.getGestorPrecuentasFromString(line);
        try {
            if(gestorPrecuentas != null){
                CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                CriteriaUpdate<Gestor_Precuentas> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Gestor_Precuentas.class);
                Root<Gestor_Precuentas> root = criteriaUpdate.from(Gestor_Precuentas.class);

                LOG.info("Actualizando el registro para el cliente ficticio: "+gestorPrecuentas.getClienteFicticio());
                criteriaUpdate.set(root.get(Gestor_Precuentas_.estadoReg), gestorPrecuentas.getEstadoRegAfter())
                        .set(root.get(Gestor_Precuentas_.idUsuario), gestorPrecuentas.getIdUsuario())
                        .set(root.get(Gestor_Precuentas_.idCampSalesforce), gestorPrecuentas.getIdCampSalesforce())
                        .set(root.get(Gestor_Precuentas_.feInicio), gestorPrecuentas.getFeInicio())
                        .set(root.get(Gestor_Precuentas_.feModiReg), gestorPrecuentas.getFeModiReg())
                        .set(root.get(Gestor_Precuentas_.txObservacion), gestorPrecuentas.getTxObservacion())
                ;
                criteriaUpdate.where(
                        criteriaBuilder.and(
                                criteriaBuilder.equal(root.get(Gestor_Precuentas_.clienteFicticio), gestorPrecuentas.getClienteFicticio()),
                                criteriaBuilder.equal(root.get(Gestor_Precuentas_.estadoReg), gestorPrecuentas.getEstadoRegCurrent())
                        )
                );
                this.entityManager.createQuery(criteriaUpdate).executeUpdate();
                LOG.info("Se ha actualizado el registro para el cliente ficticio: "+gestorPrecuentas.getClienteFicticio());
            }
        } catch (Exception e) {
            result = false;
            LOG.error("Ha ocurrido un error al actualizar el registro para el cliente ficticio: "+gestorPrecuentas.getClienteFicticio());
        }
        return result;
    }
}
