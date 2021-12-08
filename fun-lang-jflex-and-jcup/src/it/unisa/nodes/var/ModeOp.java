package it.unisa.nodes.var;

import it.unisa.visitors.scoping.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;

public class ModeOp extends DefaultMutableTreeNode {

    public ModeOp(String mode) {
        super("ModeOp");
        super.add(new DefaultMutableTreeNode(mode));
    }

    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public String getMode() {
        return super.getChildAt(0).toString();
    }

    public String toString() {
        return "ModeOp";
    }

}
