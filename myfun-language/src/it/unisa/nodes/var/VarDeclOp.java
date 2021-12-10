package it.unisa.nodes.var;

import it.unisa.visitors.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class VarDeclOp extends DefaultMutableTreeNode {

    public VarDeclOp(String type, ArrayList<DefaultMutableTreeNode> idList) {
        super("VarDeclOp");
        super.add(new TypeOp(type));
        for (DefaultMutableTreeNode id : idList)
            super.add(id);
        this.idList = idList;
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public TypeOp getType() {
        return (TypeOp) super.getChildAt(0);
    }

    public ArrayList<DefaultMutableTreeNode> getIdList() {
        return idList;
    }

    public String toString() {
        return super.toString();
    }

    private ArrayList<DefaultMutableTreeNode> idList;

}