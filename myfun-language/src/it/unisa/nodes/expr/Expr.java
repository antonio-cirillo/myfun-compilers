package it.unisa.nodes.expr;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class Expr extends DefaultMutableTreeNode {

    public Expr(String expr) {
        super(expr);
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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

    private String mode;
    private int line, column;

}