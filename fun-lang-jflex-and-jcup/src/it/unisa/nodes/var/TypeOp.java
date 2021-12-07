package it.unisa.nodes.var;

import javax.swing.tree.DefaultMutableTreeNode;

public class TypeOp extends DefaultMutableTreeNode {

    public TypeOp(String type) {
        super("TypeOp");
        super.add(new DefaultMutableTreeNode(type));
    }

    public String getType() {
        return super.getChildAt(0).toString();
    }

    public String toString() {
        return "TypeOp";
    }

}