package it.unisa.nodes;

import it.unisa.nodes.expr.Identifier;
import it.unisa.nodes.interfaces.Lineable;
import it.unisa.nodes.interfaces.PointedTable;
import it.unisa.nodes.stat.Stat;
import it.unisa.nodes.var.ParamDeclOp;
import it.unisa.nodes.var.TypeOp;
import it.unisa.nodes.var.VarDeclOp;
import it.unisa.symboltable.row.RowMethod;
import it.unisa.visitors.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class FunOp
        extends DefaultMutableTreeNode implements Lineable, PointedTable<RowMethod> {

    public FunOp(String id, ArrayList<ParamDeclOp> paramDeclList,
                 String type, ArrayList<VarDeclOp> varDeclList, ArrayList<Stat> statList) {
        super("FunOp");
        super.add(new Identifier(id));
        if (paramDeclList.size() > 0) {
            for (ParamDeclOp paramDecl : paramDeclList)
                super.add(paramDecl);
        }
        this.type = new TypeOp(type);
        super.add(this.type);
        super.add(new BodyOp(varDeclList, statList));
        this.paramDeclList = paramDeclList;
    }

    public FunOp(String id, ArrayList<ParamDeclOp> paramDeclList,
                 ArrayList<VarDeclOp> varDeclList, ArrayList<Stat> statList) {
        super("FunOp");
        super.add(new Identifier(id));
        if (paramDeclList.size() > 0) {
            for (ParamDeclOp paramDecl : paramDeclList)
                super.add(paramDecl);
        }
        super.add(new BodyOp(varDeclList, statList));
        this.paramDeclList = paramDeclList;
        this.type = null;
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public Identifier getId() {
        return (Identifier) super.getChildAt(0);
    }

    public ArrayList<ParamDeclOp> getParamDeclOp() {
        return paramDeclList;
    }

    public TypeOp getType() {
        return type;
    }

    public BodyOp getBody() {
        return (BodyOp) super.getChildAt(super.getChildCount() - 1);
    }

    @Override
    public RowMethod getPointerToRow() {
        return row;
    }

    @Override
    public void setPointerToRow(RowMethod row) {
        this.row = row;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String toString() {
        return super.toString();
    }

    private ArrayList<ParamDeclOp> paramDeclList;
    private TypeOp type;
    private RowMethod row;
    private int line;

}