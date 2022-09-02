package mercado.candidato.jpa.pa;

import automation.factory.Logger;
import automation.factory.Utils;
import jpa.BipaMessage;
import jpa.entity.pa.Tdv_Servicio;
import jpa.entity.pa.Vhv_Cuotas_Mes_V2;
import jpa.metamodel.pa.Tdv_Servicio_;
import jpa.metamodel.pa.Vhv_Cuotas_Mes_V2_;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Date;
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
     * Método que busca un id_producto según su código de cliente
     * (Internamente se verifica el resultado para el mes actual y el anterior)
     *
     * @param clientCode
     * @return identificador del producto
     */
    public String findIdProductByClientCode(String clientCode) {
        String result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
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
            result = entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
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
     * Método que busca un id_producto según su código de cliente
     * (Internamente se verifica el resultado para el mes actual y el anterior)
     *
     * @param clientCode
     * @return identificador del producto
     */
    public Object[] findIdsProductServiceAndOportunidadByClientCode(String clientCode) {
        Object[] result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<Vhv_Cuotas_Mes_V2> root = criteriaQuery.from(Vhv_Cuotas_Mes_V2.class);
            Root<Tdv_Servicio> root2 = criteriaQuery.from(Tdv_Servicio.class);
            criteriaQuery.multiselect(
                    root.get(Vhv_Cuotas_Mes_V2_.id_producto),
                    root2.get(Tdv_Servicio_.tipo_pack),
                    root.get(Vhv_Cuotas_Mes_V2_.id_servicio)
            );
            List<Integer> months = Utils.getCurrentMonthsAndPreviousMonth();
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.co_cliente), clientCode),
                            criteriaBuilder.or(
                                    criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_mes), months.get(0)),
                                    criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_mes), months.get(1))
                            ),
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_servicio), root2.get(Tdv_Servicio_.id_servicio)),
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_oportunidad), root2.get(Tdv_Servicio_.id_oportunidad))
                    )
            );
            result = entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
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
     * Método que busca todos los códigos de clientes vivos para el mercado candidato
     *
     * @return lista de códigos de clientes
     */
    public List<String> findAllAliveClientCodes() {
        List<String> result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Vhv_Cuotas_Mes_V2> root = criteriaQuery.from(Vhv_Cuotas_Mes_V2.class);
            criteriaQuery.select(
                    root.get(Vhv_Cuotas_Mes_V2_.co_cliente)
            ).distinct(true);
            List<Integer> months = Utils.getCurrentMonthsAndPreviousMonth();
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.isNotNull(root.get(Vhv_Cuotas_Mes_V2_.co_cliente)),
                            criteriaBuilder.or(
                                    criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_mes), months.get(0)),
                                    criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_mes), months.get(1))
                            )
                    )
            );
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Vhv_Cuotas_Mes_V2_.co_cliente)));
            result = entityManager.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {

            LOG.error(BipaMessage.GENERIC_ERROR + ": " + e.getMessage());

        }
        return result;
    }

    /**
     * Método que busca una proyección con el importe máximo y el mes del importe máximo dado un código de cliente
     *
     * @param clientCode
     * @return proyección
     */
    public Object[] findImporteMaxAndIdMesByClientCode(Integer clientCode) {
        Object[] result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<Vhv_Cuotas_Mes_V2> root = criteriaQuery.from(Vhv_Cuotas_Mes_V2.class);
            criteriaQuery.multiselect(
                    root.get(Vhv_Cuotas_Mes_V2_.importe),
                    root.get(Vhv_Cuotas_Mes_V2_.id_mes)
            );
            criteriaQuery.where(
                    criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.co_cliente), clientCode)
            );
            criteriaQuery.orderBy(
                    criteriaBuilder.desc(root.get(Vhv_Cuotas_Mes_V2_.importe))
            );
            result = entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
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
     * Método que busca el idmes por código cde cliente cuando cp3 es F_ADW (ULT_MES_ADW)
     *
     * @param clientCode
     * @return idmes
     */
    public Integer findIdMesToADWByClienCode(Integer clientCode) {
        Integer result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
            Root<Vhv_Cuotas_Mes_V2> root = criteriaQuery.from(Vhv_Cuotas_Mes_V2.class);
            criteriaQuery.select(
                    root.get(Vhv_Cuotas_Mes_V2_.id_mes)
            );
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.co_cliente), clientCode),
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.cp3), "F_ADW")
                    )
            );
            criteriaQuery.orderBy(
                    criteriaBuilder.desc(root.get(Vhv_Cuotas_Mes_V2_.id_mes))
            );
            result = entityManager.createQuery(criteriaQuery).setMaxResults(1).getSingleResult();
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
     * Método que busca los productos contratados según su código de cliente (MIX_FY22)
     * Internamente se verifica si o si:
     * 1. Que los productos empiecen por F_
     * 2. Que el cliente esté vivo en el mes 03 o 02 del año actual
     * 3. Los productos contratados desde el mes 04 del año anterior hasta el mes 03 del año actual
     *
     * @param clientCode
     * @return lista de cp3
     */
    public List<String> findCp3F_ByClientCode(Integer clientCode) {
        List<String> result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Vhv_Cuotas_Mes_V2> root = criteriaQuery.from(Vhv_Cuotas_Mes_V2.class);
            criteriaQuery.select(
                    root.get(Vhv_Cuotas_Mes_V2_.cp3)
            ).distinct(true);

            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int previousYear = calendar.get(Calendar.YEAR) - 1;
            int currentYear = calendar.get(Calendar.YEAR);
            Integer mes1 = Integer.parseInt(previousYear + "04");
            Integer mes3 = Integer.parseInt(currentYear + "03");//Marzo del año actual
            Integer mes4 = Integer.parseInt(currentYear + "02");//Febrero del año actual

            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.co_cliente), clientCode),
                            criteriaBuilder.like(root.get(Vhv_Cuotas_Mes_V2_.cp3), "%F_%"),
                            criteriaBuilder.between(root.get(Vhv_Cuotas_Mes_V2_.id_mes), mes4, mes3),
                            criteriaBuilder.between(root.get(Vhv_Cuotas_Mes_V2_.id_mes), mes1, mes3)
                    )
            );
            criteriaQuery.orderBy(
                    criteriaBuilder.desc(root.get(Vhv_Cuotas_Mes_V2_.id_mes))
            );
            result = entityManager.createQuery(criteriaQuery).getResultList();
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
     * Método que busca todos los productos contratados cp3 según su código de cliente para el mes actual y mes anterior
     *
     * @param clientCode
     * @return lista de cp3
     */
    public List<String> findCp3ActualAndPreviousMonthByClientCode(Integer clientCode, Integer currentMonth, Integer previousMonth) {
        List<String> result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<String> criteriaQuery = criteriaBuilder.createQuery(String.class);
            Root<Vhv_Cuotas_Mes_V2> root = criteriaQuery.from(Vhv_Cuotas_Mes_V2.class);
            criteriaQuery.select(
                    root.get(Vhv_Cuotas_Mes_V2_.cp3)
            ).distinct(true);

            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.co_cliente), clientCode),

                            criteriaBuilder.or(
                                    criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_mes), currentMonth),
                                    criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_mes), previousMonth)
                            )
                    )
            );
            criteriaQuery.orderBy(
                    criteriaBuilder.desc(root.get(Vhv_Cuotas_Mes_V2_.id_mes))
            );
            result = entityManager.createQuery(criteriaQuery).getResultList();
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
     * Método que suma los productos F_BEE contratados dentro de año fiscal anterior (abril año anterior a marzo del año actual)
     *
     * @param clientCode
     * @return suma de importes
     */
    public Double sumAmountsToCP3_F_BEEContractedInFiscalPastYear(Integer clientCode) {
        Double result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
            Root<Vhv_Cuotas_Mes_V2> root = criteriaQuery.from(Vhv_Cuotas_Mes_V2.class);
            List<Integer> months = Utils.getStartAndEndMonthsInFiscalYear();
            criteriaQuery.select(criteriaBuilder.sum(root.get(Vhv_Cuotas_Mes_V2_.importe)));
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.co_cliente), clientCode),
                            criteriaBuilder.between(root.get(Vhv_Cuotas_Mes_V2_.id_mes), months.get(1), months.get(0)),
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.cp3), "F_BEE")
                    )
            );
            criteriaQuery.orderBy(
                    criteriaBuilder.desc(root.get(Vhv_Cuotas_Mes_V2_.id_mes))
            );
            result = entityManager.createQuery(criteriaQuery).getSingleResult();
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
     * Método que suma el importe de todos los productos contratados en el mes actual
     *
     * @param clientCode
     * @return suma de importes
     */
    public Double sumAmountsCurrentsContractedProducts(Integer clientCode) {
        Double result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
            Root<Vhv_Cuotas_Mes_V2> root = criteriaQuery.from(Vhv_Cuotas_Mes_V2.class);
            criteriaQuery.select(
                    criteriaBuilder.sum(root.get(Vhv_Cuotas_Mes_V2_.importe))
            );
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.co_cliente), clientCode),
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_mes), Utils.getCurrentMonthsAndPreviousMonth().get(0))
                    )
            );
            result = entityManager.createQuery(criteriaQuery).getSingleResult();
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
     * Método que suma el importe de todos los productos contratados en el mes actual
     * @param clientCode
     * @param cp3
     * @return suma de importes
     */
    public Double sumAmountsCurrentsContractedCP3(Integer clientCode, String cp3) {
        Double result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
            Root<Vhv_Cuotas_Mes_V2> root = criteriaQuery.from(Vhv_Cuotas_Mes_V2.class);
            criteriaQuery.select(
                    criteriaBuilder.sum(root.get(Vhv_Cuotas_Mes_V2_.importe))
            );
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.co_cliente), clientCode),
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_mes), Utils.getCurrentMonthsAndPreviousMonth().get(0)),
                            criteriaBuilder.like(root.get(Vhv_Cuotas_Mes_V2_.cp3), "%"+cp3+"%")
                    )
            );
            result = entityManager.createQuery(criteriaQuery).getSingleResult();
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
     * Método que suma los importes del mes actual para los ids de objeto relacionados con productos WEB
     * (CRWEB01 o MTWEB01) = WEB1
     * (CRWEB02 o MTWEB02) = WEB2
     * @param clientCode
     * @param obj1
     * @return suma de importes
     */
    public Double sumAmountsCurrentsIdObjetoIsWeb(Integer clientCode, int obj1) {
        Double result = null;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
            Root<Vhv_Cuotas_Mes_V2> root = criteriaQuery.from(Vhv_Cuotas_Mes_V2.class);
            criteriaQuery.select(
                    criteriaBuilder.sum(root.get(Vhv_Cuotas_Mes_V2_.importe))
            );
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.co_cliente), clientCode),
                            criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_mes), Utils.getCurrentMonthsAndPreviousMonth().get(0)),
                            criteriaBuilder.or(
                                    criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_objeto), "CRWEB0" + obj1),
                                    criteriaBuilder.equal(root.get(Vhv_Cuotas_Mes_V2_.id_objeto), "MTWEB0" + obj1)
                            )
                    )
            );
            result = entityManager.createQuery(criteriaQuery).getSingleResult();
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
