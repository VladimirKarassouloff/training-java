package cdb.exception;

/**
 * Created by vkarassouloff on 20/04/17.
 */
public class FormException extends Exception {

    /**
     * Constructor.
     */
    public FormException() {
        super("Form Exception");
    }

    /**
     * Constructor.
     * @param msg string error
     */
    public FormException(String msg) {
        super(msg);
    }

}
