package recurrent.tasks;

import automation.factory.Logger;
import automation.factory.Utils;
import jpa.ConnectionJpa;
import recurrent.jpa.phw_vac.Gestor_OportunidadesJpa;
import recurrent.jpa.phw_vac.Gestor_OportunidadesJpaImpl;

import javax.persistence.EntityManager;
import java.util.List;

public class GestorOportunidadesTasks {

    private static Logger LOG = Logger.getLogger(GestorOportunidadesTasks.class);

    private ConnectionJpa connectionJpa;

    private EntityManager entityManager;

    private Gestor_OportunidadesJpa gestor_oportunidadesJpa;

    public GestorOportunidadesTasks() {
        relationalDBStart();
        initOracleProjection();
    }

    /**
     * Método que establece las conexiones a las Bases de datos relacionales Oracle y MySql
     */
    public void relationalDBStart() {
        LOG.info("Abriendo conexiones a OracleDB...");
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getPHWVACEntityManager();
        String message = "Se ha establecido la conexión con la base de datos " + connectionJpa.getPHW_VAC();
        if (entityManager == null) {
            message = "No ha sido posible establecer la conexión con la base de datos " + connectionJpa.getPHW_VAC();
        }
        LOG.info(message);
    }

    /**
     * Método que cierra las conexiones ORACLE y MySql
     */
    public void closureOfConnections() {
        LOG.info("Cerrando conexiones a OracleDB");
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
        LOG.info("Las conexiones a OracleDB, MySQL y MongoDB han sido cerradas");
    }

    public void initOracleProjection(){
        LOG.info("Instanciado clases projection para realizar las transacciones a OracleDB");
        gestor_oportunidadesJpa = new Gestor_OportunidadesJpaImpl(entityManager);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Gestor_OportunidadesJpa getGestor_oportunidadesJpa() {
        return gestor_oportunidadesJpa;
    }
}
