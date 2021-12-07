package it.unisa.nodes.var;

import it.unisa.nodes.expr.Value;

import javax.swing.tree.DefaultMutableTreeNode;

public class ParamDeclOp extends DefaultMutableTreeNode {

    public ParamDeclOp(String type, String id) {
        super("ParamDeclOp");
        super.add(new ModeOp("in"));
        super.add(new TypeOp(type));
        super.add(new Value("id", id));
    }

    public ParamDeclOp(String mode, String type, String id) {
        super("ParamDeclOp");
        super.add(new ModeOp(mode));
        super.add(new TypeOp(type));
        super.add(new Value("id", id));
    }

    public ModeOp getMode() {
        return (ModeOp) super.getChildAt(0);
    }

    public TypeOp getType() {
        return (TypeOp) super.getChildAt(1);
    }

    public Value getValue() {
        return (Value) super.getChildAt(2);
    }

    public String toString() {
        return "ParamDeclOp";
    }

}