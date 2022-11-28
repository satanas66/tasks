package recurrent.infraestructure;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import recurrent.tasks.KpisGMBTasks;

import java.util.ArrayList;
import java.util.List;

public class ExecuteKpisGMB {

    private static Logger LOG = Logger.getLogger(ExecuteKpisGMB.class);

    private static final String PATH = "C:/tasks/recurrent/src/main/resources/select/";

    public static void main(String[] arg) {
        KpisGMBTasks kpisGMBTasks = new KpisGMBTasks();
        List<String> phonesNumber = Utils.generateListFromFile(PATH, "phoneNumbers.txt");
        List<String> values = new ArrayList<>();
        values.add("cliente_ficticio;estado_reg;nombre;direccion_no_normalizada;co_actvad;tx_actvad_no_normalizada;ranking_number;reviews;total_rating;website;claim_business;temporarily_closed_banner;filename");
        int i=1;
        for (String phoneNumber : phonesNumber) {
            LOG.info("Buscando KPIS de GMB para el cliente ficticio: "+phoneNumber);
            String oracle = kpisGMBTasks.getGestor_precuentasJpa().getProjectionByClienteFicticioAndEstadoReg(Integer.parseInt(phoneNumber), "PENDIENTE");
            String mongo = kpisGMBTasks.getScrappingMJD().getGmbKpisByPhoneNumber(phoneNumber);
            values.add(oracle+";"+mongo);
            LOG.info("Se ha concatenado los resultados de oracle y mongo "+i);
            i++;

        }
        kpisGMBTasks.closureOfConnections();
        Text.generateTxtFileWithStrings(values, PATH, "KPIS_GMB.csv");
    }




}
