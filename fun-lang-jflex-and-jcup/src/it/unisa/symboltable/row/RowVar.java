package it.unisa.symboltable.row;

public class RowVar extends Row {

    public RowVar(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return "type:" + type;
    }

    private String type;

}