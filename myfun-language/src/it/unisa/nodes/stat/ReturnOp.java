package it.unisa.nodes.stat;

import it.unisa.nodes.expr.Expr;

public class ReturnOp extends Stat {

    public ReturnOp(Expr expr) {
        super("ReturnOp");
        super.add(expr);
        this.expr = expr;
    }

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }

    public String toString() {
        return super.toString();
    }

    private Expr expr;

}