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

    public String toString() {
        return super.toString();
    }

    private String mode;

}