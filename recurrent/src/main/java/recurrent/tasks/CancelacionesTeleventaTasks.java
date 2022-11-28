package recurrent.tasks;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import jpa.ConnectionJpa;
import recurrent.domain.CancelacionesTeleventaJpa;
import recurrent.application.CancelacionesTeleventaJpaImpl;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class CancelacionesTeleventaTasks {

    private static Logger LOG = Logger.getLogger(CancelacionesTeleventaTasks.class);

    private static ConnectionJpa connectionJpa;

    private EntityManager entityManagerPHW_VAC;

    private EntityManager entityManagerSISORA;

    private EntityManager entityManagerGESTFORD;

    private EntityManager entityManagerPA;

    private CancelacionesTeleventaJpa cancelacionesTeleventaJpa;

    public CancelacionesTeleventaTasks(){
        if(relationalDBStart()){
            cancelacionesTeleventaJpa = new CancelacionesTeleventaJpaImpl(entityManagerPHW_VAC, entityManagerSISORA, entityManagerGESTFORD, entityManagerPA);
        }
    }


    /**
     * Método que establece las conexiones a las Bases de datos relacionales Oracle y MySql
     */
    public boolean relationalDBStart() {
        LOG.info("Abriendo conexiones a Oracle y MySQL");
        connectionJpa = new ConnectionJpa();
        entityManagerPHW_VAC = connectionJpa.getPHWVACEntityManager();
        if(entityManagerPHW_VAC != null){
            entityManagerSISORA = connectionJpa.getSisoraEntityManager();
            if(entityManagerSISORA != null){
                entityManagerGESTFORD = connectionJpa.getGestfordEntityManager();
                if(entityManagerGESTFORD != null){
                    entityManagerPA = connectionJpa.getPAEntityManager();
                    LOG.info("Conexiones establecidas a Oracle y MySQL");
                }
            }
        }
        return (entityManagerPA!=null)?true:false;
    }

    /**
     * Método que cierra las conexiones ORACLE y MySql
     */
    public void closureOfConnections() {
        LOG.info("Cerrando conexiones a OracleDB");
        if (entityManagerPHW_VAC != null && entityManagerPHW_VAC.isOpen()) {
            entityManagerPHW_VAC.close();
        }
        if (entityManagerSISORA != null && entityManagerSISORA.isOpen()) {
            entityManagerSISORA.close();
        }
        if (entityManagerGESTFORD != null && entityManagerGESTFORD.isOpen()) {
            entityManagerGESTFORD.close();
        }
        if (entityManagerPA != null && entityManagerPA.isOpen()) {
            entityManagerPA.close();
        }
        LOG.info("Las conexiones a OracleDB, MySQL y MongoDB han sido cerradas");
    }

    public List<String> searchInformationToTelesalesChannel(List<Integer> clientCodes){
        List<String> resultados = new ArrayList<>();
        resultados.add("CO_CLIENTE;ID_ACCOUNT;CO_AMBI_COMER;TX_ACTVAD;CO_SECTOR;TX_SECTOR;CO_CCAA;TX_CCAA;ALIANZA;IMPAGO;VIVO;ULTIMA_OPORTUNIDAD");
        for(Integer clientCode : clientCodes){
            String values = String.valueOf(clientCode);
            String idAccount = cancelacionesTeleventaJpa.getIdAccountByClientCode(clientCode);
            values=values+";"+idAccount;
            Object[] projection = cancelacionesTeleventaJpa.getProjectionFromF_Datos_ConctoByClientCode(clientCode);
            String sectorCode = "";
            String sectorText = "";
            if(projection != null && projection[2] != null){
                sectorCode = cancelacionesTeleventaJpa.getSectorCodeByActivityCode((Integer) projection[2]);
                if(Utils.evaluateString(sectorCode)){
                    sectorText = cancelacionesTeleventaJpa.getSectorTextBySectorCode(sectorCode);
                }
            }
            String coAmbiComer = "";
            String ccaa = "";
            String txcaa = "";
            String txactvad = "";
            if(projection != null ){
                if(projection[0] != null){
                    ccaa = (String) projection[0];
                }
                if(projection[1] != null){
                    txcaa = (String) projection[1];
                }
                if(projection[3] != null){
                    txactvad = (String) projection[3];//texto actividad
                }
                if(projection[4] != null){
                    coAmbiComer = (String) projection[3];//Provincia
                }
            }
            values = values+";"+coAmbiComer+";"+txactvad+";"+sectorCode+";"+sectorText+";"+ccaa+";"+txcaa;
            String alianza = cancelacionesTeleventaJpa.getAlianzaByClientCode(clientCode);
            String alianzaEvaluada = Utils.evaluateString(alianza)?alianza:"";
            values=values+";"+alianzaEvaluada;
            String pay = cancelacionesTeleventaJpa.getStatusPayByClientCode(clientCode);
            String payEvaluada = Utils.evaluateString(pay)?pay:"";
            values=values+";"+payEvaluada;
            String live = cancelacionesTeleventaJpa.getLiveClientByClientCode(clientCode)!=null?"SI":"NO";
            values = values+";"+live;
            String lastChance = cancelacionesTeleventaJpa.lastChanceWinByClientCode(clientCode);
            values = values+";"+lastChance;
            resultados.add(values);
        }
        return resultados;
    }

    public String getIdAccountByClientCode(Integer clientCode){
        return cancelacionesTeleventaJpa.getIdAccountByClientCode(clientCode);
    }
}
