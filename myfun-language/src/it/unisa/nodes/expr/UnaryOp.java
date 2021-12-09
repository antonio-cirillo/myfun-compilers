package it.unisa.nodes.expr;

public class UnaryOp extends Expr {

    public UnaryOp(String nameOp, Expr expr) {
        super(nameOp);
        this.expr = expr;
    }

    public Expr getExpr() {
        return expr;
    }

    public String toString() {
        return super.toString();
    }

    private Expr expr;

}