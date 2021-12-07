package it.unisa.nodes;

import it.unisa.nodes.var.VarDeclOp;

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
    }

    public ArrayList<VarDeclOp> getVarDeclList() {
        return null;
    }

    public ArrayList<FunOp> getFunList() {
        return null;
    }

    public BodyOp getMain() {
        return (BodyOp) super.getChildAt(super.getChildCount() - 1);
    }

    public String toString() {
        return "ProgramOp";
    }

}
