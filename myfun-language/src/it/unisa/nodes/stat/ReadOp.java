package it.unisa.nodes.stat;

import it.unisa.nodes.expr.Expr;
import it.unisa.nodes.expr.Identifier;

import java.util.ArrayList;

public class ReadOp extends Stat {

    public ReadOp(ArrayList<Identifier> idList, Expr expr) {
        super("ReadOp");
        for (Identifier id : idList)
            super.add(id);
        super.add(expr);
        this.idList = idList;
        this.expr = expr;
    }

    public ReadOp(ArrayList<Identifier> idList) {
        super("ReadOp");
        for (Identifier id : idList)
            super.add(id);
        this.expr = null;
    }

    public ArrayList<Identifier> getIdList() {
        return idList;
    }

    public Expr getExpr() {
        return expr;
    }

    public String toString() {
        return super.toString();
    }

    private ArrayList<Identifier> idList;
    private Expr expr;

}
