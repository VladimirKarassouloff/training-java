package cdb.exception;

import java.util.List;

/**
 * Created by vkarassouloff on 19/04/17.
 */
public class DAODeleteException extends DAOException {

    /**
     * Exception happening while trying to delete object from databse.
     *
     * @param tableName table
     * @param id        of object deleted
     */
    public DAODeleteException(String tableName, int id) {
        super("Error while deleting object from " + tableName + " where id = " + id);
    }

    /**
     * Exception happening while trying to delete object from databse.
     *
     * @param tableName table
     * @param query     execute
     */
    public DAODeleteException(String tableName, String query) {
        super("Error while deleting object from " + tableName + " (query was : " + query + ")");
    }

    /**
     * Exception happening while trying to delete object from databse.
     *
     * @param tableName reason
     * @param ids       of object deleted
     */
    public DAODeleteException(String tableName, List<Integer> ids) {
        super("Error while deleting object from " + tableName + " where id = " + ids);
    }
}
