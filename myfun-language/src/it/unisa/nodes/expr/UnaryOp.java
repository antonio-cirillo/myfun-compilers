package it.unisa.nodes.expr;

import it.unisa.visitors.Visitor;

public class UnaryOp extends Expr {

    public UnaryOp(String nameOp, Expr expr) {
        super(nameOp);
        this.expr = expr;
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public Expr getExpr() {
        return expr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return super.toString();
    }

    private Expr expr;
    private String type;

}