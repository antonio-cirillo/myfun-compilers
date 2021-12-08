package it.unisa.nodes.expr;

import it.unisa.visitors.scoping.Visitor;

public class BinaryOp extends Expr {

    public BinaryOp(String nameOp, Expr e1, Expr e2) {
        super(nameOp);
        super.add(e1);
        super.add(e2);
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public Expr getExpr1() {
        return (Expr) super.getChildAt(0);
    }

    public Expr getExpr2() {
        return (Expr) super.getChildAt(1);
    }

    public String getNameOp() {
        return super.toString();
    }

    public String toString() {
        return super.toString();
    }

}