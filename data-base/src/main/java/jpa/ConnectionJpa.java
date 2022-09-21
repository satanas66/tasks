package jpa;

import automation.factory.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Edwin Patricio Arévalo Angulo
 *
 * Clase encargada de realizar las conexiones hacia las distintas bases de datos Oracle y Mysql
 */
public class ConnectionJpa {

    private static Logger LOG = Logger.getLogger(ConnectionJpa.class);

    private final static String ERROR = "Ha ocurrido un error al iniciar la conexión a la base de datos ";

    private final static String PACKAGE = "es.data.master.jpa.persistence.unit.";

    private String PHW_VAC = PACKAGE + "phw_vac";

    private String SISORA = PACKAGE + "sisora";

    private String GESTFORD = PACKAGE + "gestford";

    private String PA = PACKAGE + "pa";

    private String GESTCLI_SISORA = PACKAGE + "gestcli.sisora";

    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    private Map<String, Object> phw_vac_properties;

    private Map<String, Object> sisora_properties;

    private Map<String, Object> gestford_properties;

    private Map<String, Object> pa_properties;

    private Map<String, Object> gestcli_sisora_properties;


    /**
     * Método que realiza la conexión hacia la base de datos PHW_VAC
     * @return EntityManager con la conexión establecida
     */
    public EntityManager getPHWVACEntityManager() {
        try {
            phw_vac_properties = new HashMap<>();
            phw_vac_properties.put("javax.persistence.jdbc.driver", System.getenv().get("DRIVER_ORACLE"));
            phw_vac_properties.put("javax.persistence.jdbc.url", System.getenv().get("PHW_VAC_HOST"));
            phw_vac_properties.put("javax.persistence.jdbc.user", System.getenv().get("PHW_VAC_USER"));
            phw_vac_properties.put("javax.persistence.jdbc.password", System.getenv().get("PHW_VAC_PASSWORD"));

            entityManagerFactory = Persistence.createEntityManagerFactory(PHW_VAC, phw_vac_properties);
            entityManager = entityManagerFactory.createEntityManager();
        } catch (Exception e) {
            LOG.error(ERROR + "BDVC1P: " + e.getMessage());
        }
        return entityManager;
    }

    /**
     * Método que realiza la conexión hacia la base de datos SISORA
     * @return EntityManager con la conexión establecida
     */
    public EntityManager getSisoraEntityManager() {
        try {
            sisora_properties = new HashMap<>();
            sisora_properties.put("javax.persistence.jdbc.driver", System.getenv().get("DRIVER_ORACLE"));
            sisora_properties.put("javax.persistence.jdbc.url", System.getenv().get("SISORA_HOST"));
            sisora_properties.put("javax.persistence.jdbc.user", System.getenv().get("SISORA_USER"));
            sisora_properties.put("javax.persistence.jdbc.password", System.getenv().get("SISORA_PASSWORD"));

            entityManagerFactory = Persistence.createEntityManagerFactory(SISORA, sisora_properties);
            entityManager = entityManagerFactory.createEntityManager();
        } catch (Exception e) {
            LOG.error(ERROR + "BDWH1P: " + e.getMessage());
        }
        return entityManager;
    }

    /**
     * Método que realiza la conexión hacia la base de datos GESTFORD
     * @return EntityManager con la conexión establecida
     */
    public EntityManager getGestfordEntityManager() {
        try {
            gestford_properties = new HashMap<>();
            gestford_properties.put("javax.persistence.jdbc.driver", System.getenv().get("DRIVER_ORACLE"));
            gestford_properties.put("javax.persistence.jdbc.url", System.getenv().get("GESTFORD_HOST"));
            gestford_properties.put("javax.persistence.jdbc.user", System.getenv().get("GESTFORD_USER"));
            gestford_properties.put("javax.persistence.jdbc.password", System.getenv().get("GESTFORD_PASSWORD"));

            entityManagerFactory = Persistence.createEntityManagerFactory(GESTFORD, gestford_properties);
            entityManager = entityManagerFactory.createEntityManager();
        } catch (Exception e) {
            LOG.error(ERROR + "BDWH1P: " + e.getMessage());
        }
        return entityManager;
    }

    /**
     * Método que realiza la conexión hacia la base de datos PA
     * @return EntityManager con la conexión establecida
     */
    public EntityManager getPAEntityManager() {
        try {
            pa_properties = new HashMap<>();
            pa_properties.put("javax.persistence.jdbc.driver", System.getenv().get("DRIVER_MYSQL"));
            pa_properties.put("javax.persistence.jdbc.url", System.getenv().get("PA_HOST"));
            pa_properties.put("javax.persistence.jdbc.user", System.getenv().get("PA_USER"));
            pa_properties.put("javax.persistence.jdbc.password", System.getenv().get("PA_PASSWORD"));

            entityManagerFactory = Persistence.createEntityManagerFactory(PA, pa_properties);
            entityManager = entityManagerFactory.createEntityManager();
        } catch (Exception e) {
            LOG.error(ERROR + "MySQL: " + e.getMessage());
        }
        return entityManager;
    }

    /**
     * Método que realiza la conexión hacia la base de datos SISORA
     * @return EntityManager con la conexión establecida
     */
    public EntityManager getGestcliSisoraEntityManager() {
        try {
            gestcli_sisora_properties = new HashMap<>();
            gestcli_sisora_properties.put("javax.persistence.jdbc.driver", System.getenv().get("DRIVER_ORACLE"));
            gestcli_sisora_properties.put("javax.persistence.jdbc.url", System.getenv().get("GESTCLI_SISORA_HOST"));
            gestcli_sisora_properties.put("javax.persistence.jdbc.user", System.getenv().get("GESTCLI_SISORA_USER"));
            gestcli_sisora_properties.put("javax.persistence.jdbc.password", System.getenv().get("GESTCLI_SISORA_PASSWORD"));

            entityManagerFactory = Persistence.createEntityManagerFactory(GESTCLI_SISORA, gestcli_sisora_properties);
            entityManager = entityManagerFactory.createEntityManager();
        } catch (Exception e) {
            LOG.error(ERROR + "BDCL1P: " + e.getMessage());
        }
        return entityManager;
    }

    public String getPHW_VAC() {
        return PHW_VAC;
    }

    public String getSISORA() {
        return SISORA;
    }

    public String getGESTFORD() {
        return GESTFORD;
    }

    public String getPA() {
        return PA;
    }

    public String getGESTCLI_SISORA() {
        return GESTCLI_SISORA;
    }
}
