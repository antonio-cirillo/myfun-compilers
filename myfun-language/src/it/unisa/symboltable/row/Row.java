package it.unisa.symboltable.row;

import it.unisa.nodes.interfaces.Lineable;

public abstract class Row implements Lineable {

    public Row(String lexeme) {
        this.lexeme = lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public void setLine(int line) {
        this.line = line;
    }

    public String toString() {
        return "[lexeme: " + lexeme + ", line: " + line + "]";
    }

    private String lexeme;
    private int line;

}