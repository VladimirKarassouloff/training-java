package services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by vkarassouloff on 28/04/17.
 */
public class TransactionHolder {

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionHolder.class);

    /**
     * Set current connection.
     *
     * @param connection new current
     * @throws SQLException if fails to create save
     */
    public static void set(Connection connection) {
        if (connection != null) {
            /*try {
                connection.setSavepoint();
            } catch (SQLException e) {
                LOGGER.info("Error creating save point");
                throw new RuntimeException("Failed to create save point");
            }*/
        }
        threadLocal.set(connection);
    }

    /**
     * Get current connection.
     *
     * @return connection
     */
    public static Connection get() {
        return threadLocal.get();
    }


}
