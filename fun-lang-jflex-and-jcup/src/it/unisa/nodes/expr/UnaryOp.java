package it.unisa.nodes.expr;

public class UnaryOp extends Expr {

    public UnaryOp(String nameOp, Expr e) {
        super(nameOp);
        super.add(e);
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