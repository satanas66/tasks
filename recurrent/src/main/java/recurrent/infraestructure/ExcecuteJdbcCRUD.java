package recurrent.infraestructure;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import jdbc.ConnectionJdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcecuteJdbcCRUD {

    private static Logger LOG = Logger.getLogger(ExcecuteJdbcCRUD.class);

   public static void main(String[] arg ){
      query3();
   }

   public static void query1(){
       ConnectionJdbc connectionJdbc = new ConnectionJdbc();
       connectionJdbc.jdbcConnection("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@//10.30.0.149:1530/BDVC1P", "phw_vac", "malaga000");

       String path = "C:/tasks/recurrent/src/main/resources/select/";
       String fileName = "nifycodigoempresa.csv";

       Map<String, String> mapa = Utils.generateMapFromFile(path, fileName, ";");
       List<String> nofound = new ArrayList<>();
       int count = 0;
       PreparedStatement ps;
       try {
           List<String> found = new ArrayList<>();
           int index = 0;
           Connection connection = connectionJdbc.getConnection();
           for (String key : mapa.keySet()) {

               String value = mapa.get(key);
               String query = "select nc_nif, co_empresa, co_cliente, email from f_datos_contacto where nc_nif='" + key + "' and co_empresa='" + value + "'";
               LOG.info("Buscando datos : " + index+ "==>"+query);
               try {
                   ps = connection.prepareStatement(query);
                   ResultSet resultSet = ps.executeQuery();
                   if (resultSet.next()) {
                       String nif = resultSet.getString(1);
                       String co_empresa = resultSet.getString(2);
                       String co_cliente = resultSet.getString(3);
                       String email = resultSet.getString(4);
                       found.add(nif + "," + co_empresa + "," + co_cliente + "," + email);
                   } else {
                       nofound.add(key + "," + value);
                   }
                   index++;
                   count++;
                   if (count == 370) {
                       count = 0;
                       connection.close();
                       Thread.sleep(5 * 1000);
                       connectionJdbc = new ConnectionJdbc();
                       connectionJdbc.jdbcConnection("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@//10.30.0.149:1530/BDVC1P", "phw_vac", "malaga000");
                       connection = connectionJdbc.getConnection();
                   }

               } catch (Exception e) {
                   nofound.add(key + "," + value);
                   connection.close();
                   Thread.sleep(5 * 1000);
                   connectionJdbc = new ConnectionJdbc();
                   connectionJdbc.jdbcConnection("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@//10.30.0.149:1530/BDVC1P", "phw_vac", "malaga000");
                   connection = connectionJdbc.getConnection();
                   LOG.error("Ha ocurrido un error: " + e.getMessage());
               }
           }
           connection.close();
           if (nofound.size() > 0) {
               Text.generateTxtFileWithStrings(nofound, path, "NO_ENCONTRADOS.csv");
           }
           Text.generateTxtFileWithStrings(found, path, "ENCONTRADOS_EMAILS.csv");
       } catch (Exception e) {
           LOG.error("Ha ocurrido un error: " + e.getMessage());
       }
   }

    public static void query2(){
        ConnectionJdbc connectionJdbc = new ConnectionJdbc();
        connectionJdbc.jdbcConnection("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@//10.30.0.149:1530/BDVC1P", "phw_vac", "malaga000");

        String path = "C:/tasks/recurrent/src/main/resources/select/";
        String fileName_ouput = "rechazos_rgpd_clientCode.csv";
        List<Integer> clientCodes = Utils.generateIntegerListFromFile(path, "clientCodes_rgpd.txt");
        int count = 0;
        PreparedStatement ps;
        try {
            List<String> found = new ArrayList<>();
            int index = 0;
            Connection connection = connectionJdbc.getConnection();
            for (Integer clientCode : clientCodes) {
                String query = "select cc_nom_empre, no_comer, cc_pers_cto, direccion, co_post_cto, tx_loca_apa, de_prov from f_datos_contacto where co_cliente="+clientCode;
                LOG.info("Buscando datos : " + index+ "==>"+query);
                try {
                    ps = connection.prepareStatement(query);
                    ResultSet resultSet = ps.executeQuery();
                    if (resultSet.next()) {
                        String cc_nom_empre = resultSet.getString(1);
                        String no_comer = resultSet.getString(2);
                        String cc_pers_cto = resultSet.getString(3);
                        String direccion = resultSet.getString(4);
                        String co_post_cto = resultSet.getString(5);
                        String tx_loca_apa = resultSet.getString(6);
                        String de_prov = resultSet.getString(7);
                        found.add(clientCode+";"+cc_nom_empre + ";" + no_comer + ";" + cc_pers_cto + ";" + direccion+";"+co_post_cto+";"+tx_loca_apa+";"+de_prov);
                    }
                    index++;
                    count++;
                    if (count == 370) {
                        count = 0;
                        connection.close();
                        Thread.sleep(5 * 1000);
                        connectionJdbc = new ConnectionJdbc();
                        connectionJdbc.jdbcConnection("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@//10.30.0.149:1530/BDVC1P", "phw_vac", "malaga000");
                        connection = connectionJdbc.getConnection();
                    }

                } catch (Exception e) {
                    connection.close();
                    Thread.sleep(5 * 1000);
                    connectionJdbc = new ConnectionJdbc();
                    connectionJdbc.jdbcConnection("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@//10.30.0.149:1530/BDVC1P", "phw_vac", "malaga000");
                    connection = connectionJdbc.getConnection();
                    LOG.error("Ha ocurrido un error: " + e.getMessage());
                }
            }
            connection.close();
            Text.generateTxtFileWithStrings(found, path, fileName_ouput);
        } catch (Exception e) {
            LOG.error("Ha ocurrido un error: " + e.getMessage());
        }
    }

    public static void query3(){
        ConnectionJdbc connectionJdbc = new ConnectionJdbc();
        connectionJdbc.jdbcConnection("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@//10.30.0.149:1530/BDVC1P", "phw_vac", "malaga000");

        String path = "C:/tasks/recurrent/src/main/resources/select/";
        String fileName_ouput = "rechazos_rgpd_nif.csv";
        List<String> nifs = Utils.generateListFromFile(path, "nifs_rgpd.txt");
        int count = 0;
        PreparedStatement ps;
        try {
            List<String> found = new ArrayList<>();
            int index = 0;
            Connection connection = connectionJdbc.getConnection();
            for (String nif : nifs) {
                String query = "select cc_nom_empre, no_comer, cc_pers_cto, direccion, co_post_cto, tx_loca_apa, de_prov from f_datos_contacto where nc_nif='"+nif+"'";
                LOG.info("Buscando datos : " + index+ "==>"+query);
                try {
                    ps = connection.prepareStatement(query);
                    ResultSet resultSet = ps.executeQuery();
                    if (resultSet.next()) {
                        String cc_nom_empre = resultSet.getString(1);
                        String no_comer = resultSet.getString(2);
                        String cc_pers_cto = resultSet.getString(3);
                        String direccion = resultSet.getString(4);
                        String co_post_cto = resultSet.getString(5);
                        String tx_loca_apa = resultSet.getString(6);
                        String de_prov = resultSet.getString(7);
                        found.add(nif+";"+cc_nom_empre + ";" + no_comer + ";" + cc_pers_cto + ";" + direccion+";"+co_post_cto+";"+tx_loca_apa+";"+de_prov);
                    }
                    index++;
                    count++;
                    if (count == 370) {
                        count = 0;
                        connection.close();
                        Thread.sleep(5 * 1000);
                        connectionJdbc = new ConnectionJdbc();
                        connectionJdbc.jdbcConnection("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@//10.30.0.149:1530/BDVC1P", "phw_vac", "malaga000");
                        connection = connectionJdbc.getConnection();
                    }

                } catch (Exception e) {
                    connection.close();
                    Thread.sleep(5 * 1000);
                    connectionJdbc = new ConnectionJdbc();
                    connectionJdbc.jdbcConnection("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@//10.30.0.149:1530/BDVC1P", "phw_vac", "malaga000");
                    connection = connectionJdbc.getConnection();
                    LOG.error("Ha ocurrido un error: " + e.getMessage());
                }
            }
            connection.close();
            Text.generateTxtFileWithStrings(found, path, fileName_ouput);
        } catch (Exception e) {
            LOG.error("Ha ocurrido un error: " + e.getMessage());
        }
    }
}
