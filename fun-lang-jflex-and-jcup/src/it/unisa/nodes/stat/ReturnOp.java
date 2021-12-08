package it.unisa.nodes.stat;

import it.unisa.nodes.expr.Expr;
import it.unisa.visitors.scoping.Visitor;

public class ReturnOp extends Stat {

    public ReturnOp(Expr expr) {
        super("ReturnOp");
        super.add(expr);
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public Expr getExpr() {
        return (Expr) super.getChildAt(0);
    }

    public String toString() {
        return "ReturnOp";
    }

}