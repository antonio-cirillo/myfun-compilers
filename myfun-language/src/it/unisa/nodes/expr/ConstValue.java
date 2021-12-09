package it.unisa.nodes.expr;

import it.unisa.enums.Type;

public class ConstValue extends Expr {

    public ConstValue(Type type, String value) {
        super("(\"" + type + "\", \"" + value + "\")");
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return "(" + type + ", \"" + value + "\")";
    }

    private Type type;
    private String value;

}