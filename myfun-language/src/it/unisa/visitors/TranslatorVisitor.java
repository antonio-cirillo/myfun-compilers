package it.unisa.visitors;

import it.unisa.nodes.BodyOp;
import it.unisa.nodes.FunOp;
import it.unisa.nodes.ProgramOp;
import it.unisa.nodes.expr.*;
import it.unisa.nodes.stat.*;
import it.unisa.nodes.var.*;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class TranslatorVisitor implements Visitor {

    @Override
    public Object visit(BinaryOp binaryOp) throws Exception {
        String typeOp = binaryOp.toString();

        if (typeOp.equals("StrCatOp"))
            return null;

        binaryOp.getExpr1().accept(this);

        if (typeOp.equals("AddOp"))
            fileWriter.write(" + ");
        else if (typeOp.equals("DiffOp"))
            fileWriter.write(" - ");
        else if (typeOp.equals("MulOp"))
            fileWriter.write(" * ");
        else if (typeOp.equals("DivOp"))
            fileWriter.write(" / ");
        else if (typeOp.equals("AndOp"))
            fileWriter.write(" && ");
        else if (typeOp.equals("OrOp"))
            fileWriter.write(" || ");
        else if (typeOp.equals("LTOp"))
            fileWriter.write(" < ");
        else if (typeOp.equals("LEOp"))
            fileWriter.write(" <= ");
        else if (typeOp.equals("GTOp"))
            fileWriter.write(" > ");
        else if (typeOp.equals("GEOp"))
            fileWriter.write(" >= ");
        else if (typeOp.equals("EQOp"))
            fileWriter.write(" == ");
        else if (typeOp.equals("NEOp"))
            fileWriter.write(" != ");

        binaryOp.getExpr2().accept(this);

        return null;
    }

    @Override
    public Object visit(CallFunOpExpr callFunOpExpr) throws Exception {
        return null;
    }

    @Override
    // Scrive il valore della costante
    public Object visit(ConstValue constValue) throws Exception {
        fileWriter.write(constValue.getValue());
        return null;
    }

    @Override
    // Funzione che viene gestite dalle sottoclassi
    public Object visit(Expr expr) throws Exception {
        return "";
    }

    @Override
    // Scrive il lessema dell'identificatore
    // Sostituisco il simbolo $ con _ in quanto non è valido come simbolo nel C
    public Object visit(Identifier identifier) throws Exception {
        String lexeme = identifier.getLexeme().replaceAll("[$]", "_");
        fileWriter.write(lexeme);
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
    // Scrivo il codice relativo all'istruzione di read
    public Object visit(ReadOp readOp) throws Exception {
        if (readOp.getExpr() != null) {
            fileWriter.write("\t".repeat(currentTab) + "printf(");
            readOp.getExpr().accept(this);
            fileWriter.write(");\n");
        }

        fileWriter.write("\t".repeat(currentTab) + "scanf(\"");

        int i = 0;
        ArrayList<Identifier> idList = readOp.getIdList();
        for (; i < idList.size() - 1; i++) {
            String type = convertType(idList.get(i).getType());
            if (type.equals("int") || type.equals("bool"))
                fileWriter.write("%d ");
            else if (type.equals("double"))
                fileWriter.write("%lf ");
            else if (type.equals("string"))
                fileWriter.write("%s ");
        }
        String type = convertType(idList.get(i).getType());
        if (type.equals("int") || type.equals("bool"))
            fileWriter.write("%d\", ");
        else if (type.equals("double"))
            fileWriter.write("%lf\", ");
        else if (type.equals("string"))
            fileWriter.write("%s\", ");

        i = 0;
        for (; i < idList.size() - 1; i++) {
            fileWriter.write("&");
            idList.get(i).accept(this);
            fileWriter.write(", ");
        }

        fileWriter.write("&");
        idList.get(i).accept(this);
        fileWriter.write(");\n");

        return null;
    }

    @Override
    // Scrivo il codice relativo all'istruzione di return
    public Object visit(ReturnOp returnOp) throws Exception {
        fileWriter.write("\t".repeat(currentTab) + "return ");
        returnOp.getExpr().accept(this);
        fileWriter.write(";\n");

        return null;
    }

    @Override
    // Funzione che viene gestita dalle sottoclassi
    public Object visit(Stat stat) throws Exception {
        return "";
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
    // Scrive l'inizializzazione di una varaibile
    public Object visit(IdInitOp idInitOp) throws Exception {
        idInitOp.getId().accept(this);
        fileWriter.write(" = ");
        idInitOp.getExpr().accept(this);

        return null;
    }

    @Override
    // Restituisce la modalità di input
    public Object visit(ModeOp modeOp) throws Exception {
        return modeOp.getMode();
    }

    @Override
    // Scrivo il codice relativo al parametro
    public Object visit(ParamDeclOp paramDeclOp) throws Exception {
        paramDeclOp.getType().accept(this);
        String mode = (String) paramDeclOp.getMode().accept(this);
        if (mode.equals("out")) {
            fileWriter.write("*");
        }
        fileWriter.write(" ");
        paramDeclOp.getId().accept(this);

        return null;
    }

    @Override
    // Scrive il tipo
    public Object visit(TypeOp type) throws Exception {
        String _type = convertType(type.getType());
        fileWriter.write(_type);
        return _type;
    }

    @Override
    // Gestione della dichiarazione di variabili
    public Object visit(VarDeclOp varDeclOp) throws Exception {
        fileWriter.write("\t".repeat(currentTab));
        varDeclOp.getType().accept(this);
        fileWriter.write(" ");

        ArrayList<DefaultMutableTreeNode> idList = varDeclOp.getIdList();
        int i = 0;

        for (; i < idList.size() - 1; i++) {
            DefaultMutableTreeNode id = idList.get(i);
            if (id instanceof Identifier)
                ((Identifier) id).accept(this);
            if (id instanceof IdInitOp)
                ((IdInitOp) id).accept(this);
            fileWriter.write(", ");
        }

        DefaultMutableTreeNode id = idList.get(i);
        if (id instanceof Identifier)
            ((Identifier) id).accept(this);
        if (id instanceof IdInitOp)
            ((IdInitOp) id).accept(this);
        fileWriter.write(";\n");

        return null;
    }

    @Override
    // Gestisco le dichiarazioni di variabili e istruzioni del blocco
    public Object visit(BodyOp bodyOp) throws Exception {
        ArrayList<VarDeclOp> varDeclList = bodyOp.getVarDeclList();
        if (varDeclList != null && varDeclList.size() > 0) {
            for (VarDeclOp varDecl : varDeclList) {
                varDecl.accept(this);
            }
        }
        fileWriter.write("\n");
        ArrayList<Stat> statList = bodyOp.getStatList();
        if (statList != null && statList.size() > 0) {
            for (Stat stat : statList) {
                stat.accept(this);
            }
        }

        return null;
    }

    @Override
    // Gestione della dichiarazione di funzione
    public Object visit(FunOp funOp) throws Exception {
        if (funOp.getType() == null)
            fileWriter.write("void");
        else
            funOp.getType().accept(this);

        // Definisco la funzione
        fileWriter.write(" " + funOp.getSignature() + "(");

        ArrayList <ParamDeclOp> paramDeclList = funOp.getParamDeclOp();
        int i = 0;

        if (paramDeclList != null && paramDeclList.size() > 0) {
            for (; i < paramDeclList.size() - 1; i++) {
                paramDeclList.get(i).accept(this);
                fileWriter.write(", ");
            }
            paramDeclList.get(i).accept(this);
        }

        fileWriter.write(") {\n");

        currentTab++;
        funOp.getBody().accept(this);
        currentTab--;

        fileWriter.write("}\n\n");

        return null;
    }

    @Override
    // Gestisco la traduzione del programma
    public Object visit(ProgramOp programOp) throws Exception {
        // Inizializzo il file di scrittura
        FILE.createNewFile();
        fileWriter = new FileWriter(FILE);

        // Inserisco i vari import
        fileWriter.write("#include <stdio.h>\n");
        fileWriter.write("#include <string.h>\n");
        fileWriter.write("#include <math.h>\n");
        fileWriter.write("#include <stdbool.h>\n");

        fileWriter.write("\n");

        // Gestisco la dichiarazione delle variabili globali
        for (VarDeclOp varDecl : programOp.getVarDeclList())
            varDecl.accept(this);

        fileWriter.write("\n");

        // Gestisco la dichiarazione di funzioni
        for (FunOp fun : programOp.getFunList())
            fun.accept(this);

        fileWriter.write("\n");

        // Aumento il numero di tab di 1
        currentTab++;

        // Gestisco il main
        fileWriter.write("void main() {\n");
        programOp.getBody().accept(this);

        // Chiudo il main
        fileWriter.write("}");
        fileWriter.close();

        return null;
    }

    private String convertType(String type) {
        if (type.equals("integer"))
            return "int";
        else if (type.equals("real"))
            return "double";
        else if (type.equals("string"))
            return "char*";
        else if (type.equals("bool"))
            return "bool";
        else
            return "void";
    }

    private String toSignature(String lexeme, ArrayList<String> paramsMode,
                               ArrayList<String> paramsType) {
        String string = lexeme;
        if (paramsType != null && paramsType.size() > 0) {
            string += "__";
            for (int i = 0; i < paramsMode.size(); i++)
                string += paramsMode.get(i) + "_" +
                        paramsType.get(i) + "__";
            string = string.substring(0, string.length() - 2);
        }
        return string;
    }

    private static final String FILE_NAME = "c_gen.c";
    private static final File FILE = new File(
            System.getProperty("user.dir") + "\\myfun_programs\\" + FILE_NAME);
    private static FileWriter fileWriter;
    private static int currentTab = 0;

}