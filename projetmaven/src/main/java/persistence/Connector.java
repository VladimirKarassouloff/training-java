package persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public final class Connector {

    private static Connector connector = new Connector();
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

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
        HikariConfig config = new HikariConfig("/hikari.properties");

        // External configuration
        try {
            config.setJdbcUrl(InitialContext.doLookup("java:comp/env/jdbc_cdb"));
        } catch (NamingException e) {
            e.printStackTrace();
        }


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
