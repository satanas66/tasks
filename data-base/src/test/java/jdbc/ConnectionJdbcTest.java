package jdbc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ConnectionJdbcTest {

    private static  ConnectionJdbc connectionJdbc;

    private Connection connection;

    @BeforeAll
    public static void init(){
        connectionJdbc = new ConnectionJdbc();
    }

    @Test
    public void jdbcConnection() throws SQLException {
        connectionJdbc.jdbcConnection("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@//10.30.0.149:1530/BDVC1P", "phw_vac", "malaga000");
        connection = connectionJdbc.getConnection();
        assertThat(connection.isClosed()).isFalse();
        connection.close();
        assertThat(connection.isClosed()).isTrue();
    }

    @Test
    public void testJdbcConnection() throws SQLException {
        List<String> parameters = Arrays.asList("oracle.jdbc.OracleDriver", "jdbc:oracle:thin", "10.30.0.149", "1530", "BDVC1P", "phw_vac", "malaga000");
        connectionJdbc.jdbcConnection(parameters);
        connection = connectionJdbc.getConnection();
        assertThat(connection.isClosed()).isFalse();
        connection.close();
        assertThat(connection.isClosed()).isTrue();
    }

    @Test
    public void testJdbcConnection1() {
    }

    @Test
    public void testJdbcConnection2() {
    }
}