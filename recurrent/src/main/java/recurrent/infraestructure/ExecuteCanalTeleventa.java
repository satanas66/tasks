package recurrent.infraestructure;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import recurrent.tasks.CancelacionesTeleventaTasks;

import java.util.ArrayList;
import java.util.List;

public class ExecuteCanalTeleventa {

    private static Logger LOG = Logger.getLogger(ExecuteCanalTeleventa.class);

    private static final String PATH = "C:/tasks/recurrent/src/main/resources/select/";

    public static void main(String[] args) {
        selectCancelaciones();
    }

    public static void selectCancelaciones(){
        LOG.info("Empieza la búsqueda de datos...");
        List<Integer> clientCodes = Utils.generateIntegerListFromFile(PATH, "clientCodes.txt");
        CancelacionesTeleventaTasks cancelacionesTeleventaTasks = new CancelacionesTeleventaTasks();
        List<String> result = cancelacionesTeleventaTasks.searchInformationToTelesalesChannel(clientCodes);
        Text.generateTxtFileWithStrings(result, PATH, "resultados.csv");
        cancelacionesTeleventaTasks.closureOfConnections();
        LOG.info("Fin de la búsqueda de datos");
    }

    public static void selectIdAccount(){
        CancelacionesTeleventaTasks cancelacionesTeleventaTasks = new CancelacionesTeleventaTasks();
        List<Integer> clientCodes = Utils.generateIntegerListFromFile(PATH, "clientCodes.txt");
        List<String> result = new ArrayList<>();
        result.add("co_cliente;id_account");
        int i=0;
        for(Integer clientCode : clientCodes){
            LOG.info("Buscando información para el cliente: "+clientCode);
            String idAccount = cancelacionesTeleventaTasks.getIdAccountByClientCode(clientCode);
            result.add(clientCode+";"+idAccount);
            LOG.info("Tupla => "+i);
            i++;
        }
        Text.generateTxtFileWithStrings(result, PATH, "CO_CLIENTE_ID_ACCOUNT.csv");
        cancelacionesTeleventaTasks.closureOfConnections();
    }
}
