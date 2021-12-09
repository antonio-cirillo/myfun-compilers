package it.unisa.nodes;

import it.unisa.nodes.stat.Stat;
import it.unisa.nodes.var.VarDeclOp;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class BodyOp extends DefaultMutableTreeNode {

    public BodyOp(ArrayList<VarDeclOp> varDeclList, ArrayList<Stat> statList) {
        super("BodyOp");
        if (varDeclList.size() > 0) {
            for (VarDeclOp varDecl : varDeclList)
                super.add(varDecl);
        }
        if (statList.size() > 0) {
            for (Stat stat : statList) {
                if (stat != null)
                    super.add(stat);
            }
        }
        this.varDeclList = varDeclList;
        this.statList = statList;
    }

    public ArrayList<VarDeclOp> getVarDeclList() {
        return varDeclList;
    }

    public ArrayList<Stat> getStatList() {
        return statList;
    }

    public String toString() {
        return super.toString();
    }

    private ArrayList<VarDeclOp> varDeclList;
    private ArrayList<Stat> statList;

}