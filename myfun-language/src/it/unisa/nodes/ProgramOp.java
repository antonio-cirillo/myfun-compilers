package it.unisa.nodes;

import it.unisa.nodes.interfaces.Visitable;
import it.unisa.nodes.var.VarDeclOp;
import it.unisa.visitors.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class ProgramOp
        extends DefaultMutableTreeNode implements Visitable {

    public ProgramOp(ArrayList<VarDeclOp> varDeclList, ArrayList<FunOp> funList, BodyOp main) {
        super("ProgramOp");
        if (varDeclList.size() > 0) {
            for (VarDeclOp varDecl : varDeclList)
                super.add(varDecl);
        }
        if (funList.size() > 0) {
            for (FunOp fun : funList)
                super.add(fun);
        }
        super.add(main);
        this.varDeclList = varDeclList;
        this.funList = funList;
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public ArrayList<VarDeclOp> getVarDeclList() {
        return varDeclList;
    }

    public ArrayList<FunOp> getFunList() {
        return funList;
    }

    public BodyOp getBody() {
        return (BodyOp) super.getChildAt(super.getChildCount() - 1);
    }

    public String toString() {
        return super.toString();
    }

    private ArrayList<VarDeclOp> varDeclList;
    private ArrayList<FunOp> funList;

}