package it.unisa.nodes.expr;

import it.unisa.nodes.interfaces.Lineable;
import it.unisa.visitors.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class Expr
        extends DefaultMutableTreeNode implements Lineable {

    public Expr(String expr) {
        super(expr);
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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

    private String mode;
    private int line;

}