package it.unisa.symboltable;

import it.unisa.symboltable.row.Row;
import it.unisa.symboltable.row.RowMethod;
import it.unisa.symboltable.row.RowVar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public final class SymbolTable {

    public static void enterScope() {
        stack.add(new HashMap<>());
    }

    public static Row lookup(String lexeme) {
        Stack <HashMap<String, Row>> _stack =
                (Stack<HashMap<String, Row>>) stack.clone();
        while (_stack.size() > 0) {
            HashMap <String, Row> _hashMap = _stack.pop();
            if (_hashMap.containsKey(lexeme))
                return _hashMap.get(lexeme);
        }
        return null;
    }

    public static void addId(String lexeme, String type) {
        Row row = new RowVar(type);
        stack.firstElement().put(lexeme, row);
    }

    public static void addId(String lexeme, ArrayList<String> paramType, String returnType) {
        Row row = new RowMethod(paramType, returnType);
        stack.firstElement().put(lexeme, row);
    }

    public static boolean probe(String lexeme) {
        Stack <HashMap<String, Row>> _stack =
                (Stack<HashMap<String, Row>>) stack.clone();
        while (_stack.size() > 0) {
            HashMap <String, Row> _hashMap = _stack.pop();
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
