package exception;

/**
 * Created by vkarassouloff on 19/04/17.
 */
public class DAOCountException extends Exception {

    /**
     * DAOException while getting count of a table.
     *
     * @param tableName rows counted
     */
    public DAOCountException(String tableName) {
        super("Error while trying to get row count from " + tableName);
    }
}
