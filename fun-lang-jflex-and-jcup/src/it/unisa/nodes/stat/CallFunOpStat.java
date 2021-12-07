package it.unisa.nodes.stat;

import it.unisa.nodes.expr.Expr;
import it.unisa.nodes.expr.Value;
import it.unisa.nodes.var.ModeOp;

import java.util.ArrayList;
import java.util.Enumeration;

public class CallFunOpStat extends Stat {

    public CallFunOpStat(String id, ArrayList<Expr> exprList) {
        super("CallFunOp");
        super.add(new Value("id", id));
        for (Expr expr : exprList) {
            this.add(new ModeOp(expr.getMode()));
            this.add(expr);
        }
    }

    public CallFunOpStat(String id) {
        super("CallFunOp");
        super.add(new Value("id", id));
    }

    public Value getId() {
        return (Value) super.getChildAt(0);
    }

    public ArrayList<Expr> getExprList() {
        if (super.getChildCount() == 1) return null;

        ArrayList<Expr> returnArray = new ArrayList<>();
        Enumeration kiddies = super.children();
        kiddies.nextElement();

        while (kiddies.hasMoreElements()) {
            Expr child = (Expr) kiddies.nextElement();
            returnArray.add(child);
        }

        return returnArray;
    }

    public String toString() {
        return "CallFunOp";
    }

}