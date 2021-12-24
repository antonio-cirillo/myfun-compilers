package it.unisa.nodes.stat;

import it.unisa.nodes.interfaces.Lineable;
import it.unisa.nodes.interfaces.Visitable;
import it.unisa.visitors.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class Stat
        extends DefaultMutableTreeNode implements Visitable, Lineable {

    public Stat(String nameStat) {
        super(nameStat);
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public void setLine(int line) {
        this.line = line;
    }

    public String toString() {
        return super.toString();
    }

    private int line;

}