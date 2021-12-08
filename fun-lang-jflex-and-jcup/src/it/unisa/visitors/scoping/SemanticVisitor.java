package it.unisa.visitors.scoping;

import it.unisa.nodes.BodyOp;
import it.unisa.nodes.FunOp;
import it.unisa.nodes.ProgramOp;
import it.unisa.nodes.expr.Expr;
import it.unisa.nodes.expr.Value;
import it.unisa.nodes.stat.IfStatOp;
import it.unisa.nodes.stat.Stat;
import it.unisa.nodes.stat.WhileOp;
import it.unisa.nodes.var.*;
import it.unisa.symboltable.SymbolTable;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class SemanticVisitor implements Visitor {

    @Override
    public Object visit(ProgramOp programOp) {
        SymbolTable.enterScope();
        for (VarDeclOp varDecl : programOp.getVarDeclList())
            varDecl.accept(this);
        for (FunOp fun : programOp.getFunList())
            fun.accept(this);

        SymbolTable.enterScope();
        programOp.getMain().accept(this);

        System.out.println(SymbolTable.exitScope()); // Questo è quello del main
        System.out.println(SymbolTable.exitScope()); // Questo è quello del program

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
                String typeAssigned = results[0];
                typeAssigned = typeAssigned.substring(0, typeAssigned.indexOf('_'));
                String lexeme = results[1];
                if (type.equals("var") || type.equals((typeAssigned)))
                    SymbolTable.addId(lexeme, typeAssigned); // E' stato già dichiarato?
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
        String lexeme = (String) idInitOp.getId().accept(this);

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
        ArrayList<String> modeParams = new ArrayList<>();
        ArrayList<String> typeParams = new ArrayList<>();
        ArrayList<String> lexemeParams = new ArrayList<>();
        for (ParamDeclOp paramDecl : funOp.getParamDeclOp()) {
            String[] result = (String[]) paramDecl.accept(this);
            modeParams.add(result[0]);
            typeParams.add(result[1]);
            lexemeParams.add(result[2]);
        }
        String type;
        if (funOp.getType() != null)
            type = (String) funOp.getType().accept(this);
        else
            type = "void";
        ArrayList<String> typeParamsWithMode = new ArrayList<>();
        for (int i = 0; i < modeParams.size(); i++) {
            if (modeParams.get(i).equals("out"))
                typeParamsWithMode.add("out_" + typeParams.get(i));
            else
                typeParamsWithMode.add(typeParams.get(i));
        }
        SymbolTable.addId(lexeme, typeParamsWithMode, type);

        SymbolTable.enterScope();
        for (int i = 0; i < typeParams.size(); i++) {
            SymbolTable.addId(lexemeParams.get(i), typeParams.get(i));
        }
        funOp.getBody().accept(this);

        System.out.println(SymbolTable.exitScope());
        return null;
    }

    @Override
    public Object visit(ParamDeclOp paramDeclOp) {
        String mode = (String) paramDeclOp.getMode().accept(this);
        String type = (String) paramDeclOp.getType().accept(this);
        String lexeme = (String) paramDeclOp.getValue().accept(this);
        return new String[]{mode, type, lexeme};
    }

    @Override
    public Object visit(ModeOp modeOp) {
        return modeOp.getMode();
    }

    @Override
    public Object visit(BodyOp bodyOp) {
        ArrayList<VarDeclOp> varDeclList = bodyOp.getVarDeclList();
        if (varDeclList != null && varDeclList.size() > 0) {
            for (VarDeclOp varDecl : varDeclList) {
                varDecl.accept(this);
            }
        }
        ArrayList<Stat> statList = bodyOp.getStatList();
        if (statList.size() > 0) {
            for (Stat stat : statList) {
                if (stat != null)
                    stat.accept(this);
            }
        }

        return null;
    }

    @Override
    public Object visit(Stat stat) {
        if (stat instanceof IfStatOp)
            return stat.accept(this);
        if (stat instanceof WhileOp)
            return stat.accept(this);

        return null;
    }

    @Override
    public Object visit(IfStatOp ifStatOp) {
        SymbolTable.enterScope();
        ifStatOp.getIfBody().accept(this);
        System.out.println(SymbolTable.exitScope());
        BodyOp elseBody = ifStatOp.getElseBody();
        if (elseBody != null) {
            SymbolTable.enterScope();
            elseBody.accept(this);
            System.out.println(SymbolTable.exitScope());
        }
        return null;
    }

    @Override
    public Object visit(WhileOp whileOp) {
        SymbolTable.enterScope();
        whileOp.getBody().accept(this);
        System.out.println(SymbolTable.exitScope());
        return null;
    }

}