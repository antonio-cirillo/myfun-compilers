package it.unisa.symboltable.row;

import it.unisa.nodes.var.ModeOp;

import java.util.ArrayList;

public class RowMethod extends Row {

    public RowMethod(String lexeme, ArrayList<String> paramsMode,
                     ArrayList<String> paramsType, String returnType) {
        super(lexeme);
        this.paramsMode = paramsMode;
        this.paramsType = paramsType;
        this.returnType = returnType;
    }

    public ArrayList<String> getParamsType() {
        return paramsType;
    }

    public void setParamsType(ArrayList<String> paramType) {
        this.paramsType = paramType;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String toSignature() {
        String string = super.getLexeme() + "(";
        if (paramsType.size() > 0) {
            for (int i = 0; i < paramsMode.size(); i++)
                string += paramsMode.get(i) + "_" + paramsType.get(i) + ", ";
            string = string.substring(0, string.length() - 2);
        }
        string += ")";
        return string;
    }

    public String toString() {
        return super.toString() + "[signature: " + toSignature() + "]";
    }

    private ArrayList<String> paramsMode, paramsType;
    private String returnType;

}