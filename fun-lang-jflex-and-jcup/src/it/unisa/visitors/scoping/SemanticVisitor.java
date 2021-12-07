package it.unisa.visitors.scoping;

import it.unisa.nodes.FunOp;
import it.unisa.nodes.ProgramOp;
import it.unisa.nodes.expr.Expr;
import it.unisa.nodes.expr.Value;
import it.unisa.nodes.var.IdInitOp;
import it.unisa.nodes.var.ParamDeclOp;
import it.unisa.nodes.var.TypeOp;
import it.unisa.nodes.var.VarDeclOp;
import it.unisa.symboltable.SymbolTable;

import javax.swing.tree.DefaultMutableTreeNode;

public class SemanticVisitor implements Visitor {

    @Override
    public Object visit(ProgramOp programOp) {
        SymbolTable.enterScope();
        for (VarDeclOp varDecl : programOp.getVarDeclList())
            varDecl.accept(this);
        for (FunOp fun: programOp.getFunList())
            fun.accept(this);

        System.out.println(SymbolTable.exitScope());

        return null;
    }

    @Override
    public Object visit(VarDeclOp varDeclOp) {
        String type = (String) varDeclOp.getType().accept(this);
        for (DefaultMutableTreeNode id : varDeclOp.getIdList()) {
            if (id instanceof Value) {
                String lexeme = (String) ((Value) id).accept(this);
                SymbolTable.addId(lexeme, type); // E' stato già dichiarato?
            } else if (id instanceof IdInitOp) {
                String[] results = (String[]) ((IdInitOp) id).accept(this);
                String typeAssign = results[0];
                String lexeme = results[1];
                if (type.equals("var") || type.equals((typeAssign)))
                    SymbolTable.addId(lexeme, typeAssign); // E' stato già dichiarato?
                else
                    ; // Che tipo stai usando?

            }
        }
        return null;
    }

    @Override
    public Object visit(TypeOp typeOp) {
        return typeOp.getType();
    }

    @Override
    public Object visit(IdInitOp idInitOp) {
        String type = (String) idInitOp.getExpr().accept(this);
        String lexeme = idInitOp.getId();

        return new String[]{type, lexeme};
    }

    @Override
    public Object visit(Value value) {
        if (value.getType().equals("id"))
            return value.getValue();
        else
            return value.getType();
    }

    @Override
    public Object visit(Expr expr) {
        if (expr instanceof Value)
            return ((Value) expr).getType();
        return null;
    }

    @Override
    public Object visit(FunOp funOp) {
        String lexeme = (String) funOp.getId().accept(this);
        for (ParamDeclOp paramDecl: funOp.getParamDeclOp())
            ;
        String returnType = (String) funOp.getType().accept(this);
        return null;
    }

}