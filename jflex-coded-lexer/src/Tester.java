import java_cup.runtime.Symbol;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Tester {

    public static void main(String[] args) throws IOException {

        File input = new File(
                System.getProperty("user.dir") + "\\test_files\\" + args[0]);
        Lexer lexer = new Lexer(new FileReader(input));

        final String[] ID = {"IF", "THEN", "ELSE", "WHILE", "INT", "FLOAT", "RELOP", "ASSIGN",
            "ID", "NUMBER", "SEPARATOR"};

        Symbol s;
        while((s = lexer.next_token()).sym != Sym.EOF)
            if(s.value == null)
                System.out.println(ID[s.sym - 1]);
            else
                System.out.println(ID[s.sym - 1] + " " + s.value);

    }

}
