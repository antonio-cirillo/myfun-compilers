package it.unisa.nodes.stat;

import it.unisa.nodes.BodyOp;
import it.unisa.nodes.expr.Expr;
import it.unisa.nodes.var.VarDeclOp;
import it.unisa.visitors.scoping.Visitor;

import java.util.ArrayList;

public class IfStatOp extends Stat {

    public IfStatOp(Expr expr, ArrayList<VarDeclOp> varDeclList,
                    ArrayList<Stat> statList, BodyOp elseBody) {
        super("IfStatOp");
        super.add(expr);
        super.add(new BodyOp(varDeclList, statList));
        if (elseBody != null)
            super.add(elseBody);
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public Expr getExpr() {
        return (Expr) super.getChildAt(0);
    }

    public BodyOp getIfBody() {
        return (BodyOp) super.getChildAt(1);
    }

    public BodyOp getElseBody() {
        if (super.getChildCount() == 2)
            return null;
        else
            return (BodyOp) super.getChildAt(2);
    }

    public String toString() {
        return "IfStatOp";
    }

}