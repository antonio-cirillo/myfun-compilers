package it.unisa.visitors.scoping;

import it.unisa.nodes.BodyOp;
import it.unisa.nodes.FunOp;
import it.unisa.nodes.ProgramOp;
import it.unisa.nodes.expr.*;
import it.unisa.nodes.stat.*;
import it.unisa.nodes.var.*;

public interface Visitor {

    Object visit(ProgramOp programOp) throws Exception;

    Object visit(VarDeclOp varDeclOp) throws Exception;

    Object visit(TypeOp typeOp) throws Exception;

    Object visit(IdInitOp idInitOp) throws Exception;

    Object visit(Value value) throws Exception;

    Object visit(FunOp funOp) throws Exception;

    Object visit(ParamDeclOp paramDeclOp) throws Exception;

    Object visit(ModeOp modeOp) throws Exception;

    Object visit(BodyOp bodyOp) throws Exception;

    Object visit(Stat stat) throws Exception;

    Object visit(IfStatOp ifStatOp) throws Exception;

    Object visit(WhileOp whileOp) throws Exception;

    Object visit(BinaryOp bynaryOp) throws Exception;

    Object visit(UnaryOp unaryOp) throws Exception;

    Object visit(ReadOp readOp) throws Exception;

    Object visit(CallFunOpExpr callFunOpExpr) throws Exception;

    Object visit(CallFunOpStat callFunOpStat) throws Exception;

    Object visit(ReturnOp returnOp) throws Exception;

    Object visit(AssignOp assignOp) throws Exception;

    Object visit(Expr expr) throws Exception;

    Object visit(WriteOp writeOp) throws Exception;

}