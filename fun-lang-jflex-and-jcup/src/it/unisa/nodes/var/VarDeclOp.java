package it.unisa.nodes.var;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Enumeration;

public class VarDeclOp extends DefaultMutableTreeNode {

    public VarDeclOp(String type, ArrayList<DefaultMutableTreeNode> idList) {
        super("VarDeclOp");
        super.add(new TypeOp(type));
        for (DefaultMutableTreeNode id : idList)
            super.add(id);
    }

    public TypeOp getType() {
        return (TypeOp) super.getChildAt(0);
    }

    public ArrayList<DefaultMutableTreeNode> getIdList() {
        if (super.getChildCount() == 1) return null;

        ArrayList<DefaultMutableTreeNode> returnArray = new ArrayList<>();
        Enumeration kiddies = super.children();
        kiddies.nextElement();

        while (kiddies.hasMoreElements())
            returnArray.add((DefaultMutableTreeNode) kiddies.nextElement());

        return returnArray;
    }

    public String toString() {
        return "VarDeclOp";
    }

}