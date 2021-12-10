import it.unisa.nodes.ProgramOp;
import it.unisa.visitors.SemanticVisitor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.FileReader;

public class Compiler {

    public static void main(String[] args) throws Exception {

        parser p = new parser(new Yylex(new FileReader(
                System.getProperty("user.dir") + "\\myfun_programs\\" + args[0])));

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) p.parse().value;
        ((ProgramOp) root).accept(new SemanticVisitor());
        JTree tree = new JTree(root);
        JScrollPane scrollPane = new JScrollPane(tree);
        JFrame frame = new JFrame("Abstract Syntax Tree");
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

}