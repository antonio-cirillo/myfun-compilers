package it.unisa.nodes.expr;

public class BinaryOp extends Expr {

    public BinaryOp(String nameOp, Expr expr1, Expr expr2) {
        super(nameOp);
        super.add(expr1);
        super.add(expr2);
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public Expr getExpr1() {
        return expr1;
    }

    public Expr getExpr2() {
        return expr2;
    }

    public String toString() {
        return super.toString();
    }

    private Expr expr1, expr2;

}