package persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public final class Connector {

    // Config
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost/computer-database-db?useSSL=false";
    private static final String USER = "admincdb";
    private static final String PASS = "qwerty1234";


    private static Connector connector = new Connector();

    private DataSource datasource;

    public DataSource getDataSource() {
        return datasource;
    }

    /**
     * Constructor preparing Hikari.
     */
    private Connector() {

        try {
            Class.forName(Connector.JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Impossible de charger jdbc driver");
        }

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(Connector.DB_URL);
        config.setUsername(Connector.USER);
        config.setPassword(Connector.PASS);

        config.setMaximumPoolSize(40);
        config.setAutoCommit(false);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        datasource = new HikariDataSource(config);
    }

    public static Connector getInstance() {
        return connector;
    }

    /**
     * Try rollbacking.
     *
     * @param connection you are trying to rollack
     */
    public void rollback(Connection connection) {
        if (connection == null) {
            return;
        }

        try {
            connection.rollback();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Close the connection.
     *
     * @param connection you are trying to close
     */
    public void close(Connection connection) {
        if (connection == null) {
            return;
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
