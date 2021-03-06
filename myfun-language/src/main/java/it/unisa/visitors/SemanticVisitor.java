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

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

public class SemanticVisitor implements Visitor {

    @Override
    // Restituisce il tipo risultante dall'operazione binaria
    public Object visit(BinaryOp binaryOp) throws Exception {
        // Ottengo il tipo di operazione
        String op = binaryOp.toString();

        // Controllo sulla concatenazione di stringhe
        if (op.equals("StrCatOp")) {
            Expr expr1 = binaryOp.getExpr1();
            expr1.accept(this);
            Expr expr2 = binaryOp.getExpr2();
            expr2.accept(this);
            binaryOp.setType("string");
            return "string";
        }

        // Ottengo il tipo del primo operando
        String typeArg1 = (String) binaryOp.getExpr1().accept(this);

        // Ottengo il tipo del secondo operando
        String typeArg2 = (String) binaryOp.getExpr2().accept(this);

        // Controllo sulle operazioni aritmetiche classiche
        if (op.equals("AddOp") || op.equals("DiffOp") || op.equals("MulOp") || op.equals("DivOp")) {
            String type = ARITMETIC_TYPE[typeToInt(typeArg1)][typeToInt(typeArg2)];
            if (type == null) {
                throw new SymbolTable.OperationNotDefined(binaryOp.getLine(), op, typeArg1, typeArg2);
            } else {
                binaryOp.setType(type);
                return type;
            }
        }

        // Controllo sull'operazione di potenza
        if (op.equals("PowOp")) {
            if (typeArg1.equals("integer") || typeArg1.equals("real")) {
                if (typeArg2.equals("integer") || typeArg2.equals("real")) {
                    binaryOp.setType("real");
                    return "real";
                } else {
                    throw new SymbolTable.OperationNotDefined(binaryOp.getLine(), op, typeArg1, typeArg2);
                }
            } else {
                throw new SymbolTable.OperationNotDefined(binaryOp.getLine(), op, typeArg1, typeArg2);
            }
        }

        // Controllo sull'operazione di divisione intera
        if (op.equals("DivIntOp")) {
            if (typeArg1.equals("integer")) {
                if (typeArg2.equals("integer")) {
                    binaryOp.setType("integer");
                    return "integer";
                } else {
                    throw new SymbolTable.OperationNotDefined(binaryOp.getLine(), op, typeArg1, typeArg2);
                }
            } else {
                throw new SymbolTable.OperationNotDefined(binaryOp.getLine(), op, typeArg1, typeArg2);
            }
        }

        // Controllo sulle operazioni di relazione
        if (op.equals("EQOp") || op.equals("LTOp") || op.equals("LEOp") || op.equals("NEOp") ||
                op.equals("GTOp") || op.equals("GEOp")) {
            String type = RELATION_TYPE[typeToInt(typeArg1)][typeToInt(typeArg2)];
            if (type == null)
                throw new SymbolTable.OperationNotDefined(binaryOp.getLine(), op, typeArg1, typeArg2);
            else {
                binaryOp.setType(type);
                return type;
            }
        }

        // Controllo sulle operazioni logiche
        if (op.equals("AndOp") || op.equals("OrOp")) {
            if (typeArg1.equals("bool") && typeArg2.equals("bool")) {
                binaryOp.setType("bool");
                return "bool";
            } else
                throw new SymbolTable.OperationNotDefined(binaryOp.getLine(), op, typeArg1, typeArg2);
        }

        return null;
    }

    @Override
    // Controlla se la funzione chiamante esiste e ritorna il tipo ritornato da essa
    public Object visit(CallFunOpExpr callFunOpExpr) throws Exception {
        // Ottengo il lessema della funzione
        String lexeme = callFunOpExpr.getId().getLexeme();
        // Ottengo le informazioni sui parametri
        ArrayList<Expr> exprList = callFunOpExpr.getExprList();
        ArrayList<ModeOp> modeList = callFunOpExpr.getModeList();

        // Definisco la firma del metodo che sto cercando di chiamare
        String signature = generateSignature(lexeme, modeList, exprList);
        RowMethod row = SymbolTable.lookupMethod(signature);

        if (row != null) {
            callFunOpExpr.setPointerToRow(row);
            return row.getReturnType();
        } else
            throw new SymbolTable.FunctionAreNotDefined(callFunOpExpr.getLine(), signature);
    }

    @Override
    // Restituisce il tipo della costante
    public Object visit(ConstValue constValue) throws Exception {
        return constValue.getType();
    }

    @Override
    // Gestisce le chiamate alle sottoclassi
    public Object visit(Expr expr) throws Exception {
        if (expr instanceof BinaryOp)
            return expr.accept(this);
        if (expr instanceof CallFunOpExpr)
            return expr.accept(this);
        if (expr instanceof ConstValue)
            return expr.accept(this);
        if (expr instanceof Identifier)
            return expr.accept(this);
        if (expr instanceof UnaryOp)
            return expr.accept(this);

        return null;
    }

    @Override
    // Restituisce il tipo dell'identificatore
    public Object visit(Identifier identifier) throws Exception {
        RowVar row = SymbolTable.lookupVar(identifier.getLexeme());
        if (row != null) {
            identifier.setPointerToRow(row);
            return row.getType();
        } else
            throw new SymbolTable.VarAreNotDefined(identifier.getLine(), identifier.getLexeme());
    }

    @Override
    // Restituisce il tipo risultante dall'operazione unaria
    public Object visit(UnaryOp unaryOp) throws Exception {
        // Ottengo il tipo di operazione
        String op = unaryOp.toString();
        // Ottengo il tipo dell'operando
        String typeArg = (String) unaryOp.getExpr().accept(this);

        // Controllo sull'operazioen di uminus
        if (op.equals("UminusOp")) {
            if (typeArg.equals("integer") || typeArg.equals("real")) {
                unaryOp.setType(typeArg);
                return typeArg;
            } else throw new SymbolTable.OperationNotDefined(unaryOp.getLine(), op, typeArg);
        }

        // Controllo sull'operazione di not
        if (op.equals("NotOp")) {
            if (typeArg.equals("bool")) {
                unaryOp.setType("bool");
                return "bool";
            } else throw new SymbolTable.OperationNotDefined(unaryOp.getLine(), op, typeArg);
        }

        return null;
    }

    @Override
    // Controllo se il tipo dell'identificatore coincide con quello dell'espressione assegnata
    public Object visit(AssignOp assignOp) throws Exception {
        String type = (String) assignOp.getId().accept(this);
        String typeAssigned = (String) assignOp.getExpr().accept(this);

        if (!type.equals(typeAssigned))
            throw new SymbolTable.TypeMismatch(assignOp.getId().getLine(),
                    assignOp.getId().getLexeme(), type, typeAssigned);

        return null;
    }

    @Override
    // Controlla se la funzione chiamanete esiste e ritorna il tipo ritornato da essa
    public Object visit(CallFunOp callFunOp) throws Exception {
        // Ottengo il lessema della funzione
        String lexeme = callFunOp.getId().getLexeme();
        // Ottengo le informazioni sui parametri
        ArrayList<Expr> exprList = callFunOp.getExprList();
        ArrayList<ModeOp> modeList = callFunOp.getModeList();

        // Definisco la firma del metodo che sto cercando di chiamare
        String signature = generateSignature(lexeme, modeList, exprList);
        RowMethod row = SymbolTable.lookupMethod(signature);

        if (row != null) {
            callFunOp.setPointerToRow(row);
            return row.getReturnType();
        } else
            throw new SymbolTable.FunctionAreNotDefined(callFunOp.getLine(), signature);
    }

    @Override
    // Controllo se la condizione ?? di tipo bool
    // Gestisco lo scope del body del if
    // Gestisco lo scope del body del while
    public Object visit(IfOp ifOp) throws Exception {
        // Controllo se la condizione ?? di tipo booleana
        String type = (String) ifOp.getExpr().accept(this);
        if (!type.equals("bool"))
            throw new SymbolTable.OperationNotDefined(ifOp.getExpr().getLine(),
                    "if condition", type);

        // Entro nello scope dell'if
        SymbolTable.enterScope();
        // Gestisco lo scope dell'if
        ArrayList<Object> results = (ArrayList<Object>) ifOp.getIfBody().accept(this);
        // Esco dallo scope dell'if
        SymbolTable.exitScope();

        // Se l'else non ?? presente termino i controlli
        if (ifOp.getElseBody() == null)
            return results;

        // Entro nello scope dell'else
        SymbolTable.enterScope();
        // Gestisco lo scope dell'else
        results.addAll((ArrayList<Object>) ifOp.getElseBody().accept(this));
        // Esco dallo scope dell'else
        SymbolTable.exitScope();

        return results;
    }

    @Override
    // Controllo se gli identificatori utilizzati sono stati dichiarati
    // Controllo se l'espressione da stampare ?? una stringa
    public Object visit(ReadOp readOp) throws Exception {
        for (Identifier id : readOp.getIdList())
            id.accept(this);
        if (readOp.getExpr() != null) {
            String type = (String) readOp.getExpr().accept(this);
            if (type.equals("string"))
                return null;
            else
                throw new SymbolTable.OperationNotDefined(readOp.getLine(), readOp.toString(), type);
        } else
            return null;
    }

    @Override
    // Restituisce il tipo restituito dall'operazione di return
    public Object visit(ReturnOp returnOp) throws Exception {
        ArrayList<Object> results = new ArrayList<>();
        results.add(returnOp.getExpr().accept(this));
        results.add(returnOp.getLine());
        return results;
    }

    @Override
    // Gestisce le chiamate alle sottoclassi
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
    // Controllo se la condizione ?? di tipo bool
    // Gestisco lo scope del body del while
    public Object visit(WhileOp whileOp) throws Exception {
        // Controllo se la condizione ?? di tipo booleana
        String type = (String) whileOp.getExpr().accept(this);
        if (!type.equals("bool"))
            throw new SymbolTable.OperationNotDefined(whileOp.getExpr().getLine(),
                    "while condition", type);

        // Entro nello scope del while
        SymbolTable.enterScope();
        // Gestisco lo scope del while
        ArrayList<Object> results = (ArrayList<Object>) whileOp.getBody().accept(this);
        // Esco dallo scope del while
        SymbolTable.exitScope();

        return results;
    }

    @Override
    // Controllo se l'espressione da stampare ?? una stringa
    public Object visit(WriteOp writeOp) throws Exception {
        String type = (String) writeOp.getExpr().accept(this);
        if (type.equals("string"))
            return null;
        else
            throw new SymbolTable.OperationNotDefined(writeOp.getLine(), writeOp.getType(), type);
    }

    @Override
    // Ritorna in un array
    // Il tipo con cui deve essere dichiarato il lessema
    // Il lessema della varaibile
    public Object visit(IdInitOp idInitOp) throws Exception {
        String type = (String) idInitOp.getExpr().accept(this);
        String lexeme = idInitOp.getId().getLexeme();

        return new String[]{type, lexeme};
    }

    @Override
    // Restituisce la modalit?? di input del parametro della funzione
    public Object visit(ModeOp modeOp) throws Exception {
        return modeOp.getMode();
    }

    @Override
    // Ritorna in un array
    // La modalit?? di input del parametro
    // Il tipo del parametro
    // Il lessema del parametro
    // La linea in cui ?? stato dichiarato il parametro
    public Object visit(ParamDeclOp paramDeclOp) throws Exception {
        String mode = (String) paramDeclOp.getMode().accept(this);
        String type = (String) paramDeclOp.getType().accept(this);
        String lexeme = paramDeclOp.getId().getLexeme();
        String line = paramDeclOp.getId().getLine() + "";

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
        // VarDeclOp ?? formato da un tipo e a seguire dai nomi dei lessemi
        // Memorizzo il tipo
        String type = (String) varDeclOp.getType().accept(this);
        for (DefaultMutableTreeNode id : varDeclOp.getIdList()) {
            // Se la variabile dichiarata non ?? inizializzata
            if (id instanceof Identifier) {
                // Salvo il nome della variabile
                String lexeme = ((Identifier) id).getLexeme();
                // Inserisco il lessema all'interno della symbol table
                ((Identifier) id).setPointerToRow(
                        SymbolTable.addId(lexeme, type, ((Identifier) id).getLine()));
            }
            // Se la variabile dichiarata ?? inizializzata
            else if (id instanceof IdInitOp) {
                int line = ((IdInitOp) id).getId().getLine();
                // Ottengo il nome del lessema e il tipo con cui ?? stata inizializzata
                String[] results = (String[]) ((IdInitOp) id).accept(this);
                String typeAssigned = results[0];
                String lexeme = results[1];
                // Ottengo il numero della linea di codice dove effettuo questa operazione
                // Se la dichiarazione ?? var allora la variabile avra il tipo corrispondete al tipo dell'assegnazione
                // Se il tipo dichiarato corrisponde al tipo assegnato effettuo la stessa operazione
                if (type.equals(typeAssigned))
                    ((IdInitOp) id).getId().setPointerToRow(
                            SymbolTable.addId(lexeme, typeAssigned, line));
                else if (type.equals("var")) {
                    ((IdInitOp) id).getId().setPointerToRow(
                            SymbolTable.addId(lexeme, typeAssigned, line));
                    ((IdInitOp) id).getId().getPointerToRow().setType(typeAssigned);
                }
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
        ArrayList<Object> types = new ArrayList<>();

        ArrayList<VarDeclOp> varDeclList = bodyOp.getVarDeclList();
        if (varDeclList != null && varDeclList.size() > 0) {
            for (VarDeclOp varDecl : varDeclList) {
                varDecl.accept(this);
            }
        }

        ArrayList<Stat> statList = bodyOp.getStatList();
        if (statList != null && statList.size() > 0) {
            for (Stat stat : statList) {
                if (stat instanceof ReturnOp) {
                    ArrayList<Object> result = (ArrayList<Object>) stat.accept(this);
                    if (result != null) {
                        if (result.get(0) instanceof ArrayList)
                            types.addAll(result);
                        else
                            types.add(result);
                    }
                } else
                    stat.accept(this);
            }
        }

        return types;
    }

    @Override
    // Gestisce lo scope di FunOp
    public Object visit(FunOp funOp) throws Exception {
        // Ottengo il lessema della funzione
        String lexeme = funOp.getId().getLexeme();
        // Inizializzo gli array di modalit??, tipo e lessemi degli argomenti
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

        // Aggiungo l'entry all'interno della symbol table e salvo il puntatore ad essa
        funOp.setPointerToRow(
                SymbolTable.addId(lexeme, paramsMode, paramsType, type, funOp.getLine()));

        // Creo lo scope relativo al corpo della funzione
        SymbolTable.enterScope();
        // Aggiungo i parametri all'interno dello scope della funzione
        for (int i = 0; i < paramsType.size(); i++) {
            SymbolTable.addId(paramsLexeme.get(i), paramsType.get(i), Integer.parseInt(paramsLine.get(i)));
            if (paramsMode.get(i).equals("out"))
                SymbolTable.lookupVar(paramsLexeme.get(i)).setMode("out");
        }

        // Gestisco lo scope del corpo della funzione
        ArrayList<Object> results = (ArrayList<Object>) funOp.getBody().accept(this);
        for (Object returns : results) {
            String t = (String) ((ArrayList<Object>) returns).get(0);
            if (!type.equals(t))
                throw new SymbolTable.ReturnMismatch(
                        (int) ((ArrayList<Object>) returns).get(1),
                        type, t);
        }

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

    private String generateSignature(String lexeme, ArrayList<ModeOp> paramsMode,
                                     ArrayList<Expr> paramsType) throws Exception {
        String string = lexeme + "(";
        if (paramsType != null && paramsType.size() > 0) {
            for (int i = 0; i < paramsMode.size(); i++)
                string += paramsMode.get(i).accept(this) + "_" +
                        paramsType.get(i).accept(this) + ", ";
            string = string.substring(0, string.length() - 2);
        }
        string += ")";
        return string;
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
            {null, null, "bool", null},
            {null, null, null, "bool"}
    };

}