package cdb.exception;

/**
 * Created by vkarassouloff on 19/04/17.
 */
public class DAOException extends Exception {

    /**
     * Base cdb.exception for all DAOException.
     */
    public DAOException() {
        super("Exception base de donnée");
    }

    /**
     * Base cdb.exception for all DAOException.
     * @param msg excption message
     */
    public DAOException(String msg) {
        super(msg);
    }

}
