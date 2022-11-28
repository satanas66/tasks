package recurrent.application;

import automation.factory.Logger;
import automation.factory.Utils;
import jpa.BdcMesagge;
import jpa.BipaMessage;
import jpa.GestfordMessage;
import jpa.SisoraMessage;
import jpa.entity.gestford.Tsf_Account;
import jpa.entity.pa.Tdv_Oportunidades_Por_Cliente;
import jpa.entity.pa.Tdv_Tramo_Historico_Alianza;
import jpa.entity.pa.Vhv_Cuotas_Mes_V2;
import jpa.entity.phw_vac.F_Datos_Contacto;
import jpa.entity.phw_vac.F_Impago;
import jpa.entity.sisora.Tsi_Actvad;
import jpa.entity.sisora.Tsi_Sector;
import jpa.metamodel.gestford.Tsf_Account_;
import jpa.metamodel.pa.Tdv_Oportunidades_Por_Cliente_;
import jpa.metamodel.pa.Tdv_Tramo_Historico_Alianza_;
import jpa.metamodel.pa.Vhv_Cuotas_Mes_V2_;
import jpa.metamodel.phw_vac.F_Datos_Contacto_;
import jpa.metamodel.phw_vac.F_Impago_;
import jpa.metamodel.sisora.Tsi_Actvad_;
import jpa.metamodel.sisora.Tsi_Sector_;
import recurrent.domain.CancelacionesTeleventaJpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en bases de datos Oracle y Mysql *
 */
public class CancelacionesTeleventaJpaImpl implements CancelacionesTeleventaJpa {

    private static Logger LOG = Logger.getLogger(CancelacionesTeleventaJpaImpl.class);

    private EntityManager entityManagerPHW_VAC;

    private EntityManager entityManagerSISORA;

    private EntityManager entityManagerGESTFORD;

    private EntityManager entityManagerPA;

    public CancelacionesTeleventaJpaImpl(EntityManager entityManagerPHW_VAC,
                                         EntityManager entityManagerSISORA,
                                         EntityManager entityManagerGESTFORD,
                                         EntityManager entityManagerPA) {
        this.entityManagerPHW_VAC = entityManagerPHW_VAC;
        this.entityManagerSISORA = entityManagerSISORA;
        this.entityManagerGESTFORD = entityManagerGESTFORD;
        this.entityManagerPA = entityManagerPA;
    }

    /**
     * Metodo que devuelve el id_account dado un código de cliente
     * @param clientCode
     * @return id_account
     */
    @Override
    public String getIdAccountByClientCode(Integer clientCode) {
        String result = null;
        try{
            CriteriaBuilder criteriaBuilder = entityManagerGESTFORD.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Tsf_Account> root = criteriaQuery.from(Tsf_Account.class);
            criteriaQuery.select(root.get(Tsf_Account_.id));
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(Tsf_Account_.co_cliente), clientCode),
                            criteriaBuilder.like(root.get(Tsf_Account_.c_clave_unica_comercial_c), "%"+clientCode+"%")
                    )
            );
            criteriaQuery.orderBy(
                    criteriaBuilder.desc(
                            root.get(Tsf_Account_.lastmodifieddate)));
            result = entityManagerGESTFORD.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
        }catch(Exception e){
            if(e instanceof NoResultException){
                LOG.info(GestfordMessage.NO_RESULT +clientCode+": "+e.getMessage());
            }else{
                LOG.error(GestfordMessage.GENERIC_ERROR +clientCode+": "+e.getMessage());
            }
        }
        return result;
    }

    @Override
    public String getCoAmbiComerByClientCode(Integer clientCode) {
        return null;
    }

    @Override
    public Object[] getProjectionFromF_Datos_ConctoByClientCode(Integer clientCode) {
        Object[] projection = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManagerPHW_VAC.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<F_Datos_Contacto> root = criteriaQuery.from(F_Datos_Contacto.class);
            criteriaQuery.multiselect(
                    root.get(F_Datos_Contacto_.co_ccaa),
                    root.get(F_Datos_Contacto_.tx_ccaa),
                    root.get(F_Datos_Contacto_.co_actvad_pral),
                    root.get(F_Datos_Contacto_.tx_actvad),
                    root.get(F_Datos_Contacto_.de_prov)
            );
            criteriaQuery.where(
                    criteriaBuilder.equal(
                            root.get(F_Datos_Contacto_.co_cliente), clientCode)
            );
            projection = entityManagerPHW_VAC.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(BdcMesagge.NO_RESULT + clientCode + ": " + e.getMessage());
            } else {
                LOG.error(BdcMesagge.GENERIC_ERROR + clientCode + ": " + e.getMessage());
            }
        }
        return projection;
    }

    /**
     * Método que encuentra el código de un sector dado el código de una actividad
     * @param activityCode
     * @return sector
     */
    @Override
    public String getSectorCodeByActivityCode(Integer activityCode) {
        String result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManagerSISORA.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Tsi_Actvad> root = criteriaQuery.from(Tsi_Actvad.class);
            criteriaQuery.select(root.get(Tsi_Actvad_.co_sector));
            criteriaQuery.where(
                    criteriaBuilder.equal(root.get(Tsi_Actvad_.co_actvad), activityCode)
            );
            result = entityManagerSISORA.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(SisoraMessage.NO_RESULT +activityCode + ": " + e.getMessage());
            } else {
                LOG.error(SisoraMessage.GENERIC_ERROR + activityCode + ": " + e.getMessage());
            }
        }
        return result;
    }

    /**
     * Método que devuelve el texto de un sector dado un código de sector
     * @param sectorCode
     * @return texto del sector
     */
    @Override
    public String getSectorTextBySectorCode(String sectorCode) {
        String result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManagerSISORA.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Tsi_Sector> sector = criteriaQuery.from(Tsi_Sector.class);
            criteriaQuery.select(sector.get(Tsi_Sector_.tx_sector));
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(sector.get(Tsi_Sector_.co_sector), sectorCode),
                            criteriaBuilder.equal(sector.get(Tsi_Sector_.co_subSector), "000"),
                            criteriaBuilder.equal(sector.get(Tsi_Sector_.co_ssubSector), "000")
                    )
            );
            result = entityManagerSISORA.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(SisoraMessage.NO_RESULT+ sectorCode + ": " + e.getMessage());
            } else {
                LOG.error(SisoraMessage.GENERIC_ERROR + sectorCode + ": " + e.getMessage());
            }
        }
        return result;
    }

    /**
     * Método que busca una alianza dado un código de cliente
     * @param clientCode
     * @return alianza
     */
    @Override
    public String getAlianzaByClientCode(Integer clientCode) {
        String result = null;
        try{
            CriteriaBuilder criteriaBuilder = entityManagerPA.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Tdv_Tramo_Historico_Alianza> root = criteriaQuery.from(Tdv_Tramo_Historico_Alianza.class);
            criteriaQuery.select(root.get(Tdv_Tramo_Historico_Alianza_.alianza));
            criteriaQuery.where(
                    criteriaBuilder.equal(
                            root.get(Tdv_Tramo_Historico_Alianza_.co_cliente), clientCode)
            );
            criteriaQuery.orderBy(
                    criteriaBuilder.desc(
                            root.get(Tdv_Tramo_Historico_Alianza_.ct_systemmodstamp)
                    )
            );
            result = entityManagerPA.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
        }catch (Exception e){
            if (e instanceof NoResultException) {
                LOG.info(BipaMessage.NO_RESULT + clientCode + ": " + e.getMessage());
            } else {
                LOG.error(BipaMessage.GENERIC_ERROR + clientCode + ": " + e.getMessage());
            }
        }
        return  result;
    }

    /**
     * Método que busca un id_producto según su código de cliente
     * (Internamente se verifica el resultado para el mes actual y el anterior)
     * Si devuelve un valor se dice que el cliente está VIVO
     * @param clientCode
     * @return identificador del producto
     */
    @Override
    public String getStatusPayByClientCode(Integer clientCode) {
        String result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManagerPHW_VAC.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<F_Impago> root = criteriaQuery.from(F_Impago.class);
            criteriaQuery.select(
                    root.get(F_Impago_.in_impago)
            );
            criteriaQuery.where(
                    criteriaBuilder.equal(root.get(F_Impago_.co_cliente), clientCode)
            );
            result = entityManagerPHW_VAC.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(BdcMesagge.NO_RESULT + clientCode + ": " + e.getMessage());
            } else {
                LOG.error(BdcMesagge.GENERIC_ERROR + clientCode + ": " + e.getMessage());
            }
        }
        return result;
    }

    @Override
    public String getLiveClientByClientCode(Integer clientCode) {
        String result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManagerPA.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Vhv_Cuotas_Mes_V2> root = criteriaQuery.from(Vhv_Cuotas_Mes_V2.class);
            criteriaQuery.select(
                    root.get(Vhv_Cuotas_Mes_V2_.id_producto)
            );
            List<Integer> months = Utils.getCurrentMonthsAndPreviousMonth();
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.co_cliente), clientCode),
                            criteriaBuilder.or(
                                    criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_mes), months.get(0)),
                                    criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_mes), months.get(1))
                            )
                    )
            );
            result = entityManagerPA.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            if (e instanceof NoResultException) {
                LOG.info(BipaMessage.NO_RESULT + clientCode + ": " + e.getMessage());
            } else {
                LOG.error(BipaMessage.GENERIC_ERROR + clientCode + ": " + e.getMessage());
            }
        }
        return result;
    }

    /**
     * Metodo encargado de encontrar la última oportunidad ganada
     * @param clientCode
     * @return proyección
     */
    @Override
    public String lastChanceWinByClientCode(Integer clientCode) {
        String result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManagerPA.getCriteriaBuilder();
            CriteriaQuery<String> query = criteriaBuilder.createQuery(String.class);
            Root<Tdv_Oportunidades_Por_Cliente> root = query.from(Tdv_Oportunidades_Por_Cliente.class);
            query.select(root.get(Tdv_Oportunidades_Por_Cliente_.fe_creacion_dmy));
            query.where(
                    criteriaBuilder.equal(
                            root.get(Tdv_Oportunidades_Por_Cliente_.co_cliente), clientCode)
            );
            query.orderBy(
                    criteriaBuilder.desc(
                            root.get(Tdv_Oportunidades_Por_Cliente_.ct_systemmodstamp))
            );
            result = entityManagerPA.createQuery(query).setMaxResults(1).getSingleResult();
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
