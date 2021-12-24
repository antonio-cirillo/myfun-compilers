package it.unisa.nodes.expr;

import it.unisa.visitors.Visitor;

public class ConstValue extends Expr {

    public ConstValue(String type, String value) {
        super("(\"" + type + "\", \"" + value + "\")");
        this.type = type;
        this.value = value;
    }

    @Override
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

    private String type;
    private String value;

}