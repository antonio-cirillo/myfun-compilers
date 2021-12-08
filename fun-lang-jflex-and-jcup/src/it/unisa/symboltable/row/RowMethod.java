package it.unisa.symboltable.row;

import java.util.ArrayList;

public class RowMethod extends Row {

    public RowMethod(ArrayList<String> paramType, String returnType) {
        this.paramType = paramType;
        this.returnType = returnType;
    }

    public ArrayList<String> getParamType() {
        return paramType;
    }

    public void setParamType(ArrayList<String> paramType) {
        this.paramType = paramType;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String toString() {
        String string = "type:";
        if (paramType.size() > 0) {
            for (String type : paramType)
                string += type + "x";
            string = string.substring(0, string.length() - 1);
        }
        string += "->" + returnType;
        return string;
    }

    private ArrayList<String> paramType;
    private String returnType;

}