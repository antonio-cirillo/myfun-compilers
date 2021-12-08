package it.unisa.nodes.expr;

import it.unisa.visitors.scoping.Visitor;

public class Value extends Expr {

    public Value(String type, String value) {
        super("(" + type + ", \"" + value + "\")");
        this.type = type;
        this.value = value;
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
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