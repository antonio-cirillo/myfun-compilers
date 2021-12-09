package it.unisa.nodes.expr;

public class Identifier extends Expr {

    public Identifier(String lexeme) {
        super("(\"id\", \"" + lexeme + "\")");
        this.lexeme = lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }

    public String toString() {
        return "(\"id\", \"" + lexeme + "\")";
    }

    private String lexeme;

}