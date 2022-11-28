package jdbc;

import automation.factory.Logger;
import jpa.ConnectionJpa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;

public class ConnectionJdbc {

    private static Logger LOG = Logger.getLogger(ConnectionJpa.class);

    private final static String ERROR = "Ha ocurrido un error al iniciar la conexión a la base de datos ";

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void jdbcConnection(String driver, String dbType, String host, String port, String schema, String user, String password) {
        try {
            LOG.info("Abriendo conexión JDBC a " + dbType + "...");
            Class.forName(driver).newInstance();
            this.connection = DriverManager.getConnection(dbType + ":@//" + host + ":" + port + "/" + schema, user, password);
            LOG.info("Se ha establecido la conexión JDBC " + dbType + " correctamente...");
        } catch (Exception e) {
            LOG.error(ERROR + e.getMessage());
        }
    }

    public void jdbcConnection(String driver, String url, String user, String password) {
        try {
            LOG.info("Abriendo conexión JDBC a " + url + "...");
            Class.forName(driver).newInstance();
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            LOG.error("Ha ocurrido un error creando la conexión: " + e.getMessage());
        }
    }

    public void jdbcConnection(List<String> parameters) {
        try {
            LOG.info("Abriendo conexión JDBC a " + parameters.get(1) + "...");
            Class.forName(parameters.get(0)).newInstance();
            this.connection = DriverManager.getConnection(parameters.get(1) + ":@//" + parameters.get(2) + ":" + parameters.get(3) + "/" + parameters.get(4), parameters.get(5), parameters.get(6));
            LOG.info("Se ha establecido la conexión JDBC " + parameters.get(1) + " correctamente...");
        } catch (Exception e) {
            LOG.error(ERROR + e.getMessage());
        }
    }

    public void jdbcConnection(Map<String, String> parameters) {
        try {
            LOG.info("Abriendo conexión JDBC a " + parameters.get("dbType") + "...");
            Class.forName(parameters.get("driver")).newInstance();
            this.connection = DriverManager.getConnection(parameters.get("dbType") + ":@//" + parameters.get("host") + ":" + parameters.get("port") + "/" + parameters.get("schema"), parameters.get("user"), parameters.get("password"));
            LOG.info("Se ha establecido la conexión JDBC " + parameters.get("dbType") + " correctamente...");
        } catch (Exception e) {
            LOG.error(ERROR + e.getMessage());
        }
    }
}
