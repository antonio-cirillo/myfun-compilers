package it.unisa.nodes.stat;

import it.unisa.nodes.BodyOp;
import it.unisa.nodes.expr.Expr;
import it.unisa.nodes.var.VarDeclOp;

import java.util.ArrayList;

public class IfStatOp extends Stat {

    public IfStatOp(Expr expr, ArrayList<VarDeclOp> varDeclList,
                    ArrayList<Stat> statList, BodyOp elseBody) {
        super("IfStatOp");
        super.add(expr);
        super.add(new BodyOp(varDeclList, statList));
        super.add(elseBody);
    }

    public IfStatOp(Expr expr, ArrayList<VarDeclOp> varDeclList,
                    ArrayList<Stat> statList, Stat elseIfBody) {
        super("IfStatOp");
        super.add(expr);
        super.add(new BodyOp(varDeclList, statList));
        ArrayList<Stat> elseStat = new ArrayList<>();
        elseStat.add(elseIfBody);
        super.add(new BodyOp(null, elseStat));
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