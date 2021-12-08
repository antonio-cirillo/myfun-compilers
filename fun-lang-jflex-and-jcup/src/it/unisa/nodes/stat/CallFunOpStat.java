package it.unisa.nodes.stat;

import it.unisa.nodes.expr.Expr;
import it.unisa.nodes.expr.Value;
import it.unisa.nodes.var.ModeOp;
import it.unisa.visitors.scoping.Visitor;

import java.util.ArrayList;
import java.util.Enumeration;

public class CallFunOpStat extends Stat {

    public CallFunOpStat(String id, ArrayList<Expr> exprList) {
        super("CallFunOp");
        super.add(new Value("id", id));
        this.modeList = new ArrayList<>();
        for (Expr expr : exprList) {
            ModeOp mode = new ModeOp(expr.getMode());
            this.add(mode);
            modeList.add(mode);
            this.add(expr);
        }
        this.exprList = exprList;
    }

    public CallFunOpStat(String id) {
        super("CallFunOp");
        super.add(new Value("id", id));
        this.modeList = null;
        this.exprList = null;
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public Value getId() {
        return (Value) super.getChildAt(0);
    }

    public ArrayList<ModeOp> getModeList() {
        return modeList;
    }

    public ArrayList<Expr> getExprList() {
        return exprList;
    }

    public String toString() {
        return "CallFunOp";
    }

    private ArrayList<ModeOp> modeList;
    private ArrayList<Expr> exprList;

}