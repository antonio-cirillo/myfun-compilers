package it.unisa.nodes.stat;

import it.unisa.nodes.expr.Expr;
import it.unisa.nodes.expr.Value;
import it.unisa.visitors.scoping.Visitor;

public class AssignOp extends Stat {

    public AssignOp(String id, Expr expr) {
        super("AssignOp");
        super.add(new Value("id", id));
        super.add(expr);
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public Value getId() {
        return (Value) super.getChildAt(0);
    }

    public Expr getExpr() {
        return (Expr) super.getChildAt(1);
    }

    public String toString() {
        return "AssignOp";
    }

}
