package it.unisa.symboltable;

import it.unisa.symboltable.row.Row;
import it.unisa.symboltable.row.RowMethod;
import it.unisa.symboltable.row.RowVar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public final class SymbolTable {

    public static class LexemeAlreadyDefined extends Exception {
        public LexemeAlreadyDefined(String lexeme) {
            super("Lexeme " + lexeme + " is already defined in the scope");
        }
    }

    public static class TypeMismatch extends Exception {
        public TypeMismatch(String lexeme, String type, String typeAssigned) {
            super("Type Mismatch for lexeme: " + lexeme + ". Required type: " + type + ", provided: " + typeAssigned);
        }
    }

    public static class OperationNotDefined extends Exception {
        public OperationNotDefined(String operation, String type1, String type2) {
            super("Operator '" + operation + "' cannot be applied to '" + type1 + "', '" + type2 + "'");
        }

        public OperationNotDefined(String operation, String type) {
            super("Operator '" + operation + "' cannot be applied to '" + type + "'");
        }
    }

    public static class CannotResolveSymbol extends Exception {
        public CannotResolveSymbol(String lexeme) {
            super("Cannot resolve symbol '" + lexeme + "'");
        }
    }

    public static void enterScope() {
        stack.add(new HashMap<>());
    }

    public static Row lookup(String lexeme) {
        Stack<HashMap<String, Row>> _stack =
                (Stack<HashMap<String, Row>>) stack.clone();
        while (_stack.size() > 0) {
            HashMap<String, Row> _hashMap = _stack.pop();
            if (_hashMap.containsKey(lexeme))
                return _hashMap.get(lexeme);
        }
        return null;
    }

    public static void addId(String lexeme, String type) throws LexemeAlreadyDefined {
        Row row = new RowVar(type);
        if (stack.lastElement().containsKey(lexeme))
            throw new LexemeAlreadyDefined(lexeme);

        stack.lastElement().put(lexeme, row);
    }

    public static void addId(String lexeme, ArrayList<String> paramType, String returnType)
            throws LexemeAlreadyDefined {
        Row row = new RowMethod(paramType, returnType);
        if (stack.lastElement().containsKey(lexeme))
            throw new LexemeAlreadyDefined(lexeme);

        stack.lastElement().put(lexeme, row);
    }

    public static boolean probe(String lexeme) {
        Stack<HashMap<String, Row>> _stack =
                (Stack<HashMap<String, Row>>) stack.clone();
        while (_stack.size() > 0) {
            HashMap<String, Row> _hashMap = _stack.pop();
            if (_hashMap.containsKey(lexeme))
                return true;
        }
        return false;
    }

    public static HashMap<String, Row> exitScope() {
        return stack.pop();
    }

    private static final Stack<HashMap<String, Row>> stack = new Stack<>();

}
