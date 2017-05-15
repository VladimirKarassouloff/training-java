package persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public final class Connector {

    private static final Logger LOGGER = LoggerFactory.getLogger(Connector.class);


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
            LOGGER.info("Connector : jdbc url not overriden");
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
    public static void close(Connection connection) {
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
