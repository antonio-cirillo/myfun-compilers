package it.unisa.nodes.stat;

import it.unisa.visitors.scoping.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class Stat extends DefaultMutableTreeNode {

    public Stat(String nameStat) {
        super(nameStat);
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

}
