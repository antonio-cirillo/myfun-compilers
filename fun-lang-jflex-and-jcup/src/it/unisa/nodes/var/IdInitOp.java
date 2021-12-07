package it.unisa.nodes.var;

import it.unisa.nodes.expr.Expr;
import it.unisa.nodes.expr.Value;

import javax.swing.tree.DefaultMutableTreeNode;

public class IdInitOp extends DefaultMutableTreeNode {

    public IdInitOp(String id, Expr expr) {
        super("IdInitOp");
        super.add(new Value("id", id));
        super.add(expr);
    }

    public String getId() {
        return super.getChildAt(0).toString();
    }

    public Expr getExpr() {
        return (Expr) super.getChildAt(1);
    }

    public String toString() {
        return "IdInitOp";
    }

}