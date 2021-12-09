package it.unisa.nodes;

import it.unisa.enums.Type;
import it.unisa.nodes.expr.Identifier;
import it.unisa.nodes.stat.Stat;
import it.unisa.nodes.var.ParamDeclOp;
import it.unisa.nodes.var.TypeOp;
import it.unisa.nodes.var.VarDeclOp;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class FunOp extends DefaultMutableTreeNode {

    public FunOp(String id, ArrayList<ParamDeclOp> paramDeclList,
                 Type type, ArrayList<VarDeclOp> varDeclList, ArrayList<Stat> statList) {
        super("FunOp");
        super.add(new Identifier(id));
        if (paramDeclList.size() > 0) {
            for (ParamDeclOp paramDecl : paramDeclList)
                super.add(paramDecl);
        }
        this.type = new TypeOp(type);
        super.add(this.type);
        super.add(new BodyOp(varDeclList, statList));
        this.paramDeclList = paramDeclList;
        this.varDeclList = varDeclList;
        this.statList = statList;
    }

    public FunOp(String id, ArrayList<ParamDeclOp> paramDeclList,
                 ArrayList<VarDeclOp> varDeclList, ArrayList<Stat> statList) {
        super("FunOp");
        super.add(new Identifier(id));
        if (paramDeclList.size() > 0) {
            for (ParamDeclOp paramDecl : paramDeclList)
                super.add(paramDecl);
        }
        super.add(new BodyOp(varDeclList, statList));
        this.paramDeclList = paramDeclList;
        this.type = null;
        this.varDeclList = varDeclList;
        this.statList = statList;
    }

    public Identifier getId() {
        return (Identifier) super.getChildAt(0);
    }

    public ArrayList<ParamDeclOp> getParamDeclOp() {
        return paramDeclList;
    }

    public TypeOp getType() {
        return type;
    }

    public BodyOp getBody() {
        return (BodyOp) super.getChildAt(super.getChildCount() - 1);
    }

    public String toString() {
        return super.toString();
    }

    private ArrayList<ParamDeclOp> paramDeclList;
    private TypeOp type;
    private ArrayList<VarDeclOp> varDeclList;
    private ArrayList<Stat> statList;

}