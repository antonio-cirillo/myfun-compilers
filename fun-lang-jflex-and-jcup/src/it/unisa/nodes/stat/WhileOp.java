package it.unisa.nodes.stat;

import it.unisa.nodes.BodyOp;
import it.unisa.nodes.expr.Expr;
import it.unisa.nodes.var.VarDeclOp;

import java.util.ArrayList;

public class WhileOp extends Stat {

    public WhileOp(Expr expr, ArrayList<VarDeclOp> varDeclList,
                   ArrayList<Stat> statList) {
        super("WhileOp");
        super.add(expr);
        super.add(new BodyOp(varDeclList, statList));
    }

    public Expr getExpr() {
        return (Expr) super.getChildAt(0);
    }

    public BodyOp getBody() {
        return (BodyOp) super.getChildAt(1);
    }

    public String toString() {
        return "WhileOp";
    }

}