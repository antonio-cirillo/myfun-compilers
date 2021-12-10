package it.unisa.nodes.var;

import it.unisa.visitors.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;

public class ModeOp extends DefaultMutableTreeNode {

    public ModeOp(String mode) {
        super("ModeOp");
        super.add(new DefaultMutableTreeNode(mode));
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public String getMode() {
        return super.getChildAt(0).toString();
    }

    public String toString() {
        return super.toString();
    }

}