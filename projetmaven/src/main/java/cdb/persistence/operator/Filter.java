package cdb.persistence.operator;

/**
 * Created by vkarassouloff on 27/04/17.
 */
public abstract class Filter {

    protected String operator;
    protected String value;

    /**
     * Constructor for building query parts.
     * @param operator Like / = / ....
     * @param value 'Apple' / ...
     */
    public Filter(String operator, String value) {
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


}
