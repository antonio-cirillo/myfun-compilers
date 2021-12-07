package it.unisa.nodes;

import it.unisa.nodes.stat.Stat;
import it.unisa.nodes.var.VarDeclOp;

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
    }

    public ArrayList<VarDeclOp> getVarDeclList() {
        if (super.getChildCount() == 0) return null;

        Enumeration kiddies = super.children();
        ArrayList<VarDeclOp> returnArray = new ArrayList<>();
        while (kiddies.hasMoreElements()) {
            DefaultMutableTreeNode kid = (DefaultMutableTreeNode) kiddies.nextElement();
            if (kid instanceof VarDeclOp)
                returnArray.add((VarDeclOp) kid);
            else
                break;
        }

        if (returnArray.size() > 0)
            return returnArray;
        else
            return null;
    }

    public ArrayList<Stat> getStatList() {
        if (super.getChildCount() == 0) return null;

        Enumeration kiddies = super.children();
        ArrayList<Stat> returnArray = new ArrayList<>();
        while (kiddies.hasMoreElements()) {
            DefaultMutableTreeNode kid = (DefaultMutableTreeNode) kiddies.nextElement();
            if (kid instanceof Stat)
                returnArray.add((Stat) kid);
            else
                continue;
        }

        if (returnArray.size() > 0)
            return returnArray;
        else
            return null;
    }

    public String toString() {
        return "BodyOp";
    }

}