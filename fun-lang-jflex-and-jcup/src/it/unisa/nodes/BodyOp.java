package it.unisa.nodes;

import it.unisa.nodes.stat.Stat;
import it.unisa.nodes.var.VarDeclOp;
import it.unisa.visitors.scoping.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Enumeration;

public class BodyOp extends Stat {

    public BodyOp(ArrayList<VarDeclOp> varDeclList, ArrayList<Stat> statList) {
        super("BodyOp");
        if (varDeclList != null) {
            for (VarDeclOp varDecl : varDeclList)
                super.add(varDecl);
        }
        if (statList != null) {
            for (Stat stat : statList) {
                if (stat != null)
                    super.add(stat);
            }
        }
        this.varDeclList = varDeclList;
        this.statList = statList;
    }

    public Object accept(Visitor visitor) {
        return visitor.visit(this);
    }

    public ArrayList<VarDeclOp> getVarDeclList() {
        return varDeclList;
    }

    public ArrayList<Stat> getStatList() {
        return statList;
    }

    public String toString() {
        return "BodyOp";
    }

    private ArrayList<VarDeclOp> varDeclList;
    private ArrayList<Stat> statList;

}