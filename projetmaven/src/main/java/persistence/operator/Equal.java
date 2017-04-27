package persistence.operator;

/**
 * Created by vkarassouloff on 27/04/17.
 */
public class Equal extends Filter {

    /**
     * Constructor.
     * @param value of the right term
     */
    public Equal(String value) {
        super(" = ", value);
    }

}
