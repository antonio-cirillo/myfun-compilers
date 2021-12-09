package it.unisa.nodes.var;

import it.unisa.nodes.expr.Expr;
import it.unisa.nodes.expr.Identifier;

import javax.swing.tree.DefaultMutableTreeNode;

public class IdInitOp extends DefaultMutableTreeNode {

    public IdInitOp(String id, Expr expr) {
        super("IdInitOp");
        super.add(new Identifier(id));
        super.add(expr);
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