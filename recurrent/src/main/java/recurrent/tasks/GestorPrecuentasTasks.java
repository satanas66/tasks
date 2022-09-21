package recurrent.tasks;

import automation.factory.Logger;
import jpa.ConnectionJpa;
import recurrent.jpa.phw_vac.Gestor_PrecuentasJpa;
import recurrent.jpa.phw_vac.Gestor_PrecuentasJpaImpl;

import javax.persistence.EntityManager;

public class GestorPrecuentasTasks {

    private static Logger LOG = Logger.getLogger(GestorPrecuentasTasks.class);

    private ConnectionJpa connectionJpa;

    private EntityManager entityManager;

    private Gestor_PrecuentasJpa gestor_precuentasJpa;

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

    /**
     * Método que instancia las clases de proyección para recoger devolver todos los
     * valores asociados a los KPIs desde OracleDB
     */
    public void initOracleProjections() {
        LOG.info("Instanciado clases projection para realizar las transacciones a OracleDB");
        gestor_precuentasJpa = new Gestor_PrecuentasJpaImpl(entityManager);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Gestor_PrecuentasJpa getGestor_precuentasJpa() {
        return gestor_precuentasJpa;
    }

    //    public void findAllGestorPrecuentasByFile(){
//        List<String> lines = Utils.generateListFromFile(PATH, "gestor_precuentas.csv");
//        List<String> resultados = new ArrayList<>();
//        for (int i = 0; i < lines.size(); i++) {
//            String line = lines.get(i);
//            GestorPrecuentasMapper mapper = new GestorPrecuentasMapper();
//            GestorPrecuentas gestorPrecuentas = mapper.getGestorPrecuentasFromString(line);
//            Gestor_Precuentas gestor_precuentas = gestor_precuentasJpa.findByByClienteFicticioAndEstadoReg(gestorPrecuentas.getClienteFicticio(), gestorPrecuentas.getEstadoRegCurrent());
//            if(gestor_precuentas != null){
//                String resultado = gestor_precuentas.getEstadoReg()+";"+gestor_precuentas.getClienteFicticio()+";"+gestor_precuentas.getFeInicio()+";"+gestor_precuentas.getFeModiReg()+";"+gestor_precuentas.getTxObservacion()+";"+gestor_precuentas.getTxError();
//                resultados.add(resultado);
//            }
//        }
//        Text.generateTxtFileWithStrings(resultados, PATH, "consulta.csv");
//    }

}
