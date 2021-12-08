package it.unisa.visitors.scoping;

import it.unisa.nodes.BodyOp;
import it.unisa.nodes.FunOp;
import it.unisa.nodes.ProgramOp;
import it.unisa.nodes.expr.Expr;
import it.unisa.nodes.expr.Value;
import it.unisa.nodes.stat.IfStatOp;
import it.unisa.nodes.stat.Stat;
import it.unisa.nodes.stat.WhileOp;
import it.unisa.nodes.var.*;

public interface Visitor {

    Object visit(ProgramOp programOp);

    Object visit(VarDeclOp varDeclOp);

    Object visit(TypeOp typeOp);

    Object visit(IdInitOp idInitOp);

    Object visit(Value value);

    Object visit(Expr expr);

    Object visit(FunOp funOp);

    Object visit(ParamDeclOp paramDeclOp);

    Object visit(ModeOp modeOp);

    Object visit(BodyOp bodyOp);

    Object visit(Stat stat);

    Object visit(IfStatOp ifStatOp);

    Object visit(WhileOp whileOp);

}