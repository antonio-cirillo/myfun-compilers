package it.unisa.visitors.scoping;

import it.unisa.nodes.BodyOp;
import it.unisa.nodes.FunOp;
import it.unisa.nodes.ProgramOp;
import it.unisa.nodes.expr.*;
import it.unisa.nodes.stat.*;
import it.unisa.nodes.var.*;
import it.unisa.symboltable.SymbolTable;
import it.unisa.symboltable.row.Row;
import it.unisa.symboltable.row.RowMethod;
import it.unisa.symboltable.row.RowVar;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class SemanticVisitor implements Visitor {

    @Override
    public Object visit(ProgramOp programOp) throws Exception {
        // Creo lo scope del program
        SymbolTable.enterScope();
        // Aggiungo le dichiarazioni di variabili
        for (VarDeclOp varDecl : programOp.getVarDeclList())
            varDecl.accept(this);
        // Aggiungo le dichiarazioni di funzioni
        for (FunOp fun : programOp.getFunList())
            fun.accept(this);

        // Credo lo scope del main
        SymbolTable.enterScope();
        // Gestisco lo scope del main
        programOp.getMain().accept(this);

        // Esco dallo scope del main
        System.out.println(SymbolTable.exitScope());
        // Esco dallo scope del program
        System.out.println(SymbolTable.exitScope());

        return null;
    }

    @Override
    public Object visit(VarDeclOp varDeclOp) throws Exception {
        // VarDeclOp è formato da un tipo e a seguire dai nomi dei lessemi
        // Memorizzo il tipo
        String type = (String) varDeclOp.getType().accept(this);
        for (DefaultMutableTreeNode id : varDeclOp.getIdList()) {
            // Se la variabile dichiarata non è inizializzata
            if (id instanceof Value) {
                // Salvo il nome della variabile
                String lexeme = (String) ((Value) id).accept(this);
                // Inserisco il lessema all'interno della symbol tabel
                SymbolTable.addId(lexeme, type);
            }
            // Se la variabile dichiarata è inizializzata
            else if (id instanceof IdInitOp) {
                // Ottengo il nome del lessema e il tipo con cui è stata inizializzata
                String[] results = (String[]) ((IdInitOp) id).accept(this);
                String typeAssigned = results[0];
                typeAssigned = typeAssigned.substring(0, typeAssigned.indexOf('_'));
                String lexeme = results[1];
                // Se la dichiarazione è var allora la variabile avra il tipo corrispondete al tipo dell'assegnazione
                // Se il tipo dichiarato corrisponde al tipo assegnato effettuo la stessa operazione
                if (type.equals("var") || type.equals((typeAssigned)))
                    SymbolTable.addId(lexeme, typeAssigned);
                    // Se il tipo assegnato non corrisponde al tipo della variabile lancio un'eccezione
                else
                    throw new SymbolTable.TypeMismatch(lexeme, type, typeAssigned);
            }
        }

        return null;
    }

    @Override
    public Object visit(TypeOp typeOp) throws Exception {
        return typeOp.getType();
    }

    @Override
    public Object visit(IdInitOp idInitOp) throws Exception {
        String type = (String) idInitOp.getExpr().accept(this);
        String lexeme = (String) idInitOp.getId().accept(this);

        return new String[]{type, lexeme};
    }

    @Override
    public Object visit(Value value) throws Exception {
        // Se il valore è un id ritorno il lessema
        if (value.getType().equals("id"))
            return value.getValue();
            // Altrimenti ritorno il tipo della costante
        else
            return value.getType();
    }

    @Override
    public Object visit(FunOp funOp) throws Exception {
        // Ottengo il lessema della funzione
        String lexeme = (String) funOp.getId().accept(this);
        // Inizializzo gli array di modalità, tipo e lessemi degli argomenti
        ArrayList<String> modeParams = new ArrayList<>();
        ArrayList<String> typeParams = new ArrayList<>();
        ArrayList<String> lexemeParams = new ArrayList<>();
        // Ottengo le informazioni su tutti i parametri
        for (ParamDeclOp paramDecl : funOp.getParamDeclOp()) {
            String[] result = (String[]) paramDecl.accept(this);
            modeParams.add(result[0]);
            typeParams.add(result[1]);
            lexemeParams.add(result[2]);
        }
        // Ottengo il tipo di ritorno della funzione
        String type;
        if (funOp.getType() != null)
            type = (String) funOp.getType().accept(this);
        else
            type = "void";
        // Inizializzo l'array che identifica il tipo e modalità dei parametri
        ArrayList<String> typeParamsWithMode = new ArrayList<>();
        for (int i = 0; i < modeParams.size(); i++) {
            if (modeParams.get(i).equals("out"))
                typeParamsWithMode.add("out_" + typeParams.get(i));
            else
                typeParamsWithMode.add(typeParams.get(i));
        }
        // Aggiungo le informazioni alla tabella dei simboli
        SymbolTable.addId(lexeme, typeParamsWithMode, type);

        // Creo lo scope relativo al corpo della funzione
        SymbolTable.enterScope();
        // Aggiungo i parametri all'interno dello scope della funzione
        for (int i = 0; i < typeParams.size(); i++) {
            SymbolTable.addId(lexemeParams.get(i), typeParams.get(i));
        }
        // Gestisco lo scope del corpo della funzione
        funOp.getBody().accept(this);

        // Esco dallo scope della funzione
        System.out.println(SymbolTable.exitScope());

        return null;
    }

    @Override
    public Object visit(ParamDeclOp paramDeclOp) throws Exception {
        String mode = (String) paramDeclOp.getMode().accept(this);
        String type = (String) paramDeclOp.getType().accept(this);
        String lexeme = (String) paramDeclOp.getValue().accept(this);

        return new String[]{mode, type, lexeme};
    }

    @Override
    public Object visit(ModeOp modeOp) throws Exception {
        return modeOp.getMode();
    }

    @Override
    public Object visit(BodyOp bodyOp) throws Exception {
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
    public Object visit(Stat stat) throws Exception {
        System.out.println(stat);
        if (stat instanceof AssignOp)
            return stat.accept(this);
        if (stat instanceof CallFunOpStat)
              return stat.accept(this);
        if (stat instanceof IfStatOp)
            return stat.accept(this);
        if (stat instanceof ReadOp)
            return stat.accept(this);
        if (stat instanceof ReturnOp)
            return stat.accept(this);
        if (stat instanceof WhileOp)
            return stat.accept(this);
        if (stat instanceof WriteOp)
            return stat.accept(this);

        return null;
    }

    @Override
    public Object visit(IfStatOp ifStatOp) throws Exception {
        // Controllo se la condizione è di tipo booleana
        ifStatOp.getExpr().accept(this);
        // Entro nello scope dell'if
        SymbolTable.enterScope();
        // Gestisco lo scope del corpo dell'if
        ifStatOp.getIfBody().accept(this);
        // Esco dallo scope dell'if
        System.out.println(SymbolTable.exitScope());

        // Se è presente l'istruzione else
        BodyOp elseBody = ifStatOp.getElseBody();
        if (elseBody != null) {
            // Creo lo scope dell'else
            SymbolTable.enterScope();
            // Gestisco lo scope del corpo dell'else
            elseBody.accept(this);
            // Esco dallo scope dell'else
            System.out.println(SymbolTable.exitScope());
        }

        return null;
    }

    @Override
    public Object visit(WhileOp whileOp) throws Exception {
        // Controllo se la condizione è di tipo booleana
        whileOp.getExpr().accept(this);
        // Entro nello scope del while
        SymbolTable.enterScope();
        // Gestisco lo scope del corpo del while
        whileOp.getBody().accept(this);
        // Esco dallo scope del while
        System.out.println(SymbolTable.exitScope());

        return null;
    }

    @Override
    public Object visit(BinaryOp binaryOp) throws Exception {
        // Ottengo il tipo di operazione
        String op = binaryOp.toString();
        // Ottengo il tipo del primo operando
        String typeArg1 = (String) defineType(binaryOp.getExpr1());
        // Ottengo il tipo del secondo operando
        String typeArg2 = (String) defineType(binaryOp.getExpr2());

        // Controllo sulle operazioni aritmetiche classiche
        if (op.equals("AddOp") || op.equals("DiffOp") || op.equals("MulOp") || op.equals("DivOp")) {
            String type = ARITMETIC_TYPE[typeToInt(typeArg1)][typeToInt(typeArg2)];
            if (type == null)
                throw new SymbolTable.OperationNotDefined(op, typeArg1, typeArg2);
            else
                return type;
        }

        // Controllo sulla concatenazione di stringhe
        if (op.equals("StrCatOp")) {
            return "string_const";
            /*
            if (typeArg1.equals("string_const") && typeArg2.equals("string_const"))
                return "string_const";
            else
                throw new SymbolTable.OperationNotDefined(op, typeArg1, typeArg2);
             */
        }

        // Controllo sulle operazioni di relazione
        if (op.equals("EQOp") || op.equals("LTOp") || op.equals("LEOp") || op.equals("NEOp") ||
                op.equals("GTOp") || op.equals("GEOp")) {
            String type = ARITMETIC_TYPE[typeToInt(typeArg1)][typeToInt(typeArg2)];
            if (type == null)
                throw new SymbolTable.OperationNotDefined(op, typeArg1, typeArg2);
            else
                return type;
        }

        // Controllo sulle operazioni logiche
        if (op.equals("AndOp") || op.equals("OrOp")) {
            if (typeArg1.equals("bool_const") && typeArg2.equals("bool_const"))
                return "bool_const";
            else
                throw new SymbolTable.OperationNotDefined(op, typeArg1, typeArg2);
        }

        return null;
    }

    @Override
    public Object visit(CallFunOpExpr callFunOpExpr) throws Exception {
        String lexeme = (String) callFunOpExpr.getId().accept(this);
        Row row = SymbolTable.lookup(lexeme);
        ArrayList<Expr> exprList = callFunOpExpr.getExprList();
        ArrayList<ModeOp> modeList = callFunOpExpr.getModeList();
        ArrayList<String> typeArgumentDefined = ((RowMethod) row).getParamType();

        if (row == null)
            throw new SymbolTable.CannotResolveSymbol(lexeme);

        if (exprList.size() != typeArgumentDefined.size())
            throw new SymbolTable.CannotResolveSymbol(lexeme);

        for (int i = 0; i < exprList.size(); i++) {
            String prefix = (modeList.get(i).accept(this)).equals("out") ? "out_" : "";
            String type = prefix + defineType(exprList.get(i));
            if (!type.equals(typeArgumentDefined.get(i)))
                throw new SymbolTable.CannotResolveSymbol(lexeme);
        }


        String type = ((RowMethod) row).getReturnType();

        return type;
    }

    @Override
    public Object visit(CallFunOpStat callFunOpStat) throws Exception {
        String lexeme = (String) callFunOpStat.getId().accept(this);
        Row row = SymbolTable.lookup(lexeme);
        ArrayList<Expr> exprList = callFunOpStat.getExprList();
        ArrayList<ModeOp> modeList = callFunOpStat.getModeList();
        ArrayList<String> typeArgumentDefined = ((RowMethod) row).getParamType();

        if (row == null)
            throw new SymbolTable.CannotResolveSymbol(lexeme);

        if (exprList.size() != typeArgumentDefined.size())
            throw new SymbolTable.CannotResolveSymbol(lexeme);

        for (int i = 0; i < exprList.size(); i++) {
            String prefix = (modeList.get(i).accept(this)).equals("out") ? "out_" : "";
            String type = prefix + defineType(exprList.get(i));
            System.out.println(type);
            if (!type.equals(typeArgumentDefined.get(i)))
                throw new SymbolTable.CannotResolveSymbol(lexeme);
        }

        String type = ((RowMethod) row).getReturnType();

        return type;
    }

    @Override
    public Object visit(UnaryOp unaryOp) throws Exception {
        // Ottengo il tipo di operazione
        String op = unaryOp.toString();
        // Ottengo il tipo del primo operando
        String typeArg = (String) defineType(unaryOp.getExpr());

        // Controllo sull'operazione uminus
        if (op.equals("UminusOp")) {
            if (typeArg.equals("integer_const") || typeArg.equals("real_const"))
                return typeArg;
            else
                throw new SymbolTable.OperationNotDefined(op, typeArg);
        }

        // Controllo sull'operazione not
        if (op.equals("NotOp")) {
            if (typeArg.equals("bool_const"))
                return typeArg;
            else
                throw new SymbolTable.OperationNotDefined(op, typeArg);
        }

        return null;
    }

    @Override
    public Object visit(ReturnOp returnOp) throws Exception {
        /* do nothing */
        return null;
    }

    @Override
    public Object visit(AssignOp assignOp) throws Exception {
        String type = (String) defineType(assignOp.getId());
        String typeAssigned = (String) defineType(assignOp.getExpr());
        if (typeAssigned.contains("_"))
            typeAssigned = typeAssigned.substring(0, typeAssigned.indexOf('_'));

        if(type.equals(typeAssigned))
            return null;
        else
            throw new SymbolTable.TypeMismatch(assignOp.getId().getValue(), type, typeAssigned);
    }

    @Override
    public Object visit(Expr expr) throws Exception {
        return null;
    }

    @Override
    public Object visit(WriteOp writeOp) throws Exception {
        String type = (String) defineType(writeOp.getExpr());
        if (type.equals("string") || type.equals("string_const"))
            return null;
        else
            throw new SymbolTable.OperationNotDefined(writeOp.getType(), type);
    }

    @Override
    public Object visit(ReadOp readOp) throws Exception {
        return "error";
    }

    private int typeToInt(String type) {
        if (type.equals("integer") || type.equals("integer_const"))
            return 0;
        if (type.equals("real") || type.equals("real_const"))
            return 1;
        if (type.equals("string") || type.equals("string_const"))
            return 2;
        if (type.equals("bool") || type.equals("bool_const"))
            return 3;

        return -1;
    }

    private Object defineType(Expr expr) throws Exception {
        if (expr instanceof Value) {
            String typeOrId = (String) expr.accept(this);
            if (typeOrId.equals("integer_const") || typeOrId.equals("real_const") ||
                    typeOrId.equals("string_const") || typeOrId.equals("bool_const")) {
                return typeOrId;
            } else {
                Row row = SymbolTable.lookup(typeOrId);
                if (row == null)
                    throw new SymbolTable.CannotResolveSymbol(typeOrId);
                if (row instanceof RowVar)
                    return ((RowVar) row).getType();
                else
                    ; // Qui sto cercando di usare una variabile che però è un metodo
            }
        }

        if (expr instanceof BinaryOp)
            return expr.accept(this);

        if (expr instanceof UnaryOp)
            return expr.accept(this);

        if (expr instanceof CallFunOpExpr)
            return expr.accept(this);

        return null;
    }

    private static final String[][] ARITMETIC_TYPE = {
            {"integer_const", "real_const", null, null},
            {"real_const", "real_const", null, null},
            {null, null, null, null},
            {null, null, null, null}
    };

    private static final String[][] RELATION_TYPE = {
            {"bool_const", "bool_const", null, null},
            {"bool_const", "bool_const", null, null},
            {null, null, null, null},
            {null, null, null, null}
    };

}