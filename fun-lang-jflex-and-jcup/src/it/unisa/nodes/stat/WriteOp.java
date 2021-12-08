package it.unisa.nodes.stat;

import it.unisa.nodes.expr.Expr;
import it.unisa.visitors.scoping.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;

public class WriteOp extends Stat {

    public WriteOp(String type, Expr expr) {
        super("WriteOp");
        super.add(new DefaultMutableTreeNode(
                type.equals("?") ? "write" :
                        type.equals("?.") ? "writeln" :
                                type.equals("?,") ? "writeb" : "writet"
        ));
        super.add(expr);
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public String getType() {
        return super.getChildAt(0).toString();
    }

    public Expr getExpr() {
        return (Expr) super.getChildAt(1);
    }

    public String toString() {
        return "WriteOp";
    }

}
