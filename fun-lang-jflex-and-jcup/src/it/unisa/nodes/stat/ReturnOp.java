package it.unisa.nodes.stat;

import it.unisa.nodes.expr.Expr;

public class ReturnOp extends Stat {

    public ReturnOp(Expr expr) {
        super("ReturnOp");
        super.add(expr);
    }

    public Expr getExpr() {
        return (Expr) super.getChildAt(0);
    }

    public String toString() {
        return "ReturnOp";
    }

}
