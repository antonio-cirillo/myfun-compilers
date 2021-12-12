import it.unisa.nodes.ProgramOp;
import it.unisa.visitors.SemanticVisitor;
import it.unisa.visitors.TranslatorVisitor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Compiler {

    public static void main(String[] args) throws Exception {

        parser parser = new parser(new Yylex(new FileReader(
                System.getProperty("user.dir") + "\\myfun_programs\\" + args[0])));

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) parser.parse().value;
        ((ProgramOp) root).accept(new SemanticVisitor());
        ((ProgramOp) root).accept(new TranslatorVisitor());

        ProcessBuilder builder = new ProcessBuilder(
                "cmd", "/c", "cd myfun_programs && gcc c_gen.c && a");
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println(line);
        }

    }

}