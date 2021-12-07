package it.unisa.nodes.expr;

import it.unisa.nodes.var.ModeOp;

import java.util.ArrayList;
import java.util.Enumeration;

public class CallFunOpExpr extends Expr {

    public CallFunOpExpr(String id, ArrayList<Expr> exprList) {
        super("CallFunOp");
        super.add(new Value("id", id));
        for (Expr expr : exprList) {
            this.add(new ModeOp(expr.getMode()));
            this.add(expr);
        }
    }

    public CallFunOpExpr(String id) {
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