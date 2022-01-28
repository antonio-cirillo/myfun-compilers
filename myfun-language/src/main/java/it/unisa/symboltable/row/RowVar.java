package it.unisa.symboltable.row;

public class RowVar extends Row {

    public RowVar(String lexeme, String type) {
        super(lexeme);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMode() {
        return (mode == null) ? "in" : mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String toString() {
        return super.toString() + "[mode: " + getMode() + ", type: " + type + "]";
    }

    private String type, mode;

}