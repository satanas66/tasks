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

    private static ConnectionJpa connectionJpa;

    private static EntityManager entityManager;

    private static Gestor_OportunidadesJpa gestor_oportunidadesJpa;

    public GestorOportunidadesTasks() {
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
     * Método que cierra las conexiones a ORACLE
     */
    public void closureOfConnections() {
        LOG.info("Cerrando conexiones a MongoDB y OracleDB");
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
        gestor_oportunidadesJpa = new Gestor_OportunidadesJpaImpl(entityManager);
    }

    public void insertGestorOportunidadesFromFile(String path, String nameFile) {
        entityManager.getTransaction().begin();
        List<String> lines = Utils.generateListFromFile(path, nameFile);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            gestor_oportunidadesJpa.insertGestor_Oportunidades(line);
        }
        entityManager.getTransaction().commit();
    }
}
