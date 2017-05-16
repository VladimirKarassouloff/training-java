package cdb.exception;

/**
 * Created by vkarassouloff on 19/04/17.
 */
public class DAOInsertException extends DAOException {

    /**
     * Exception happening while having error for inserting object.
     * @param obj object inserted
     */
    public DAOInsertException(Object obj) {
        super("Error while inserting : " + obj);
    }

    /**
     * Exception happening while having error for inserting object.
     *
     * @param obj object inserted
     * @param msg reason
     */
    public DAOInsertException(String msg, Object obj) {
        super(msg + " => " + obj);
    }
}
