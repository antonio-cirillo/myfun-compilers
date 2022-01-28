import it.unisa.nodes.ProgramOp;
import it.unisa.visitors.SemanticVisitor;
import it.unisa.visitors.TranslatorVisitor;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Compiler {

    public static void main(String[] args) throws Exception {


        Path path = Paths.get(args[0]);
        Path filename = path.getFileName();

        parser parser = new parser(new Yylex(new FileReader(args[0])));

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) parser.parse().value;
        ((ProgramOp) root).accept(new SemanticVisitor());
        TranslatorVisitor.FILE_NAME = filename.toString().substring(0, filename.toString().lastIndexOf('.')) + ".c";
        ((ProgramOp) root).accept(new TranslatorVisitor());

        /* Questa parte è stata commentata in quanto è dipendente dal sistema operativo Windwos
            try {

            ProcessBuilder builder = new ProcessBuilder(
                    "cmd", "/c", "cd test_files && cd c_out && gcc " + TranslatorVisitor.FILE_NAME);
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
                    "cmd", "/c", "cd test_files && cd c_out && start a.exe");
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
        } */

    }

}