package persistence.operator;

/**
 * Created by vkarassouloff on 27/04/17.
 */
public class LikeBoth extends Operator {

    public LikeBoth(String value) {
        super(" LIKE ","%"+value+"%");
    }

}
