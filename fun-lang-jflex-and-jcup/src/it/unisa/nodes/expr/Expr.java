package it.unisa.nodes.expr;

import it.unisa.nodes.stat.Stat;

public abstract class Expr extends Stat {

    public Expr(String nameOp) {
        super(nameOp);
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    private String mode;

}
