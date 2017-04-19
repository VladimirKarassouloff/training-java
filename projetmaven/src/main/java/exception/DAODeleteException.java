package exception;

/**
 * Created by vkarassouloff on 19/04/17.
 */
public class DAODeleteException extends DAOException {

    /**
     * Exception happening while trying to delete object from databse.
     *
     * @param tableName reason
     * @param id        of object deleted
     */
    public DAODeleteException(String tableName, int id) {
        super("Error while deleting object from " + tableName + " where id = " + id);
    }
}
