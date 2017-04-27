package persistence.operator;

/**
 * Created by vkarassouloff on 27/04/17.
 */
public abstract class Operator {

    protected String operator;
    protected String value;

    public Operator(String operator, String value) {
        this.operator = operator;
        this.value = value;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String boundValue() {
        return value;
    }
}
