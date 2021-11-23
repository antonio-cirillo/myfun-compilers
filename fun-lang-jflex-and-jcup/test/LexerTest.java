import java_cup.runtime.Symbol;

import java.io.*;

public class LexerTest {

    public static void main(String[] args) throws IOException {

        Yylex lexer = new Yylex(new FileReader(
                System.getProperty("user.dir") + "\\test\\test_token.txt"));

        Symbol s;
        while((s = lexer.next_token()).sym != sym.EOF) {
            if (s.value == null)
                System.out.println(sym.terminalNames[s.sym]);
            else
                System.out.println(sym.terminalNames[s.sym] + " " + s.value);
        }

    }

}
