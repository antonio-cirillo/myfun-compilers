package it.unisa.nodes.var;

import it.unisa.nodes.interfaces.Visitable;
import it.unisa.visitors.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;

public class TypeOp
        extends DefaultMutableTreeNode implements Visitable {

    public TypeOp(String type) {
        super("TypeOp");
        super.add(new DefaultMutableTreeNode(type));
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public String getType() {
        return super.getChildAt(0).toString();
    }

    public String toString() {
        return super.toString();
    }

}