package it.unisa.visitors;

import it.unisa.nodes.BodyOp;
import it.unisa.nodes.FunOp;
import it.unisa.nodes.ProgramOp;
import it.unisa.nodes.expr.*;
import it.unisa.nodes.stat.*;
import it.unisa.nodes.var.*;
import it.unisa.symboltable.SymbolTable;
import it.unisa.symboltable.row.RowMethod;
import it.unisa.symboltable.row.RowVar;
import java_cup.runtime.Symbol;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class SemanticVisitor implements Visitor {

    @Override
    public Object visit(BinaryOp binaryOp) throws Exception {
        /*// Ottengo il tipo di operazione
        String op = binaryOp.toString();
        // Ottengo il tipo del primo operando
        String typeArg1 = (String) binaryOp.getExpr1().accept(this);
        // Ottengo il tipo del secondo operando
        String typeArg2 = (String) binaryOp.getExpr2().accept(this);

        // Controllo sulle operazioni aritmetiche classiche
        if (op.equals("AddOp") || op.equals("DiffOp") || op.equals("MulOp") || op.equals("DivOp")) {
            String type = ARITMETIC_TYPE[typeToInt(typeArg1)][typeToInt(typeArg2)];
            if (type == null)
                ; // throw new SymbolTable.OperationNotDefined(op, typeArg1, typeArg2);
            else
                return type;
        }

        // Controllo sulla concatenazione di stringhe
        if (op.equals("StrCatOp"))
            return "string";

        // Controllo sulle operazioni di relazione
        if (op.equals("EQOp") || op.equals("LTOp") || op.equals("LEOp") || op.equals("NEOp") ||
                op.equals("GTOp") || op.equals("GEOp")) {
            String type = RELATION_TYPE[typeToInt(typeArg1)][typeToInt(typeArg2)];
            if (type == null)
                ; // throw new SymbolTable.OperationNotDefined(op, typeArg1, typeArg2);
            else
                return type;
        }

        // Controllo sulle operazioni logiche
        if (op.equals("AndOp") || op.equals("OrOp")) {
            if (typeArg1.equals("bool") && typeArg2.equals("bool"))
                return "bool";
            else
                ; // throw new SymbolTable.OperationNotDefined(op, typeArg1, typeArg2);
        }
*/
        return null;
    }

    @Override
    public Object visit(CallFunOpExpr callFunOpExpr) throws Exception {
        /*// Ottengo il lessema della funzione
        String lexeme = (String) callFunOpExpr.getId().accept(this);
        // Ottengo le informazioni sui parametri
        ArrayList<Expr> exprList = callFunOpExpr.getExprList();
        ArrayList<ModeOp> modeList = callFunOpExpr.getModeList();

        // Definisco la firma del metodo che sto cercando di chiamare
        String signature = lexeme + "(";
        for (int i = 0; i < exprList.size(); i++) {
            signature += modeList.get(i).accept(this) + "_" +
                    exprList.get(i).accept(this) + ", ";
        }
        signature = signature.substring(0, signature.length() - 2) + ")";
        System.out.println(signature);

        RowMethod row = SymbolTable.lookupMethod(signature);

        if (row != null) {
            return row.getReturnType();
        }
*/
        return null;
    }

    @Override
    // Restituisce il tipo della costante
    public Object visit(ConstValue constValue) throws Exception {
        return constValue.getType();
    }

    @Override
    // Handle di chiamate sui sotto tipi di Expr
    // Restituisce il tipo dell'espressione
    public Object visit(Expr expr) throws Exception {
        if (expr instanceof Identifier) {
            RowVar row = SymbolTable.lookupVar((String) expr.accept(this));
            return row.getType();
        } else if (expr instanceof ConstValue)
            return expr.accept(this);
        else if (expr instanceof BinaryOp)
            return expr.accept(this);
        else if (expr instanceof UnaryOp)
            return expr.accept(this);
        else if (expr instanceof CallFunOpExpr)
            return expr.accept(this);

        return null;
    }

    @Override
    // Restituisce il lessema dell'identificatore
    public Object visit(Identifier identifier) throws Exception {
        return identifier.getLexeme();
    }

    @Override
    public Object visit(UnaryOp unaryOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(AssignOp assignOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(CallFunOp callFunOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(IfOp ifOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(ReadOp readOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(ReturnOp returnOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(Stat stat) throws Exception {
        if (stat instanceof AssignOp)
            return stat.accept(this);
        if (stat instanceof CallFunOp)
            return stat.accept(this);
        if (stat instanceof IfOp)
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
    public Object visit(WhileOp whileOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(WriteOp writeOp) throws Exception {
        return null;
    }

    @Override
    // Ritorna in un array
    // Il tipo con cui deve essere dichiarato il lessema
    // Il lessema della varaibile
    public Object visit(IdInitOp idInitOp) throws Exception {
        String type = (String) idInitOp.getExpr().accept(this);
        String lexeme = (String) idInitOp.getId().accept(this);

        return new String[]{type, lexeme};
    }

    @Override
    // Restituisce la modalità di input del parametro della funzione
    public Object visit(ModeOp modeOp) throws Exception {
        return modeOp.getMode();
    }

    @Override
    // Ritorna in un array
    // La modalità di input del parametro
    // Il tipo del parametro
    // Il lessema del parametro
    // La linea in cui è stato dichiarato il parametro
    public Object visit(ParamDeclOp paramDeclOp) throws Exception {
        String mode = (String) paramDeclOp.getMode().accept(this);
        String type = (String) paramDeclOp.getType().accept(this);
        String lexeme = (String) paramDeclOp.getValue().accept(this);
        String line = paramDeclOp.getValue().getLine() + "";

        return new String[]{mode, type, lexeme, line};
    }

    @Override
    // Ritorna il tipo
    public Object visit(TypeOp type) throws Exception {
        return type.getType();
    }

    @Override
    // Gestisce l'inserimento all'interno della symbol table delle dichiarazione di varaibili
    // Effettua un controllo sul tipo di assegnamento alla dichiarazione
    // Effettua l'inferenza di tipo sulla dichiarazione di variabili var
    public Object visit(VarDeclOp varDeclOp) throws Exception {
        // VarDeclOp è formato da un tipo e a seguire dai nomi dei lessemi
        // Memorizzo il tipo
        String type = (String) varDeclOp.getType().accept(this);
        for (DefaultMutableTreeNode id : varDeclOp.getIdList()) {
            // Se la variabile dichiarata non è inizializzata
            if (id instanceof Identifier) {
                // Salvo il nome della variabile
                String lexeme = (String) ((Identifier) id).accept(this);
                // Inserisco il lessema all'interno della symbol table
                SymbolTable.addId(lexeme, type, ((Identifier) id).getLine());
            }
            // Se la variabile dichiarata è inizializzata
            else if (id instanceof IdInitOp) {
                int line = ((IdInitOp) id).getId().getLine();
                // Ottengo il nome del lessema e il tipo con cui è stata inizializzata
                String[] results = (String[]) ((IdInitOp) id).accept(this);
                String typeAssigned = results[0];
                String lexeme = results[1];
                // Ottengo il numero della linea di codice dove effettuo questa operazione
                // Se la dichiarazione è var allora la variabile avra il tipo corrispondete al tipo dell'assegnazione
                // Se il tipo dichiarato corrisponde al tipo assegnato effettuo la stessa operazione
                if (type.equals("var") || type.equals((typeAssigned)))
                    SymbolTable.addId(lexeme, typeAssigned, line);
                    // Se il tipo assegnato non corrisponde al tipo della variabile lancio un'eccezione
                else
                    throw new SymbolTable.TypeMismatch(line, lexeme, type, typeAssigned);
            }
        }
        return null;
    }

    @Override
    // Gestisce lo scope del Body
    public Object visit(BodyOp bodyOp) throws Exception {
        ArrayList<VarDeclOp> varDeclList = bodyOp.getVarDeclList();
        if (varDeclList != null && varDeclList.size() > 0) {
            for (VarDeclOp varDecl : varDeclList) {
                varDecl.accept(this);
            }
        }
        ArrayList<Stat> statList = bodyOp.getStatList();
        if (statList != null && statList.size() > 0) {
            for (Stat stat : statList) {
                stat.accept(this);
            }
        }

        return null;
    }

    @Override
    // Gestisce lo scope di FunOp
    public Object visit(FunOp funOp) throws Exception {
        // Ottengo il lessema della funzione
        String lexeme = (String) funOp.getId().accept(this);
        // Inizializzo gli array di modalità, tipo e lessemi degli argomenti
        ArrayList<String> paramsMode = new ArrayList<>();
        ArrayList<String> paramsType = new ArrayList<>();
        ArrayList<String> paramsLexeme = new ArrayList<>();
        ArrayList<String> paramsLine = new ArrayList<>();

        // Ottengo le informazioni su tutti i parametri
        for (ParamDeclOp paramDecl : funOp.getParamDeclOp()) {
            String[] result = (String[]) paramDecl.accept(this);
            paramsMode.add(result[0]);
            paramsType.add(result[1]);
            paramsLexeme.add(result[2]);
            paramsLine.add(result[3]);
        }

        // Ottengo il tipo di ritorno della funzione
        String type;
        if (funOp.getType() != null)
            type = (String) funOp.getType().accept(this);
        else
            type = "void";

        SymbolTable.addId(lexeme, paramsMode, paramsType, type, funOp.getLine());

        // Creo lo scope relativo al corpo della funzione
        SymbolTable.enterScope();
        // Aggiungo i parametri all'interno dello scope della funzione
        for (int i = 0; i < paramsType.size(); i++) {
            SymbolTable.addId(paramsLexeme.get(i), paramsType.get(i), Integer.parseInt(paramsLine.get(i)));
        }
        // Gestisco lo scope del corpo della funzione
        funOp.getBody().accept(this);

        // Esco dallo scope della funzione
        SymbolTable.exitScope();

        return null;
    }

    @Override
    // Gestisce lo scope di ProgramOp
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
        programOp.getBody().accept(this);

        // Esco dallo scope del main
        SymbolTable.exitScope();
        // Esco dallo scope del program
        SymbolTable.exitScope();

        return null;
    }

    private int typeToInt(String type) {
        if (type.equals("integer"))
            return 0;
        if (type.equals("real"))
            return 1;
        if (type.equals("string"))
            return 2;
        if (type.equals("bool"))
            return 3;

        return -1;
    }

    private static final String[][] ARITMETIC_TYPE = {
            {"integer", "real", null, null},
            {"real", "real", null, null},
            {null, null, null, null},
            {null, null, null, null}
    };

    private static final String[][] RELATION_TYPE = {
            {"bool", "bool", null, null},
            {"bool", "bool", null, null},
            {null, null, null, null},
            {null, null, null, null}
    };

}