package indices;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import indices.application.DigitalPresenceIndex;
import indices.infraestructure.RequestAndrepsonsesToMarketgoo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExecuteIndice {

    private static Logger LOG = Logger.getLogger(RequestAndrepsonsesToMarketgoo.class);

    private static String PATH = "C:/tasks/indices/src/main/resources/";

    public static void main(String[] arg) {
//        first();
        second();
//        sendRequestToMarketgoo();
    }

    public static void first() {
        DigitalPresenceIndex digitalPresenceIndex = new DigitalPresenceIndex();
        Map<String, String> mapa = digitalPresenceIndex.findAllDigitalPresenceIndex();
        List<String> kpis = new ArrayList<>(mapa.values());
        Text.generateTxtFileWithStrings(kpis, PATH, "ONLY_kpis_indice_presencia_digital.csv");
        List<String> digitalPresenceReport = digitalPresenceIndex.getAllValuesToDigitalPresenceIndex(mapa);
        Text.generateTxtFileWithStrings(digitalPresenceReport, PATH, "kpis_indice_presencia_digital.csv");
        digitalPresenceIndex.closureOfConnections();
    }

    public static void second() {
        DigitalPresenceIndex digitalPresenceIndex = new DigitalPresenceIndex();
        Map<String, String> mapa = digitalPresenceIndex.findOneToOneDigitalPresenceIndex();
        List<String> kpis = new ArrayList<>(mapa.values());
        Text.generateTxtFileWithStrings(kpis, PATH, "SOLO_kpis_indice_presencia_digital.csv");
    }

    public static void sendRequestToMarketgoo(){
        Map<String, String> mapa = Utils.generateMapFromFile(PATH, "Hilo1_client_codes_failed.csv", ";");
        RequestAndrepsonsesToMarketgoo requestAndrepsonsesToMarketgoo = new RequestAndrepsonsesToMarketgoo();
        List<String> errors = new ArrayList<>();
        for(String identifier : mapa.keySet()){
            boolean result = requestAndrepsonsesToMarketgoo.updateCustomerInformation(1, identifier, mapa.get(identifier));
            if(!result){
                errors.add(identifier+";"+mapa.get(identifier));
            }
        }
        Text.generateTxtFileWithStrings(errors, PATH, "ERROR_MARKETGOO_NO_ACTUALIZADOS_1.csv");
    }








}
