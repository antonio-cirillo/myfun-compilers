package it.unisa.nodes.stat;

import it.unisa.visitors.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class Stat extends DefaultMutableTreeNode {

    public Stat(String nameStat) {
        super(nameStat);
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String toString() {
        return super.toString();
    }

    private int line, column;

}