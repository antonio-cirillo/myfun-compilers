package it.unisa.nodes;

import it.unisa.nodes.expr.Value;
import it.unisa.nodes.stat.Stat;
import it.unisa.nodes.var.ParamDeclOp;
import it.unisa.nodes.var.TypeOp;
import it.unisa.nodes.var.VarDeclOp;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Enumeration;

public class FunOp extends DefaultMutableTreeNode {

    public FunOp(String id, ArrayList<ParamDeclOp> paramDeclList,
                 String type, ArrayList<VarDeclOp> varDeclList, ArrayList<Stat> statList) {
        super("FunOp");
        super.add(new Value("id", id));
        for (ParamDeclOp paramDecl: paramDeclList)
            super.add(paramDecl);
        for (VarDeclOp varDecl : varDeclList)
            super.add(varDecl);
        super.add(new TypeOp(type));
        super.add(new BodyOp(varDeclList, statList));
        this.returnFlag = true;
    }

    public FunOp(String id, ArrayList<ParamDeclOp> paramDeclList,
                 ArrayList<VarDeclOp> varDeclList, ArrayList<Stat> statList) {
        super("FunOp");
        super.add(new Value("id", id));
        for (VarDeclOp varDecl : varDeclList)
            super.add(varDecl);
        super.add(new BodyOp(varDeclList, statList));
        this.returnFlag = false;
        this.indexType = varDeclList.size() + 1;
    }

    public Value getId() {
        return (Value) super.getChildAt(0);
    }

    public ArrayList<ParamDeclOp> getParamDeclOp() {
        ArrayList<ParamDeclOp> returnArray = new ArrayList<>();
        Enumeration kiddies = super.children();
        kiddies.nextElement();

        while (kiddies.hasMoreElements()) {
            DefaultMutableTreeNode kid = (DefaultMutableTreeNode) kiddies.nextElement();
            if (kid instanceof ParamDeclOp)
                returnArray.add((ParamDeclOp) kid);
            else
                continue;
        }

        return returnArray;
    }

    public DefaultMutableTreeNode getType() {
        if (returnFlag)
            return (DefaultMutableTreeNode) super.getChildAt(indexType);
        else
            return null;
    }

    public BodyOp getBody() {
        return (BodyOp) super.getChildAt(super.getChildCount() - 1);
    }

    public String toString() {
        return "FunOp";
    }

    private boolean returnFlag;
    private int indexType;

}
