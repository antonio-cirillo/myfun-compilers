package it.unisa.visitors;

import it.unisa.nodes.BodyOp;
import it.unisa.nodes.FunOp;
import it.unisa.nodes.ProgramOp;
import it.unisa.nodes.expr.*;
import it.unisa.nodes.stat.*;
import it.unisa.nodes.var.*;

public interface Visitor {

    Object visit(BinaryOp binaryOp) throws Exception;

    Object visit(CallFunOpExpr callFunOpExpr) throws Exception;

    Object visit(ConstValue constValue) throws Exception;

    Object visit(Expr expr) throws Exception;

    Object visit(Identifier identifier) throws Exception;

    Object visit(UnaryOp unaryOp) throws Exception;

    Object visit(AssignOp assignOp) throws Exception;

    Object visit(CallFunOp callFunOp) throws Exception;

    Object visit(IfOp ifOp) throws Exception;

    Object visit(ReadOp readOp) throws Exception;

    Object visit(ReturnOp returnOp) throws Exception;

    Object visit(Stat stat) throws Exception;

    Object visit(WhileOp whileOp) throws Exception;

    Object visit(WriteOp writeOp) throws Exception;

    Object visit(IdInitOp idInitOp) throws Exception;

    Object visit(ModeOp modeOp) throws Exception;

    Object visit(ParamDeclOp paramDeclOp) throws Exception;

    Object visit(TypeOp type) throws Exception;

    Object visit(VarDeclOp varDeclOp) throws Exception;

    Object visit(BodyOp bodyOp) throws Exception;

    Object visit(FunOp funOp) throws Exception;

    Object visit(ProgramOp programOp) throws Exception;

}