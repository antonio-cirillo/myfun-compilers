import it.unisa.nodes.ProgramOp;
import it.unisa.visitors.SemanticVisitor;
import it.unisa.visitors.TranslatorVisitor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;

public class Compiler {

    public static void main(String[] args) throws Exception {

        System.out.println("Insert name of file: ");
        Scanner scanner = new Scanner(System.in);

        parser parser = new parser(new Yylex(new FileReader(
                System.getProperty("user.dir") + "/myfun_programs/" + scanner.next())));

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) parser.parse().value;
        ((ProgramOp) root).accept(new SemanticVisitor());
        ((ProgramOp) root).accept(new TranslatorVisitor());

        ProcessBuilder builder = new ProcessBuilder(
                "/bin/bash", "-c", "cd myfun_programs && gcc c_gen.c");
        builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        Map<String, String> env = builder.environment();
        builder.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process process;
        try {
            process = builder.start();
            process.waitFor();
            if (process.exitValue() != 0) {
                System.out.println("Error during compilation...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}