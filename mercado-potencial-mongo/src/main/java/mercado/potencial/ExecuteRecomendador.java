package mercado.potencial;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import mercado.potencial.application.MercadoPotencial;
import mercado.potencial.application.MercadoPotencialThread;
import mercado.potencial.application.PaqueteRecomendado;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Edwin Patricio Arévalo Angulo
 */
public class ExecuteRecomendador {

    private static Logger LOG = Logger.getLogger(ExecuteRecomendador.class);

    public static void main(String[] arg) {
        String path = "C:/tasks/mercado-potencial-mongo/src/main/resources/";
        PaqueteRecomendado paqueteRecomendado = new PaqueteRecomendado();
        List<Integer> clientCodes = Utils.generateIntegerListFromFile(path, "clientCodes.txt");
        List<String> result = new ArrayList<>();
        for(Integer clientCode : clientCodes){
            LOG.info("Buscando paquete recomendado para el código: "+clientCode);
            String recomendado = paqueteRecomendado.getBi_recomendatorProjection().findPaqueteRecomendadoByClientCode(clientCode);
            result.add(clientCode+";"+recomendado);
        }
        Text.generateTxtFileWithStrings(result, path, "paqueteRecomendado.csv");

    }


}
