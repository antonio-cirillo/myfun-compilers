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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TranslatorVisitor implements Visitor {

    @Override
    public Object visit(BinaryOp binaryOp) throws Exception {
        String typeOp = binaryOp.toString();

        fileWriter.write("(");

        if (typeOp.equals("StrCatOp")) {
            fileWriter.write("concat_string(");
            writeExprInString(binaryOp.getExpr1());
            fileWriter.write(", ");
            writeExprInString(binaryOp.getExpr2());
            fileWriter.write(")");
            fileWriter.write(")");

            return null;
        }

        if (typeOp.equals("PowOp")) {
            fileWriter.write("pow(");
            binaryOp.getExpr1().accept(this);
            fileWriter.write(", ");
            binaryOp.getExpr2().accept(this);
            fileWriter.write(")");
            fileWriter.write(")");

            return null;
        }

        if ((binaryOp.getExpr1() instanceof Identifier &&
                ((Identifier) binaryOp.getExpr1()).getPointerToRow().getType().equals("string")) ||
                (binaryOp.getExpr1() instanceof ConstValue &&
                        ((ConstValue) binaryOp.getExpr1()).getType().equals("string"))) {
            fileWriter.write("strcmp(");
            binaryOp.getExpr1().accept(this);
            fileWriter.write(", ");
            binaryOp.getExpr2().accept(this);
            fileWriter.write(")");

            if (typeOp.equals("LTOp"))
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

            fileWriter.write("0");
            fileWriter.write(")");

            return null;
        }

        binaryOp.getExpr1().accept(this);

        if (typeOp.equals("AddOp"))
            fileWriter.write(" + ");
        else if (typeOp.equals("DiffOp"))
            fileWriter.write(" - ");
        else if (typeOp.equals("MulOp"))
            fileWriter.write(" * ");
        else if (typeOp.equals("DivOp"))
            fileWriter.write(" / ");
        else if (typeOp.equals("DivIntOp"))
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
        fileWriter.write(")");

        return null;
    }

    @Override
    // Scrivo il codice relativo ad una chiamata a funzione
    public Object visit(CallFunOpExpr callFunOpExpr) throws Exception {
        ArrayList<ModeOp> paramsMode = callFunOpExpr.getModeList();
        ArrayList<Expr> params = callFunOpExpr.getExprList();

        fileWriter.write(callFunOpExpr.getPointerToRow().getLexeme() + "(");

        if (params != null && params.size() > 0) {
            int i = 0;
            for (; i < paramsMode.size() - 1; i++) {
                if (paramsMode.get(i).getMode().equals("out"))
                    fileWriter.write("&");
                params.get(i).accept(this);
                fileWriter.write(", ");
            }
            if (paramsMode.get(i).getMode().equals("out"))
                fileWriter.write("&");
            params.get(i).accept(this);
        }

        fileWriter.write(")");

        return null;
    }

    @Override
    // Scrive il valore della costante
    public Object visit(ConstValue constValue) throws Exception {
        if (constValue.getType().equals("string")) {
            fileWriter.write("\"");
            fileWriter.write(constValue.getValue());
            fileWriter.write("\"");
        } else
            fileWriter.write(constValue.getValue());

        return null;
    }

    @Override
    // Gestisce le chiamate alle sottoclassi
    public Object visit(Expr expr) throws Exception {
        if (expr instanceof BinaryOp)
            return expr.accept(this);
        if (expr instanceof CallFunOpExpr)
            return expr.accept(this);
        if (expr instanceof ConstValue)
            return expr.accept(this);
        if (expr instanceof Identifier)
            return expr.accept(this);
        if (expr instanceof UnaryOp)
            return expr.accept(this);

        return null;
    }

    @Override
    // Scrive il lessema dell'identificatore
    // Sostituisco il simbolo $ con _ in quanto non ?? valido come simbolo nel C
    public Object visit(Identifier identifier) throws Exception {
        String lexeme = identifier.getLexeme().replaceAll("[$]", "_");
        if (identifier.getPointerToRow() != null &&
                identifier.getPointerToRow().getMode().equals("out"))
            fileWriter.write("*");
        fileWriter.write(lexeme);

        return null;
    }

    @Override
    public Object visit(UnaryOp unaryOp) throws Exception {
        String typeOp = unaryOp.toString();
        fileWriter.write("(");

        if (typeOp.equals("UminusOp"))
            fileWriter.write(" -");
        else
            fileWriter.write(" !");

        unaryOp.getExpr().accept(this);
        fileWriter.write(")");

        return null;
    }

    @Override
    // Scrivo il codice relativo all'assegnazione
    public Object visit(AssignOp assignOp) throws Exception {
        fileWriter.write("\t".repeat(currentTab));

        if (assignOp.getId().getPointerToRow().getType().equals("string")) {
            assignOp.getId().accept(this);
            fileWriter.write(" = malloc(sizeof(char) * strlen(");
            assignOp.getExpr().accept(this);
            fileWriter.write("));\n");
            fileWriter.write("\t".repeat(currentTab));
            fileWriter.write("strcpy(");
            assignOp.getId().accept(this);
            fileWriter.write(", ");
            assignOp.getExpr().accept(this);
            fileWriter.write(")");
        } else {
            assignOp.getId().accept(this);
            fileWriter.write(" = ");
            assignOp.getExpr().accept(this);
        }
        fileWriter.write(";\n");

        return null;
    }

    @Override
    // Scrivo il codice relativo ad una chiamata a funzione
    public Object visit(CallFunOp callFunOp) throws Exception {
        ArrayList<ModeOp> paramsMode = callFunOp.getModeList();
        ArrayList<Expr> params = callFunOp.getExprList();

        fileWriter.write("\t".repeat(currentTab) + callFunOp.getPointerToRow().getLexeme() + "(");

        if (params != null && params.size() > 0) {
            int i = 0;
            for (; i < paramsMode.size() - 1; i++) {
                if (paramsMode.get(i).getMode().equals("out"))
                    fileWriter.write("&");
                params.get(i).accept(this);
                fileWriter.write(", ");
            }
            if (paramsMode.get(i).getMode().equals("out"))
                fileWriter.write("&");
            params.get(i).accept(this);
        }

        fileWriter.write(");");

        return null;
    }

    @Override
    // Scrivo il codice relativo all'if
    public Object visit(IfOp ifOp) throws Exception {
        fileWriter.write("\t".repeat(currentTab) + "if(");
        ifOp.getExpr().accept(this);
        fileWriter.write(") {\n");

        currentTab++;
        ifOp.getIfBody().accept(this);
        currentTab--;

        fileWriter.write("\t".repeat(currentTab) + "}\n");

        if (ifOp.getElseBody() != null) {
            fileWriter.write("\t".repeat(currentTab) + "else {\n");
            currentTab++;
            ifOp.getElseBody().accept(this);
            currentTab--;
            fileWriter.write("\t".repeat(currentTab) + "}\n");
        }

        return null;
    }

    @Override
    // Scrivo il codice relativo all'istruzione di read
    public Object visit(ReadOp readOp) throws Exception {
        if (readOp.getExpr() != null) {
            fileWriter.write("\t".repeat(currentTab) + "printf(");
            if (!(readOp.getExpr() instanceof ConstValue))
                fileWriter.write("\"%s\", ");
            writeExprInString(readOp.getExpr());
            fileWriter.write(");\n");
        }

        int i = 0;
        ArrayList<Identifier> idList = readOp.getIdList();
        for (; i < idList.size(); i++) {
            String type = convertType(idList.get(i).getPointerToRow().getType());
            if (type.equals("int") || type.equals("bool")) {
                fileWriter.write("\t".repeat(currentTab) + "scanf(\"%d\", &");
                idList.get(i).accept(this);
            } else if (type.equals("double")) {
                fileWriter.write("\t".repeat(currentTab) + "scanf(\"%lf\", &");
                idList.get(i).accept(this);
            } else if (type.equals("char*")) {
                fileWriter.write("\t".repeat(currentTab));
                idList.get(i).accept(this);
                fileWriter.write(" = leggiStringa(");
            }
            fileWriter.write(");\n");
            fileWriter.write("\t".repeat(currentTab));
            fileWriter.write("fflush(stdin);\n");
        }

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
    // Gestisce le chiamate alle sottoclassi
    public Object visit(Stat stat) throws Exception {
        if (stat instanceof AssignOp)
            return stat.accept(this);
        if (stat instanceof CallFunOp)
            return stat.accept(this);
        if (stat instanceof IfOp)
            return stat.accept(this);
        if (stat instanceof ReadOp)
            return stat.accept(this);
        if (stat instanceof ReturnOp)
            return stat.accept(this);
        if (stat instanceof WhileOp)
            return stat.accept(this);
        if (stat instanceof WriteOp)
            return stat.accept(this);

        return null;
    }

    @Override
    // Scrivo il codice relativo al while
    public Object visit(WhileOp whileOp) throws Exception {
        fileWriter.write("\t".repeat(currentTab) + "while(");
        whileOp.getExpr().accept(this);
        fileWriter.write(") {\n");

        currentTab++;
        whileOp.getBody().accept(this);
        currentTab--;

        fileWriter.write("\t".repeat(currentTab) + "}\n");

        return null;
    }

    @Override
    public Object visit(WriteOp writeOp) throws Exception {
        fileWriter.write("\t".repeat(currentTab) + "printf(");
        if (!(writeOp.getExpr() instanceof ConstValue))
            fileWriter.write("\"%s\", ");
        writeExprInString(writeOp.getExpr());
        fileWriter.write(");\n");

        String type = writeOp.getType();
        if (type.equals("writeln"))
            fileWriter.write("\t".repeat(currentTab) + "printf(\"\\n\");");
        else if (type.equals("writeb"))
            fileWriter.write("\t".repeat(currentTab) + "printf(\" \");");
        else if (type.equals("writet"))
            fileWriter.write("\t".repeat(currentTab) + "printf(\"\\t\");");

        fileWriter.write("\n");

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
    // Restituisce la modalit?? di input
    public Object visit(ModeOp modeOp) throws Exception {
        return modeOp.getMode();
    }

    @Override
    // Scrivo il codice relativo al parametro
    public Object visit(ParamDeclOp paramDeclOp) throws Exception {
        paramDeclOp.getType().accept(this);
        String mode = (String) paramDeclOp.getMode().accept(this);
        if (mode.equals("out"))
            fileWriter.write("*");

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
        ArrayList<DefaultMutableTreeNode> idList = varDeclOp.getIdList();
        int i = 0;

        for (; i < idList.size(); i++) {
            DefaultMutableTreeNode id = idList.get(i);
            if (id instanceof Identifier) {
                fileWriter.write("\t".repeat(currentTab));
                fileWriter.write(
                        convertType(((Identifier) id).getPointerToRow().getType()));
                fileWriter.write(" ");
                ((Identifier) id).accept(this);
                if (((Identifier) id).getPointerToRow().getType().equals("string")) {
                    fileWriter.write(" = malloc(sizeof(char) * MAXCHAR)");
                }
            }
            if (id instanceof IdInitOp) {
                fileWriter.write("\t".repeat(currentTab));
                fileWriter.write(
                        convertType(((IdInitOp) id).getId().getPointerToRow().getType()));
                fileWriter.write(" ");
                ((IdInitOp) id).accept(this);
            }
            fileWriter.write(";\n");
        }

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
            fileWriter.write("\n");
        }
        ArrayList<Stat> statList = bodyOp.getStatList();
        if (statList != null && statList.size() > 0) {
            int i = 0;
            for (; i < statList.size() - 1; i++) {
                statList.get(i).accept(this);
                fileWriter.write("\n");
            }
            statList.get(i).accept(this);
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
        fileWriter.write(" " + funOp.getPointerToRow().getLexeme() + "(");

        ArrayList<ParamDeclOp> paramDeclList = funOp.getParamDeclOp();
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
        if (!(new File("test_files" + File.separator + "c_out" + File.separator)).exists()) {
            Files.createDirectory(Paths.get("test_files" + File.separator + "c_out" + File.separator));
        }
        FILE = new File("test_files" + File.separator + "c_out" + File.separator + FILE_NAME);
        FILE.createNewFile();
        fileWriter = new FileWriter(FILE);

        // Inserisco i vari import
        fileWriter.write("#include <stdio.h>\n");
        fileWriter.write("#include <stdlib.h>\n");
        fileWriter.write("#include <string.h>\n");
        fileWriter.write("#include <math.h>\n");
        fileWriter.write("#include <unistd.h>\n");
        fileWriter.write("#include <stdbool.h>\n");
        fileWriter.write("\n#define MAXCHAR 512\n");

        fileWriter.write("\n");

        fileWriter.write("char* int_to_string(int number) {\n");
        fileWriter.write("\tchar* buffer = malloc(sizeof(char));\n");
        fileWriter.write("\tsprintf(buffer, \"%d\", number);\n");
        fileWriter.write("\treturn buffer;\n");
        fileWriter.write("}\n\n");

        fileWriter.write("char* double_to_string(double number) {\n");
        fileWriter.write("\tchar* buffer = malloc(sizeof(char));\n");
        fileWriter.write("\tsprintf(buffer, \"%.2f\", number);\n");
        fileWriter.write("\treturn buffer;\n");
        fileWriter.write("}\n\n");

        fileWriter.write("char* bool_to_string(bool flag) {\n");
        fileWriter.write("\tchar* buffer = malloc(sizeof(char));\n");
        fileWriter.write("\tif(flag == true)\n");
        fileWriter.write("\t\tbuffer = \"true\";\n");
        fileWriter.write("\telse\n");
        fileWriter.write("\t\tbuffer = \"false\";\n");
        fileWriter.write("\treturn buffer;\n");
        fileWriter.write("}\n\n");

        fileWriter.write("char* leggiStringa() {\n");
        fileWriter.write("\tchar* buffer = malloc(sizeof(char) * MAXCHAR);\n");
        fileWriter.write("\tscanf(\"%s\", buffer);\n");
        fileWriter.write("\treturn buffer;\n");
        fileWriter.write("}\n\n");

        fileWriter.write("char* concat_string(char* str1, char* str2) {\n");
        fileWriter.write("\tchar* buffer = malloc(sizeof(char) * MAXCHAR);\n");
        fileWriter.write(("\t*buffer = '\\0';\n"));
        fileWriter.write(("\tstrcat(buffer, str1);\n"));
        fileWriter.write(("\tstrcat(buffer, str2);\n"));
        fileWriter.write("\treturn buffer;\n");
        fileWriter.write("}\n\n");

        // Gestisco la dichiarazione delle variabili globali
        for (VarDeclOp varDecl : programOp.getVarDeclList())
            varDecl.accept(this);

        fileWriter.write("\n");

        // Gestisco la dichiarazione di funzioni
        for (FunOp fun : programOp.getFunList())
            fun.accept(this);

        // Aumento il numero di tab di 1
        currentTab++;

        // Gestisco il main
        fileWriter.write("int main() {\n");
        programOp.getBody().accept(this);

        // Chiudo il main
        fileWriter.write("\n\tprintf(\"\\nExecution finished. The program will close in three seconds.\\n\");\n");
        fileWriter.write("\tsleep(3);\n\n");
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

    private void writeExprInString(Expr expr) throws Exception {
        if (expr instanceof UnaryOp) {
            String type = ((UnaryOp) expr).getType();
            if (type.equals("integer"))
                fileWriter.write("int_to_string(");
            else if (type.equals("real"))
                fileWriter.write("double_to_string(");
            else if (type.equals("bool"))
                fileWriter.write("bool_to_string(");
            expr.accept(this);
            if (!type.equals("string"))
                fileWriter.write(")");
        } else if (expr instanceof BinaryOp) {
            String type = ((BinaryOp) expr).getType();
            if (type.equals("integer"))
                fileWriter.write("int_to_string(");
            else if (type.equals("real"))
                fileWriter.write("double_to_string(");
            else if (type.equals("bool"))
                fileWriter.write("bool_to_string(");
            expr.accept(this);
            if (!type.equals("string"))
                fileWriter.write(")");
        } else if (expr instanceof ConstValue) {
            String type = ((ConstValue) expr).getType();
            if (type.equals("integer"))
                fileWriter.write("int_to_string(");
            else if (type.equals("real"))
                fileWriter.write("double_to_string(");
            else if (type.equals("bool"))
                fileWriter.write("bool_to_string(");
            expr.accept(this);
            if (!type.equals("string"))
                fileWriter.write(")");
        } else if (expr instanceof CallFunOpExpr) {
            String typeReturned = ((CallFunOpExpr) expr).getPointerToRow().getReturnType();
            if (typeReturned.equals("integer"))
                fileWriter.write("int_to_string(");
            else if (typeReturned.equals("real"))
                fileWriter.write("double_to_string(");
            else if (typeReturned.equals("bool"))
                fileWriter.write("bool_to_string(");
            expr.accept(this);
            if (!typeReturned.equals("string"))
                fileWriter.write(")");
        } else if (expr instanceof Identifier) {
            String type = ((Identifier) expr).getPointerToRow().getType();
            if (type.equals("integer"))
                fileWriter.write("int_to_string(");
            if (type.equals("real"))
                fileWriter.write("double_to_string(");
            if (type.equals("bool"))
                fileWriter.write("bool_to_string(");
            expr.accept(this);
            if (!type.equals("string"))
                fileWriter.write(")");
        } else if (expr instanceof BinaryOp) {
            expr.accept(this);
        }
    }

    public static String FILE_NAME = "c_gen.c";
    private static File FILE;
    private static FileWriter fileWriter;
    private static int currentTab = 0;

}