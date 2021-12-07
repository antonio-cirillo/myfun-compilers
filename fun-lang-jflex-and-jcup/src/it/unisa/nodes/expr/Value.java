package it.unisa.nodes.expr;

public class Value extends Expr {

    public Value(String type, String value) {
        super("(" + type + ", \"" + value + "\")");
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return "(" + type + ", \"" + value + "\")";
    }

    private String type, value;

}
