package it.unisa.symboltable;

import it.unisa.symboltable.row.RowMethod;
import it.unisa.symboltable.row.RowVar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class SymbolTable {

    public static class TypeMismatch extends Exception {
        public TypeMismatch(int line, String lexeme, String type, String typeAssigned) {
            super("Error at line: " + line + ".\nType Mismatch for lexeme: '" + lexeme +
                    "'. Required type: '" + type + "', provided: '" + typeAssigned + "'.");
        }
    }

    public static class LexemeAlreadyDefined extends Exception {
        public LexemeAlreadyDefined(int line, String lexeme, int lineAssigned) {
            super("Error at line: " + line + ".\n'" + lexeme +
                    "' is already defined in the scope at line: " + lineAssigned + ".");
        }
    }

    public static class OperationNotDefined extends Exception {
        public OperationNotDefined(int line, String op, String type1, String type2) {
            super("Error at line: " + line + ".\nOperator '" + getOpByNodeName(op) +
                    "' cannot be applied to '" + type1 + "', '" + type2 + "'.");
        }

        public OperationNotDefined(int line, String op, String type) {
            super("Error at line: " + line + ".\nOperator '" + getOpByNodeName(op) +
                    "' cannot be applied to '" + type + "'.");
        }

        private static String getOpByNodeName(String nodeName) {
            if (nodeName.equals("AddOp"))
                return "+";
            else if (nodeName.equals("DiffOp"))
                return "-";
            else if (nodeName.equals("MulOp"))
                return "*";
            else if (nodeName.equals("DivOp"))
                return "/";
            else if (nodeName.equals("PowOp"))
                return "^";
            else if (nodeName.equals("DivIntOp"))
                return "div";
            else if (nodeName.equals("StrCatOp"))
                return "&";
            else if (nodeName.equals("GT"))
                return ">";
            else if (nodeName.equals("GE"))
                return ">=";
            else if (nodeName.equals("LT"))
                return "<";
            else if (nodeName.equals("LE"))
                return "<=";
            else if (nodeName.equals("EQ"))
                return "=";
            else if (nodeName.equals("AndOp"))
                return "and";
            else if (nodeName.equals("OrOp"))
                return "or";
            else if (nodeName.equals("write"))
                return "?";
            else if (nodeName.equals("writeln"))
                return "?.";
            else if (nodeName.equals("writeb"))
                return "?,";
            else if (nodeName.equals("writet"))
                return "?:";
            else if (nodeName.equals("ReadOp"))
                return "%";
            else if (nodeName.equals("AssignOp"))
                return ":=";
            else
                return nodeName;
        }
    }

    public static class FunctionAreNotDefined extends Exception {
        public FunctionAreNotDefined(int line, String signature) {
            super("Error at line: " + line + ".\nFunction '" + signature +
                    "' are not defined.");
        }
    }

    public static class StringConcatNotDefined extends Exception {
        public StringConcatNotDefined(int line) {
            super("Error at line: " + line + ".\nString concatenation can be applied " +
                    "between variables, functions and constants.");
        }
    }

    public static class VarAreNotDefined extends Exception {
        public VarAreNotDefined(int line, String lexeme) {
            super("Error at line: " + line + ".\nVar '" + lexeme +
                    "' are not defined.");
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

    public static RowVar addId(String lexeme, String type, int line) throws LexemeAlreadyDefined {
        RowVar row = new RowVar(lexeme, type);
        if (STACK_VAR.lastElement().containsKey(lexeme)) {
            int lineAlreadyDefined = STACK_VAR.lastElement().get(lexeme).getLine();
            throw new LexemeAlreadyDefined(line, lexeme, lineAlreadyDefined);
        }
        row.setLine(line);
        STACK_VAR.lastElement().put(lexeme, row);

        return row;
    }

    public static RowMethod addId(String lexeme, ArrayList<String> paramsMode,
                             ArrayList<String> paramsType, String returnType, int line)
            throws LexemeAlreadyDefined {
        RowMethod row = new RowMethod(lexeme, paramsMode, paramsType, returnType);
        if (STACK_METHOD.lastElement().containsKey(row.getSignature())) {
            RowMethod rowAlreadyDefined = STACK_METHOD.lastElement().get(row.getSignature());
            throw new LexemeAlreadyDefined(line, rowAlreadyDefined.getSignature(),
                    rowAlreadyDefined.getLine());
        }
        row.setLine(line);
        STACK_METHOD.lastElement().put(row.getSignature(), row);

        return row;
    }

    public static void exitScope() {
        STACK_VAR.pop();
        STACK_METHOD.pop();
    }

    private static final Stack<HashMap<String, RowVar>> STACK_VAR = new Stack<>();
    private static final Stack<HashMap<String, RowMethod>> STACK_METHOD = new Stack<>();

}