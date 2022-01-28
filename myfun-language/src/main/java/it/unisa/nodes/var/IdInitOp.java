package it.unisa.nodes.var;

import it.unisa.nodes.expr.Expr;
import it.unisa.nodes.expr.Identifier;
import it.unisa.nodes.interfaces.Visitable;
import it.unisa.visitors.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;

public class IdInitOp
        extends DefaultMutableTreeNode implements Visitable {

    public IdInitOp(String id, Expr expr) {
        super("IdInitOp");
        super.add(new Identifier(id));
        super.add(expr);
    }

    @Override
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