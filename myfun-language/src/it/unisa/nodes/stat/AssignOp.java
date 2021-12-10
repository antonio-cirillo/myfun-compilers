package it.unisa.nodes.stat;

import it.unisa.nodes.expr.Expr;
import it.unisa.nodes.expr.Identifier;
import it.unisa.visitors.Visitor;

public class AssignOp extends Stat {

    public AssignOp(String id, Expr expr) {
        super("AssignOp");
        super.add(new Identifier(id));
        super.add(expr);
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public Identifier getId() {
        return (Identifier) super.getChildAt(0);
    }

    public Expr getExpr() {
        return (Expr) super.getChildAt(1);
    }

    public String toString() {
        return super.toString();
    }

}