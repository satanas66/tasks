package recurrent.tasks;

import automation.factory.Logger;
import automation.factory.Utils;
import jpa.ConnectionJpa;
import recurrent.jpa.phw_vac.Gestor_PrecuentasJpa;
import recurrent.jpa.phw_vac.Gestor_PrecuentasJpaImpl;

import javax.persistence.EntityManager;
import java.util.List;

public class GestorPrecuentasTasks {

    private static Logger LOG = Logger.getLogger(GestorPrecuentasTasks.class);

    private static final String PATH = "C:/tasks/recurrent/src/main/resources/update/";

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Gestor_PrecuentasJpa gestor_precuentasJpa;

    public GestorPrecuentasTasks() {
        relationalDBStart();
        initOracleProjections();
    }

    /**
     * Método que establece las conexiones a las Bases de datos relacionales Oracle y MySql
     */
    public void relationalDBStart() {
        LOG.info("Abriendo conexiones a OracleDB");
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getPHWVACEntityManager();
        LOG.info("Conexiones establecidas a OracleDB");
    }

    /**
     * Método que cierra las conexiones a MONGO, ORACLE y MySql
     */
    public void closureOfConnections() {
        LOG.info("Cerrando conexiones a OracleDB");
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
        LOG.info("Las conexiones a OracleDB, MySQL y MongoDB han sido cerradas");
    }

    /**
     * Método que instancia las clases de proyección para recoger devolver todos los
     * valores asociados a los KPIs desde OracleDB
     */
    public void initOracleProjections() {
        LOG.info("Instanciado clases projection para realizar las transacciones a OracleDB");
        gestor_precuentasJpa = new Gestor_PrecuentasJpaImpl(entityManager);
    }

    public void updateGestorPrecuentasFromFile() {
        entityManager.getTransaction().begin();
        List<String> lines = Utils.generateListFromFile(PATH, "gestor_precuentas.csv");
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            gestor_precuentasJpa.updateGestorPrecuentasByClienteFicticioAndEstadoReg(line);
        }
        entityManager.getTransaction().commit();
    }

    public void updateGestorPrecuentasErrorFromFile() {
        entityManager.getTransaction().begin();
        List<String> lines = Utils.generateListFromFile(PATH, "gestor_precuentas_error.csv");
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            gestor_precuentasJpa.updateGestorPrecuentasByClienteFicticioAndEstadoRegError(line);
        }
        entityManager.getTransaction().commit();
    }
}
