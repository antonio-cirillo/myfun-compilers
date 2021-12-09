package it.unisa.nodes.var;

import it.unisa.enums.Type;
import it.unisa.nodes.expr.Identifier;

import javax.swing.tree.DefaultMutableTreeNode;

public class ParamDeclOp extends DefaultMutableTreeNode {

    public ParamDeclOp(Type type, String id) {
        super("ParamDeclOp");
        super.add(new ModeOp("in"));
        super.add(new TypeOp(type));
        super.add(new Identifier(id));
    }

    public ParamDeclOp(String mode, Type type, String id) {
        super("ParamDeclOp");
        super.add(new ModeOp(mode));
        super.add(new TypeOp(type));
        super.add(new Identifier(id));
    }

    public ModeOp getMode() {
        return (ModeOp) super.getChildAt(0);
    }

    public TypeOp getType() {
        return (TypeOp) super.getChildAt(1);
    }

    public Identifier getValue() {
        return (Identifier) super.getChildAt(2);
    }

    public String toString() {
        return super.toString();
    }

}