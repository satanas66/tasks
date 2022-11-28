package potential.market.application;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import jpa.ConnectionJpa;
import potential.market.jpa.phw_vac.F_Datos_ContactoProjection;
import potential.market.jpa.sisora.Tsi_ActvadProjection;
import potential.market.jpa.sisora.Tsi_F_WebProjection;
import potential.market.jpa.sisora.Tsi_SectorProjection;

import javax.persistence.EntityManager;
import java.io.File;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 */
public class MercadoNoWebAnalytics {

    private static Logger LOG = Logger.getLogger(MercadoNoWebAnalytics.class);

    protected String PATH = "C:/tasks/mercado-no-web-analytics/src/main/resources/";

    protected String FILENAME_MERCADO_POTENCIAL = "MERCADO_NO_WEB_ANALYTICS_";

    protected String EXTENSION = ".xlsx";

    private boolean continuaProceso = true;

    private ConnectionJpa connectionJpa;

    private EntityManager entityManagerPHWVAC;

    private EntityManager entityManagerSISORA;

    private F_Datos_ContactoProjection f_datos_contactoProjection;

    private Tsi_ActvadProjection tsi_actvadProjection;

    private Tsi_F_WebProjection tsi_f_webProjection;

    private Tsi_SectorProjection tsi_sectorProjection;

    public MercadoNoWebAnalytics() {
        relationalDBStart();
        initOracleProjections();
    }

    /**
     * Método que establece las conexiones a las Bases de datos relacionales Oracle y MySql
     */
    public void relationalDBStart() {
        if (continuaProceso) {
            LOG.info("Abriendo conexiones a OracleDB");
            connectionJpa = new ConnectionJpa();
            entityManagerPHWVAC = connectionJpa.getPHWVACEntityManager();
            entityManagerSISORA = connectionJpa.getSisoraEntityManager();
            if (entityManagerPHWVAC == null || !entityManagerPHWVAC.isOpen() ||
                    entityManagerSISORA == null || !entityManagerSISORA.isOpen()) {
                continuaProceso = false;
            }
            LOG.info("Conexiones establecidas a OracleDB");
        }
    }

    /**
     * Método que cierra las conexiones a MONGO, ORACLE y MySql
     */
    public void closureOfConnections() {
        LOG.info("Cerrando conexiones a OracleDB");
        if (entityManagerPHWVAC != null && entityManagerPHWVAC.isOpen()) {
            entityManagerPHWVAC.close();
        }
        if (entityManagerSISORA != null && entityManagerSISORA.isOpen()) {
            entityManagerSISORA.close();
        }
        LOG.info("Las conexiones a OracleDB han sido cerradas");
    }

    /**
     * Método que instancia las clases de proyección para recoger devolver todos los
     * valores asociados a los KPIs desde OracleDB
     */
    public void initOracleProjections() {
        LOG.info("Instanciado clases projection para realizar las consultas a OracleDB");
        f_datos_contactoProjection = new F_Datos_ContactoProjection(entityManagerPHWVAC);
        tsi_actvadProjection = new Tsi_ActvadProjection(entityManagerSISORA);
        tsi_f_webProjection = new Tsi_F_WebProjection(entityManagerSISORA);
        tsi_sectorProjection = new Tsi_SectorProjection(entityManagerSISORA);
    }

    public F_Datos_ContactoProjection getF_datos_contactoProjection() {
        return f_datos_contactoProjection;
    }

    public Tsi_ActvadProjection getTsi_actvadProjection() {
        return tsi_actvadProjection;
    }

    public Tsi_F_WebProjection getTsi_f_webProjection() {
        return tsi_f_webProjection;
    }

    public Tsi_SectorProjection getTsi_sectorProjection() {
        return tsi_sectorProjection;
    }

    public boolean isContinuaProceso() {
        return continuaProceso;
    }

    /**
     * Método que genera una Lista de lista de códigos de clientes
     *
     * @param numberOfDivisions
     * @return lista de lista de códigos
     */
    public List<List<Integer>> listsForExecutionByThreads(int numberOfDivisions) {
        File file = new File(PATH+ "20220916.txt");
        if(!file.exists()){
            return null;
        }
        file = new File(PATH, "bdcClientCodes.txt");
        if(!file.exists()){
            Text.generateTxtFile(f_datos_contactoProjection.findAllClientCodes(), PATH, "bdcClientCodes.txt");
        }
        List<Integer> bdcClientCodes = Utils.generateIntegerListFromFile(PATH, "bdcClientCodes.txt");//1386085
        List<Integer> potentialClientCodes = Utils.generateIntegerListFromFile(PATH, "20220916.txt");//673369
        List<Integer> clientCodes = Utils.getDisjunctionFromLists(bdcClientCodes, potentialClientCodes);//814174
        return Utils.getListDivision(clientCodes, numberOfDivisions);
    }
}
