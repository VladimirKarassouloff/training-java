package exception;

/**
 * Created by vkarassouloff on 19/04/17.
 */
public class DAOSelectException extends DAOException {

    /**
     * DAOException generated while trying to get objects.
     */
    public DAOSelectException() {
        super("Error while trying to select objects from database");
    }

    /**
     * DAOException generated while trying to get objects.
     *
     * @param tableName table where objects are selected
     * @param query     used
     */
    public DAOSelectException(String tableName, String query) {
        super("Error while trying to select objects from " + tableName + " query : " + query);
    }
}
