import java.io.FileNotFoundException;

public class Tester {

	public static void main(String[] args) {

		Lexer lexicalAnalyzer = new Lexer();

		try {
			lexicalAnalyzer.initialize(args[0]);
			Token token;
			try {
				while ((token = lexicalAnalyzer.nextToken()) != null) {
					System.out.println(token);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
