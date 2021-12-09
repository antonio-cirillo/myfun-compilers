package it.unisa.nodes.var;

import it.unisa.enums.Type;

import javax.swing.tree.DefaultMutableTreeNode;

public class TypeOp extends DefaultMutableTreeNode {

    public TypeOp(Type type) {
        super("TypeOp");
        super.add(new DefaultMutableTreeNode(type.toString()));
    }

    public String getType() {
        return super.getChildAt(0).toString();
    }

    public String toString() {
        return super.toString();
    }

}