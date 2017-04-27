package persistence.operator;

/**
 * Created by vkarassouloff on 27/04/17.
 */
public class LikeBoth extends Filter {

    /**
     * Constructor.
     * @param value for the operator Like
     */
    public LikeBoth(String value) {
        super(" LIKE ", "%" + value + "%");
    }

}
