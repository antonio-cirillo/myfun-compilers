package it.unisa.nodes.expr;

import it.unisa.nodes.interfaces.PointedTable;
import it.unisa.symboltable.row.RowVar;
import it.unisa.visitors.Visitor;

public class Identifier
        extends Expr implements PointedTable<RowVar> {

    public Identifier(String lexeme) {
        super("(\"id\", \"" + lexeme + "\")");
        this.lexeme = lexeme;
    }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }

    public String getLexeme() {
        return lexeme;
    }

    @Override
    public RowVar getPointerToRow() {
        return row;
    }

    @Override
    public void setPointerToRow(RowVar row) {
        this.row = row;
    }

    public String toString() {
        return "(\"id\", \"" + lexeme + "\")";
    }

    private String lexeme;
    private RowVar row;

}