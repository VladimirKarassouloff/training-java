package cdb.persistence.operator;

/**
 * Created by vkarassouloff on 16/05/17.
 */
public class LikeRight extends Filter {

    /**
     * Constructor.
     * @param value for the operator Like
     */
    public LikeRight(String value) {
        super(" LIKE ", value + "%");
    }

}
