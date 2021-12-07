package it.unisa.nodes;

import it.unisa.nodes.expr.Value;
import it.unisa.nodes.stat.Stat;
import it.unisa.nodes.var.ParamDeclOp;
import it.unisa.nodes.var.TypeOp;
import it.unisa.nodes.var.VarDeclOp;
import it.unisa.visitors.scoping.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Enumeration;

public class FunOp extends DefaultMutableTreeNode {

    public FunOp(String id, ArrayList<ParamDeclOp> paramDeclList,
                 String type, ArrayList<VarDeclOp> varDeclList, ArrayList<Stat> statList) {
        super("FunOp");
        super.add(new Value("id", id));
        for (ParamDeclOp paramDecl : paramDeclList)
            super.add(paramDecl);
        this.type = new TypeOp(type);
        super.add(this.type);
        super.add(new BodyOp(varDeclList, statList));
        this.paramDeclList = paramDeclList;
        this.varDeclList = varDeclList;
        this.statList = statList;
    }

    public FunOp(String id, ArrayList<ParamDeclOp> paramDeclList,
                 ArrayList<VarDeclOp> varDeclList, ArrayList<Stat> statList) {
        super("FunOp");
        super.add(new Value("id", id));
        for (VarDeclOp varDecl : varDeclList)
            super.add(varDecl);
        super.add(new BodyOp(varDeclList, statList));
        this.paramDeclList = paramDeclList;
        this.type = null;
        this.varDeclList = varDeclList;
        this.statList = statList;
    }

    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public Value getId() {
        return (Value) super.getChildAt(0);
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

    public String toString() {
        return "FunOp";
    }

    private ArrayList<ParamDeclOp> paramDeclList;
    private TypeOp type;
    private ArrayList<VarDeclOp> varDeclList;
    private ArrayList<Stat> statList;

}