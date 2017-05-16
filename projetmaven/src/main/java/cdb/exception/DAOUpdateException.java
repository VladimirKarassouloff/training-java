package cdb.exception;

/**
 * Created by vkarassouloff on 19/04/17.
 */
public class DAOUpdateException extends DAOException {

    /**
     * Exception happening while having error for updating object.
     *
     * @param obj object updated
     */
    public DAOUpdateException(Object obj) {
        super("Error while updating object : " + obj);
    }

    /**
     * Exception happening while having error for updating object.
     *
     * @param obj object updated
     * @param msg reason
     */
    public DAOUpdateException(Object obj, String msg) {
        super(msg + " => " + obj);
    }
}
