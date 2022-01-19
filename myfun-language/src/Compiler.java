import it.unisa.nodes.ProgramOp;
import it.unisa.visitors.SemanticVisitor;
import it.unisa.visitors.TranslatorVisitor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Scanner;

public class Compiler {

    public static void main(String[] args) throws Exception {

        System.out.println("Insert name of file: ");
        Scanner scanner = new Scanner(System.in);

        parser parser = new parser(new Yylex(new FileReader(
                System.getProperty("user.dir") + "\\myfun_programs\\" + scanner.next())));

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) parser.parse().value;
        ((ProgramOp) root).accept(new SemanticVisitor());
        ((ProgramOp) root).accept(new TranslatorVisitor());

        try {

            ProcessBuilder builder = new ProcessBuilder(
                    "cmd", "/c", "cd myfun_programs && gcc c_gen.c");
            builder.redirectErrorStream(true);
            Process process = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }

            builder = new ProcessBuilder(
                    "cmd", "/c", "cd myfun_programs && start a.exe");
            builder.redirectErrorStream(true);
            process = builder.start();
            r = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}