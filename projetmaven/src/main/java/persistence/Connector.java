package persistence;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.SQLException;

public final class Connector {

    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/computer-database-db?useSSL=false";

    private static final String USER = "admincdb";
    private static final String PASS = "qwerty1234";

    private static Connector connector = null;
    private Connection connection = null;

    /**
     * Private constructor for singleton.
     */
    private Connector() {
        try {
            Class.forName(Connector.JDBC_DRIVER);
            connection = (Connection) DriverManager.getConnection(Connector.DB_URL, Connector.USER, Connector.PASS);
        } catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Get instance singleton.
     * @return Connector
     */
    public static Connector getInstance() {
        if (connector == null) {
            connector = new Connector();
        }
        return connector;
    }

    /**
     * Get the mysql connection.
     * @return Connection
     * @throws ClassNotFoundException ?
     * @throws SQLException ?
     */
    public Connection getDBConnection() throws ClassNotFoundException, SQLException {
        return connection;
    }

    /**
     * Prepare a statement with the mysql connection.
     * @param sql string query
     * @return preparedstatement
     */
    public PreparedStatement preparedStatement(String sql) {
        try {
            return (PreparedStatement) connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Prepare a statement with the mysql connection.
     * @param sql string query
     * @param autoGeneratedKeys arg for prepared statement
     * @return prepared statement
     */
    public PreparedStatement preparedStatement(String sql, int autoGeneratedKeys) {
        try {
            return (PreparedStatement) connection.prepareStatement(sql, autoGeneratedKeys);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
