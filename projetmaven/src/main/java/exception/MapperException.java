package exception;

/**
 * Created by vkarassouloff on 21/04/17.
 */
public class MapperException extends Exception {

    /**
     * Constructor.
     */
    public MapperException() {
    }

    /**
     * Constructor.
     * @param message cause
     */
    public MapperException(String message) {
        super(message);
    }
}
