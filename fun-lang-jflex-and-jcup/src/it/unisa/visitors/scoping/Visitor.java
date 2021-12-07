package it.unisa.visitors.scoping;

import it.unisa.nodes.FunOp;
import it.unisa.nodes.ProgramOp;
import it.unisa.nodes.expr.Expr;
import it.unisa.nodes.expr.Value;
import it.unisa.nodes.var.IdInitOp;
import it.unisa.nodes.var.TypeOp;
import it.unisa.nodes.var.VarDeclOp;

public interface Visitor {

    Object visit(ProgramOp programOp);
    Object visit(VarDeclOp varDeclOp);
    Object visit(TypeOp typeOp);
    Object visit(IdInitOp idInitOp);
    Object visit(Value value);
    Object visit(Expr expr);
    Object visit(FunOp funOp);

}
