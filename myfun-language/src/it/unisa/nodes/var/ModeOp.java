package it.unisa.nodes.var;

import javax.swing.tree.DefaultMutableTreeNode;

public class ModeOp extends DefaultMutableTreeNode {

    public ModeOp(String mode) {
        super("ModeOp");
        super.add(new DefaultMutableTreeNode(mode));
    }

    public String getMode() {
        return super.getChildAt(0).toString();
    }

    public String toString() {
        return super.toString();
    }

}