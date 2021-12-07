package it.unisa.nodes.stat;

import it.unisa.nodes.expr.Expr;
import it.unisa.nodes.expr.Value;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Enumeration;

public class ReadOp extends Stat {

    public ReadOp(ArrayList<Value> idList, Expr expr) {
        super("ReadOp");
        for (Value id : idList)
            super.add(id);
        super.add(expr);
    }

    public ReadOp(ArrayList<Value> idList) {
        super("ReadOp");
        for (Value id : idList)
            super.add(id);
    }

    public ArrayList<Value> getIdList() {
        ArrayList<Value> returnArray = new ArrayList<>();
        Enumeration kiddies = super.children();
        for (int i = 0; i < super.getChildCount() - 1; i++)
            returnArray.add((Value) kiddies.nextElement());

        DefaultMutableTreeNode lastNode = (DefaultMutableTreeNode) kiddies.nextElement();
        if (lastNode instanceof Value)
            super.add(lastNode);
        return returnArray;
    }

    public Expr getExpr() {
        DefaultMutableTreeNode lastNode =
                (DefaultMutableTreeNode) super.getChildAt(super.getChildCount() - 1);

        return (lastNode instanceof Expr) ? (Expr) lastNode : null;
    }

    public String toString() {
        return "ReadOp";
    }

}
