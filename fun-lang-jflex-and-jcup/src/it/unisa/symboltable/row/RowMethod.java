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

    private ArrayList<String> paramType;

    private String returnType;


}
