package it.unisa.symboltable;

import it.unisa.nodes.var.ModeOp;
import it.unisa.symboltable.row.RowMethod;
import it.unisa.symboltable.row.RowVar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class SymbolTable {

    public static class TypeMismatch extends Exception {
        public TypeMismatch(int line, String lexeme, String type, String typeAssigned) {
            super("Error at line: " + line + ".\nType Mismatch for lexeme: " + lexeme +
                    ". Required type: " + type + ", provided: " + typeAssigned);
        }
    }

    public static class LexemeAlreadyDefined extends Exception {
        public LexemeAlreadyDefined(int line, String lexeme, int lineAssigned) {
            super("Error at line: " + line + ".\nLexeme " + lexeme +
                    " is already defined in the scope at line: " + lineAssigned);
        }
    }

    public static void enterScope() {
        STACK_VAR.add(new HashMap<>());
        STACK_METHOD.add(new HashMap<>());
    }

    public static RowVar lookupVar(String lexeme) {
        Stack<HashMap<String, RowVar>> _stack_var =
                (Stack<HashMap<String, RowVar>>) STACK_VAR.clone();
        while (_stack_var.size() > 0) {
            HashMap<String, RowVar> _hashMap = _stack_var.pop();
            if (_hashMap.containsKey(lexeme))
                return _hashMap.get(lexeme);
        }
        return null;
    }

    public static RowMethod lookupMethod(String signature) {
        Stack<HashMap<String, RowMethod>> _stack_method =
                (Stack<HashMap<String, RowMethod>>) STACK_METHOD.clone();
        while (_stack_method.size() > 0) {
            HashMap<String, RowMethod> _hashMap = _stack_method.pop();
            if (_hashMap.containsKey(signature))
                return _hashMap.get(signature);
        }
        return null;
    }

    public static void addId(String lexeme, String type, int line) throws LexemeAlreadyDefined {
        RowVar row = new RowVar(lexeme, type);
        if (STACK_VAR.lastElement().containsKey(lexeme)) {
            int lineAlreadyDefined = STACK_VAR.lastElement().get(lexeme).getLine();
            throw new LexemeAlreadyDefined(line, lexeme, lineAlreadyDefined);
        }
        row.setLine(line);
        STACK_VAR.lastElement().put(lexeme, row);
    }

    public static void addId(String lexeme, ArrayList<String> paramsMode,
                             ArrayList<String> paramsType, String returnType, int line)
            throws LexemeAlreadyDefined {
        RowMethod row = new RowMethod(lexeme, paramsMode, paramsType, returnType);
        if (STACK_METHOD.lastElement().containsKey(row.toSignature())) {
            RowMethod rowAlreadyDefined = STACK_METHOD.lastElement().get(row.toSignature());
            throw new LexemeAlreadyDefined(line, rowAlreadyDefined.toSignature(), rowAlreadyDefined.getLine());
        }
        row.setLine(line);
        STACK_METHOD.lastElement().put(row.toSignature(), row);
    }

    public static void exitScope() {
        HashMap<String, RowVar> hashMap = STACK_VAR.pop();
        HashMap<String, RowMethod> hashMap2 = STACK_METHOD.pop();
        System.out.println("Var HashMap: " + hashMap);
        System.out.println("Method HashMap: " + hashMap2);
    }

    private static final Stack<HashMap<String, RowVar>> STACK_VAR = new Stack<>();
    private static final Stack<HashMap<String, RowMethod>> STACK_METHOD = new Stack<>();

}