package mercado.potencial.application;

import automation.factory.Logger;
import com.mongodb.DBCollection;
import mercado.potencial.mongo.biRecomendador.Bi_RecomendatorProjection;
import mongo.ConnectionMongo;

public class PaqueteRecomendado {

    private static Logger LOG = Logger.getLogger(PaqueteRecomendado.class);

    protected String PATH = "C:/tasks/mercado-potencial-mongo/src/main/resources/";

    private ConnectionMongo connectionMongo;

    private DBCollection bi_RecomendatorCollection;

    private Bi_RecomendatorProjection bi_recomendatorProjection;

    public PaqueteRecomendado() {
        noRelationalDBStart();
        initMongoProjections();
    }

    /**
     * Método que establece la conexión a la Base de datos MONGO
     */
    public void noRelationalDBStart() {
        LOG.info("Abriendo conexión a MONGODB");
        connectionMongo = new ConnectionMongo();
        if (connectionMongo.getMongoClient() != null) {
            bi_RecomendatorCollection = connectionMongo.getDBCollection("bi_Recomendador");
            LOG.info("Conexión establecida con MONGODB");
        }
    }

    /**
     * Método que cierra las conexiones a MONGO, ORACLE y MySql
     */
    public void closureOfConnections() {
        LOG.info("Cerrando conexiones a MongoDB y OracleDB");
        if (connectionMongo != null) {
            connectionMongo.endConnection();
            LOG.info("Las conexiones a OracleDB, MySQL y MongoDB han sido cerradas");
        }
    }

    /**
     * Método que instancia las colecciones para recoger, evaluar y devolver todos los
     * valores asociados a los KPIs desde MongoDB
     */
    public void initMongoProjections() {
        LOG.info("Instanciado clases projection para realizar las consultas a MongoDB");
        bi_recomendatorProjection = new Bi_RecomendatorProjection(bi_RecomendatorCollection);
    }

    public Bi_RecomendatorProjection getBi_recomendatorProjection() {
        return bi_recomendatorProjection;
    }
}
