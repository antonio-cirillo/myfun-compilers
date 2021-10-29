import java.io.*;
import java.util.HashMap;

public class Lexer {

    private RandomAccessFile rInput;
    private static HashMap<String, Token> stringTable;
    private int state, current;

    public Lexer() {
        stringTable = new HashMap<>();
        state = 0;
        current = -1;
        stringTable.put("if", new Token(Token.IF));
        stringTable.put("then", new Token(Token.THEN));
        stringTable.put("else", new Token(Token.ELSE));
        stringTable.put("while", new Token(Token.WHILE));
        stringTable.put("end", new Token(Token.END));
        stringTable.put("loop", new Token(Token.LOOP));
    }

    public void initialize(String filePath) throws FileNotFoundException {
        File input = new File(
                System.getProperty("user.dir") + "\\test_files\\" + filePath);
        rInput = new RandomAccessFile(input, "r");
    }

    public Token nextToken() throws Exception {
        state = 0;
        String lessema = "";
        int c = 0;

        while (true) {

            switch (state) {
                case 0:
                    c = rInput.read();
                    current++;
                    switch (c) {
                        case 60 -> // <
                                state = 1;
                        case 61 -> // =
                                state = 7;
                        case 62 -> // >
                                state = 8;
                        default -> // Non è un operatore
                                state = 11;
                    }
                    break;

                case 1:
                    c = rInput.read();
                    current++;
                    switch (c) {
                        case 61 -> // =
                                state = 2;
                        case 62 -> // >
                                state = 3;
                        case 45 -> // -
                                state = 4;
                        default -> state = 6;
                    }
                    break;

                case 2:
                    return new Token(Token.RELOP, "LE");

                case 3:
                    return new Token(Token.RELOP, "NE");

                case 4:
                    c = rInput.read();
                    current++;
                    if (c == 45) // -
                        state = 5;
                    else
                        state = 6;
                    break;

                case 5:
                    return new Token(Token.ASSIGN);

                case 6:
                    retract();
                    return new Token(Token.RELOP, "LT");

                case 7:
                    return new Token(Token.RELOP, "EQ");

                case 8:
                    c = rInput.read();
                    current++;
                    switch (c) {
                        case 61: // =
                            state = 9;
                            break;
                        default:
                            state = 10;
                            break;
                    }
                    break;

                case 9:
                    return new Token(Token.RELOP, "GE");

                case 10:
                    retract();
                    return new Token(Token.RELOP, "GT");

                /* Istruzioni di controllo sugli identificatori */

                case 11:
                    if (Character.isLetter((char) c)) {
                        state = 12;
                        lessema += (char) c;
                    } else
                        state = 14;
                    break;

                case 12:
                    c = rInput.read();
                    current++;
                    if (Character.isLetter((char) c) || Character.isDigit((char) c))
                        lessema += (char) c;
                    else
                        state = 13;
                    break;

                case 13:
                    retract();
                    return installID(lessema);

                /* Istruzione di controllo sui numeri */

                case 14:
                    if (Character.isDigit((char) c)) {
                        state = 15;
                        lessema += (char) c;
                    } else
                        state = 24;
                    break;

                case 15:
                    c = rInput.read();
                    current++;
                    if (Character.isDigit((char) c))
                        lessema += (char) c;
                    else if (c == 46) { // .
                        lessema += (char) c;
                        state = 16;
                    } else if (c == 69) { // E
                        lessema += (char) c;
                        state = 18;
                    } else
                        state = 21;
                    break;

                case 16:
                    c = rInput.read();
                    current++;
                    if (Character.isDigit((char) c)) {
                        lessema += (char) c;
                        state = 17;
                    } else
                        state = 22;
                    break;

                case 17:
                    c = rInput.read();
                    current++;
                    if (Character.isDigit((char) c))
                        lessema += (char) c;
                    else if (c == 69) { // E
                        lessema += (char) c;
                        state = 18;
                    } else
                        state = 21;
                    break;

                case 18:
                    c = rInput.read();
                    current++;
                    if (Character.isDigit((char) c)) {
                        lessema += (char) c;
                        state = 20;
                    } else if (c == 43 || c == 45) { // + o -
                        lessema += (char) c;
                        state = 19;
                    } else
                        state = 22;
                    break;

                case 19:
                    c = rInput.read();
                    current++;
                    if (Character.isDigit((char) c)) {
                        lessema += (char) c;
                        state = 20;
                    } else
                        state = 23;
                    break;

                case 20:
                    c = rInput.read();
                    current++;
                    if (Character.isDigit((char) c))
                        lessema += (char) c;
                    else
                        state = 21;
                    break;

                case 21:
                    retract();
                    return new Token(Token.NUMBER, lessema);

                case 22:
                    retract();
                    retract();
                    lessema = lessema.substring(0, lessema.length() - 1);
                    return new Token(Token.NUMBER, lessema);

                case 23:
                    retract();
                    retract();
                    retract();
                    lessema = lessema.substring(0, lessema.length() - 2);
                    return new Token(Token.NUMBER, lessema);

                /* Istruzioni di controllo sui separatori (Tab, New Line, Return) */

                case 24:
                    if (c == 9 || c == 10 || c == 13 || c == 32) // Separatori
                        state = 25;
                    else
                        state = 27;
                    break;

                case 25:
                    c = rInput.read();
                    current++;
                    if (c == 9 || c == 10 || c == 13 || c == 32) // Separatori
                        break;
                    else
                        state = 26;
                    break;

                case 26:
                    retract();
                    state = 0; // Procedo con la lettura del prossimo token;
                    break;

                /* Istruzioni di controllo sui sepatori ( ) { } ; , */

                case 27:
                    if ((char) c == ';')
                        state = 28;
                    else
                        state = 29;
                    break;

                case 28:
                    return new Token(Token.COMMA);

                case 29:
                    if(c == -1)
                        state = 30;
                    else
                        state = 31;
                    break;

                case 30:
                    return new Token(Token.EOF);

                case 31:
                    throw new Exception("Invalid syntax: " + (char) c);

            }

        }

    }

    private Token installID(String lessema) {
        Token token;

        if (stringTable.containsKey(lessema))
            return stringTable.get(lessema);
        else {
            token = new Token(Token.ID, lessema);
            stringTable.put(lessema, token);
            return token;
        }

    }


    private void retract() throws IOException {
        rInput.seek(current);
        current--;
    }

}