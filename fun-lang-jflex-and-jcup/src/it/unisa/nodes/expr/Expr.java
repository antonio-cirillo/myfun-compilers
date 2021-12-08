package it.unisa.nodes.expr;

import it.unisa.nodes.stat.Stat;
import it.unisa.visitors.scoping.Visitor;

public abstract class Expr extends Stat {

    public Expr(String nameOp) {
        super(nameOp);
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

    private String mode;

}