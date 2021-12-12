package it.unisa.symboltable.row;

public abstract class Row {

    public Row(String lexeme) {
        this.lexeme = lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String toString() {
        return "[lexeme: " + lexeme + ", line: " + line + "]";
    }

    private String lexeme;
    private int line;

}