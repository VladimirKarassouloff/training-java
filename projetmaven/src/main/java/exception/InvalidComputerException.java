package exception;

/**
 * Created by vkarassouloff on 21/04/17.
 */
public class InvalidComputerException extends Exception {

    /**
     * Constructor.
     */
    public InvalidComputerException() {
    }

    /**
     * Constructor.
     * @param message cause
     */
    public InvalidComputerException(String message) {
        super(message);
    }
}
