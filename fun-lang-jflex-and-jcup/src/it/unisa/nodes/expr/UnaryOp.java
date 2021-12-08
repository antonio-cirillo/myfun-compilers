package it.unisa.nodes.expr;

import it.unisa.visitors.scoping.Visitor;

public class UnaryOp extends Expr {

    public UnaryOp(String nameOp, Expr e) {
        super(nameOp);
        super.add(e);
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public String getNameOp() {
        return super.toString();
    }

    public Expr getExpr() {
        return (Expr) super.getChildAt(0);
    }

    public String toString() {
        return super.toString();
    }

}