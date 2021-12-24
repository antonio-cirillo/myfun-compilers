package it.unisa.nodes.expr;

import it.unisa.nodes.interfaces.PointedTable;
import it.unisa.nodes.var.ModeOp;
import it.unisa.symboltable.row.RowMethod;
import it.unisa.visitors.Visitor;

import java.util.ArrayList;

public class CallFunOpExpr
        extends Expr implements PointedTable<RowMethod> {

    public CallFunOpExpr(String id, ArrayList<Expr> exprList) {
        super("CallFunOp");
        super.add(new Identifier(id));
        this.modeList = new ArrayList<>();
        for (Expr expr : exprList) {
            ModeOp mode = new ModeOp(expr.getMode());
            this.add(mode);
            modeList.add(mode);
            this.add(expr);
        }
        this.exprList = exprList;
    }

    public CallFunOpExpr(String id) {
        super("CallFunOp");
        super.add(new Identifier(id));
        this.modeList = null;
        this.exprList = null;
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public Identifier getId() {
        return (Identifier) super.getChildAt(0);
    }

    public ArrayList<ModeOp> getModeList() {
        return modeList;
    }

    public ArrayList<Expr> getExprList() {
        return exprList;
    }

    @Override
    public RowMethod getPointerToRow() {
        return row;
    }

    @Override
    public void setPointerToRow(RowMethod row) {
        this.row = row;
    }

    public String toString() {
        return super.toString();
    }

    private ArrayList<ModeOp> modeList;
    private ArrayList<Expr> exprList;
    private RowMethod row;

}