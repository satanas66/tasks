package recurrent.tasks;

import automation.factory.Logger;
import com.mongodb.DBCollection;
import jpa.ConnectionJpa;
import mongo.ConnectionMongo;
import recurrent.domain.Gestor_PrecuentasJpa;
import recurrent.application.Gestor_PrecuentasJpaImpl;
import recurrent.domain.ScrappingMJD;
import recurrent.application.ScrappingMJDImpl;

import javax.persistence.EntityManager;

public class KpisGMBTasks {

    private static Logger LOG = Logger.getLogger(KpisGMBTasks.class);

    private ConnectionJpa connectionJpa;

    private EntityManager entityManager;

    private Gestor_PrecuentasJpa gestor_precuentasJpa;

    private ConnectionMongo connectionMongo;

    private DBCollection scrappingCollection;

    private ScrappingMJD scrappingMJD;


    public KpisGMBTasks() {
        relationalDBStart();
        initOracleProjections();
        noRealionalDBStart();
        initMongoProjections();
    }

    /**
     * Método que establece las conexiones a las Bases de datos relacionales Oracle y MySql
     */
    public void relationalDBStart() {
        LOG.info("Abriendo conexión a OracleDB...");
        connectionJpa = new ConnectionJpa();
        entityManager = connectionJpa.getPHWVACEntityManager();
        String message = "Se ha establecido la conexión con la base de datos " + connectionJpa.getPHW_VAC();
        if (entityManager == null) {
            message = "No ha sido posible establecer la conexión con la base de datos " + connectionJpa.getPHW_VAC();
        }
        LOG.info(message);
    }

    public void noRealionalDBStart(){
        LOG.info("Abriendo conexión a MongoDB...");
        connectionMongo = new ConnectionMongo();
        connectionMongo.setMongoClientReplica();
        scrappingCollection = connectionMongo.getDBCollection("scrapping");
    }

    /**
     * Método que cierra las conexiones ORACLE y MySql
     */
    public void closureOfConnections() {
        LOG.info("Cerrando conexiones a OracleDB y MpongoDB");
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
        connectionMongo.endConnection();
        LOG.info("Las conexiones a OracleDB y MongoDB han sido cerradas");
    }

    /**
     * Método que instancia las clases de proyección para recoger devolver todos los
     * valores asociados a los KPIs desde OracleDB
     */
    public void initOracleProjections() {
        LOG.info("Instanciado clases projection para realizar las transacciones a OracleDB");
        gestor_precuentasJpa = new Gestor_PrecuentasJpaImpl(entityManager);
    }

    public void initMongoProjections() {
        LOG.info("Instanciado clases projection para realizar las transacciones a OracleDB");
        scrappingMJD = new ScrappingMJDImpl(scrappingCollection);
    }

    public Gestor_PrecuentasJpa getGestor_precuentasJpa() {
        return gestor_precuentasJpa;
    }

    public ScrappingMJD getScrappingMJD() {
        return scrappingMJD;
    }
}
