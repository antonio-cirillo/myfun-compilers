package it.unisa.nodes.expr;

import it.unisa.visitors.Visitor;

public class Identifier extends Expr {

    public Identifier(String lexeme) {
        super("(\"id\", \"" + lexeme + "\")");
        this.lexeme = lexeme;
    }

    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public String getLexeme() {
        return lexeme;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        return "(\"id\", \"" + lexeme + "\")";
    }

    private String lexeme, type;

}