package cdb.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import cdb.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class Connector {

    private static final Logger LOGGER = LoggerFactory.getLogger(Connector.class);


    private static Connector connector = new Connector();
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    private DataSource datasource;
    private ThreadLocal<Connection> connections = new ThreadLocal<>();

    public DataSource getDataSource() {
        return datasource;
    }

    /**
     * Constructor preparing Hikari.
     */
    private Connector() {

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

    /**
     * Create a new connection if there no connection currently used.
     *
     * @return connection
     */
    public Connection getConnection() {
        Connection connection = connections.get();
        if (connection == null) {
            try {
                connection = datasource.getConnection();
                connections.set(connection);
            } catch (SQLException e) {
                LOGGER.error("Error Unable to Connect to Database");
                throw new IllegalStateException(e);
            }
        }
        return connection;
    }

    /**
     * Start a transaction.
     */
    public void startTransaction() {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("Error while starting transaction");
            throw new IllegalStateException(e);
        }
    }

    /**
     * Check if current connection is performing a transaction.
     *
     * @return true if transactionnal
     */
    public boolean isTransactional() {
        try {
            return !getConnection().getAutoCommit();
        } catch (SQLException e) {
            LOGGER.error("Error while getting transaction state");
            throw new IllegalStateException(e);
        }
    }

    /**
     * Close current connection.
     */
    public void closeConnection() {
        Connection connection = connections.get();
        if (connection == null) {
            LOGGER.error("Cannot close non-existent transaction");
            throw new IllegalStateException("Cannot close non-existent transaction");
        }
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Error while closing connection");
            throw new IllegalStateException(e);
        } finally {
            connections.remove();
        }
    }

    /**
     * Close current connection if current connection is not a transaction.
     */
    public void closeIfNotTransactionnal() {
        if (!isTransactional()) {
            closeConnection();
        }
    }

    /**
     * Close statetement.
     *
     * @param st closed statement
     * @throws DAOException if error happened during statement closure
     */
    public void closeObjects(Statement st) throws DAOException {
        closeObjects(st, null);
    }

    /**
     * Close result set.
     *
     * @param rs closed result set
     * @throws DAOException if error happened during statement closure
     */
    public void closeObjects(ResultSet rs) throws DAOException {
        closeObjects(null, rs);
    }

    /**
     * Close statement and result set.
     *
     * @param st statement closed
     * @param rs resultset closed.
     * @throws DAOException if error happened during closure or st or rs
     */
    public void closeObjects(Statement st, ResultSet rs) throws DAOException {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
        }
    }

    /**
     * Commit current transaction.
     */
    public void commit() {
        Connection connection = connections.get();
        if (connection == null) {
            LOGGER.error("Cannot commit non-existent transaction");
            throw new IllegalStateException("Cannot commit non-existent transaction");
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("Error while committing transaction");
            throw new IllegalStateException(e);
        }
    }


}
