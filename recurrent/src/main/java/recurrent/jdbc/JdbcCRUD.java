package recurrent.jdbc;

import automation.factory.Logger;
import automation.factory.Utils;
import automation.factory.txt.Text;
import jdbc.ConnectionJdbc;
import recurrent.infraestructure.ExecuteGestorPrecuentas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JdbcCRUD {

    private static Logger LOG = Logger.getLogger(ExecuteGestorPrecuentas.class);

    private ConnectionJdbc connectionJdbc;

    private Connection connection;

    public JdbcCRUD(ConnectionJdbc connectionJdbc) {
        this.connectionJdbc = connectionJdbc;
    }

    public void updatePrecuentasMasiva(String path, String fileName) {
        Map<String, String> mapa = Utils.generateMapFromFile(path, fileName, ";");
        List<String> errores = new ArrayList<>();
        int count = 0;
        PreparedStatement ps;
        try {
            LOG.info("Actualizando datos");
            int index = 0;
            Connection connection = connectionJdbc.getConnection();
            for (String key : mapa.keySet()) {
                String value = mapa.get(key);
                String line = "update gestor_precuentas set NOMBRE = '" + value + "' where cliente_ficticio = " + key;
                try {
                    ps = connection.prepareStatement(line);
                    ps.executeUpdate();
                    count++;
                    index++;
                    LOG.info("Se ha actualizado la línea: " + index + " => " + line);
                    if (count == 370) {
                        count = 0;
                        connection.close();
                        Thread.sleep(5 * 1000);
                        connection = connectionJdbc.getConnection();
                    }
                } catch (Exception e) {
                    errores.add(key + ";" + value);
                    count++;
                    if (count == 370) {
                        count = 0;
                        connection.close();
                        Thread.sleep(5 * 1000);
                        connection = connectionJdbc.getConnection();
                    }
                    LOG.error("Ha ocurrido un error: " + e.getMessage());
                }
            }
            connection.close();
            if (errores.size() > 0) {
                Text.generateTxtFileWithStrings(errores, path, "errores_" + path);
            }
        } catch (Exception e) {
            LOG.error("Ha ocurrido un error: " + e.getMessage());
        }
    }

    public void selectEmail(String path, String fileName) {
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
                } catch (Exception e) {
                    nofound.add(key + "," + value);
                    Text.generateTxtFileWithStrings(found, path, "ENCONTRADOS_EMAILS.csv");
                    connection.close();
                    Thread.sleep(5 * 1000);
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
}
