import java.io.FileNotFoundException;
import java.io.FileReader;

public class Tester {

    public static void main(String[] args) throws FileNotFoundException {

        parser p = new parser(new Yylex(new FileReader(
                System.getProperty("user.dir") + "\\test_files\\myfun_program.txt")));
        try {
            p.debug_parse();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
