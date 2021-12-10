package it.unisa.visitors;

import it.unisa.nodes.BodyOp;
import it.unisa.nodes.FunOp;
import it.unisa.nodes.ProgramOp;
import it.unisa.nodes.expr.*;
import it.unisa.nodes.stat.*;
import it.unisa.nodes.var.*;

public class SemanticVisitor implements Visitor {

    @Override
    public Object visit(BinaryOp binaryOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(CallFunOpExpr callFunOpExpr) throws Exception {
        return null;
    }

    @Override
    public Object visit(ConstValue constValue) throws Exception {
        return null;
    }

    @Override
    public Object visit(Expr expr) throws Exception {
        return null;
    }

    @Override
    public Object visit(Identifier identifier) throws Exception {
        return null;
    }

    @Override
    public Object visit(UnaryOp unaryOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(AssignOp assignOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(CallFunOp callFunOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(IfOp ifOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(ReadOp readOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(ReturnOp returnOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(Stat stat) throws Exception {
        return null;
    }

    @Override
    public Object visit(WhileOp whileOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(WriteOp writeOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(IdInitOp idInitOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(ModeOp modeOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(ParamDeclOp paramDeclOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(TypeOp type) throws Exception {
        return null;
    }

    @Override
    public Object visit(VarDeclOp varDeclOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(BodyOp bodyOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(FunOp funOp) throws Exception {
        return null;
    }

    @Override
    public Object visit(ProgramOp programOp) throws Exception {
        return null;
    }
}
