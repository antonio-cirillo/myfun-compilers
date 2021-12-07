package it.unisa.nodes;

import it.unisa.nodes.var.VarDeclOp;
import it.unisa.visitors.scoping.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class ProgramOp extends DefaultMutableTreeNode {

    public ProgramOp(ArrayList<VarDeclOp> varDeclList, ArrayList<FunOp> funList,
                     BodyOp main) {
        super("ProgramOp");
        for (VarDeclOp varDecl : varDeclList)
            super.add(varDecl);
        for (FunOp fun : funList)
            super.add(fun);
        super.add(main);
        this.varDeclList = varDeclList;
        this.funList = funList;
    }

    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public ArrayList<VarDeclOp> getVarDeclList() {
        return varDeclList;
    }

    public ArrayList<FunOp> getFunList() {
        return funList;
    }

    public BodyOp getMain() {
        return (BodyOp) super.getChildAt(super.getChildCount() - 1);
    }

    public String toString() {
        return "ProgramOp";
    }

    private ArrayList<VarDeclOp> varDeclList;
    private ArrayList<FunOp> funList;

}