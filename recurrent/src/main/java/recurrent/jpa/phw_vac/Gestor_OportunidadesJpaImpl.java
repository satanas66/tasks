package recurrent.jpa.phw_vac;

import automation.factory.Logger;
import jpa.BdcMesagge;
import jpa.entity.phw_vac.Gestor_Oportunidades;
import jpa.metamodel.phw_vac.Gestor_Oportunidades_;
import recurrent.domain.business.GestorOportunidades;
import recurrent.domain.mapper.GestorOportunidadesMapper;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de recoger los valores almacenados en la tabla GESTOR_OPORTUNIDADES
 */
public class Gestor_OportunidadesJpaImpl implements Gestor_OportunidadesJpa {

    private static Logger LOG = Logger.getLogger(Gestor_OportunidadesJpaImpl.class);

    private EntityManager entityManager;

    public Gestor_OportunidadesJpaImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean insertGestor_Oportunidades(String line) {
        boolean result = true;
        GestorOportunidadesMapper mapper = new GestorOportunidadesMapper();
        GestorOportunidades gestorOportunidades = mapper.getGestorOportunidadesFromString(line);
        Gestor_Oportunidades entity = mapper.getGestor_OportunidadesFromString(gestorOportunidades);
        try{
            LOG.info("Insertando el registro para el cliente: "+entity.getCo_cliente());
            entityManager.persist(entity);
            LOG.info("El registro para el cliente ha sido insertado correctamente: "+entity.getCo_cliente());
        }catch (Exception e){
            result = false;
            LOG.error("Ha ocurrido un error al insertar el registro para el cliente: "+entity.getCo_cliente());
        }
        return result;
    }

    @Override
    public Gestor_Oportunidades getGestorOportunidadesByCoClienteAndIdAccount(Integer co_cliente, String id_account) {
        Gestor_Oportunidades result = null;
        try{
            LOG.info("Buscando el registro para el cliente ficticio: "+co_cliente);
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Gestor_Oportunidades> criteriaQuery = criteriaBuilder.createQuery(Gestor_Oportunidades.class);
            Root<Gestor_Oportunidades> root = criteriaQuery.from(Gestor_Oportunidades.class);
            criteriaQuery.select(root);
            criteriaQuery.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get(Gestor_Oportunidades_.co_cliente), co_cliente),
                            criteriaBuilder.equal(root.get(Gestor_Oportunidades_.id_account), id_account)
                    )
            );
            criteriaQuery.orderBy(
                    criteriaBuilder.desc(root.get(Gestor_Oportunidades_.fe_crea_reg))
            );

            result = entityManager.createQuery(criteriaQuery).getResultList().get(0);
            LOG.info("Se ha encontrado el registro para el cliente ficticio: "+co_cliente);
        }catch (Exception e){
            if (e instanceof NoResultException) {
                LOG.info(BdcMesagge.NO_RESULT + co_cliente + ": " + e.getMessage());
            } else {
                LOG.error(BdcMesagge.GENERIC_ERROR + co_cliente + ": " + e.getMessage());
            }
        }
        return result;
    }


}
