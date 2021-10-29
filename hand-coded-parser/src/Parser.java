/*
 *       N = {S, Program, Stmt, Expr},
 *       T = {EOF, COMMA, IF, THEN, ELSE, END, ID, ASSIGN, WHILE, LOOP, RELOP, NUMBER},
 *       S
 *       P = {
 *           S -> Program EOF
 *           Program -> Stmt ProgramRest
 *           ProgramRest -> ; Stmt ProgramRest
 *           ProgramRest -> epsilon
 *           Stmt -> IF Expr THEN Stmt ELSE Stmt END IF
 *           Stmt -> ID ASSIGN Expr
 *           Stmt -> WHILE Expr LOOP Stmt END LOOP
 *           Expr -> Value RelopChain
 *           RelopChain -> RELOP Value RelopChain
 *           RelopChain -> epsilon
 *           Value -> ID
 *           Value -> Number
 *       }
 */

import java.util.ArrayList;

public class Parser {

    static Token token;
    static Lexer lexer;
    static ArrayList<Token> tokens;
    static int pointer = 0;

    public static void main(String[] args) throws Exception {

        tokens = new ArrayList <> ();

        lexer = new Lexer();
        lexer.initialize(args[0]);

        boolean isValid = S();
        if(isValid)
            System.out.println("Nessun errore sintattico!");
        else
            System.out.println("Errore sintattico!");
    }

    static boolean S() throws Exception {
        if (!Program()) return false;

        token = get_next_token();
        if (token.getName() != Token.EOF) return false;

        return true;
    }

    static boolean Program() throws Exception {
        int tmp_pointer = pointer;

        if (!Stmt()) {
            pointer = tmp_pointer;
            return false;
        }

        if (!ProgramRest()) {
            pointer = tmp_pointer;
            return true;
        }

        return true;
    }

    static boolean ProgramRest() throws Exception {
        int tmp_pointer = pointer;

        token = get_next_token();
        if (token.getName() != Token.COMMA) {
            pointer = tmp_pointer;
            return true;
        }
        pointer++;

        if (!Stmt()) {
            pointer = tmp_pointer;
            return true;
        }

        ProgramRest();

        return true;
    }

    static boolean Stmt() throws Exception {
        int tmp_pointer = pointer;
        token = get_next_token();

        if (token.getName() == Token.IF) {

            pointer++;

            if (!Expr()) {
                pointer = tmp_pointer;
                return false;
            }

            token = get_next_token();
            if (token.getName() != Token.THEN) {
                pointer = tmp_pointer;
                return false;
            }
            pointer++;

            if (!Stmt()) {
                pointer = tmp_pointer;
                return false;
            }

            token = get_next_token();
            if (token.getName() != Token.ELSE) {
                pointer = tmp_pointer;
                return false;
            }
            pointer++;

            if (!Stmt()) {
                pointer = tmp_pointer;
                return false;
            }

            token = get_next_token();
            if (token.getName() != Token.END) {
                pointer = tmp_pointer;
                return false;
            }
            pointer++;

            token = get_next_token();
            if (token.getName() != Token.IF) {
                pointer = tmp_pointer;
                return false;
            }
            pointer++;

        } else if (token.getName() == Token.ID) {

            pointer++;

            token = get_next_token();
            if (token.getName() != Token.ASSIGN) {
                pointer = tmp_pointer;
                return false;
            }
            pointer++;

            if (!Expr()) {
                pointer = tmp_pointer;
                return false;
            }

        } else if (token.getName() == Token.WHILE) {

            pointer++;

            if (!Expr()) {
                pointer = tmp_pointer;
                return false;
            }

            token = get_next_token();
            if (token.getName() != Token.LOOP) {
                pointer = tmp_pointer;
                return false;
            }
            pointer++;

            if (!Stmt()) {
                pointer = tmp_pointer;
                return false;
            }

            token = get_next_token();
            if (token.getName() != Token.END) {
                pointer = tmp_pointer;
                return false;
            }
            pointer++;

            token = get_next_token();
            if (token.getName() != Token.LOOP) {
                pointer = tmp_pointer;
                return false;
            }
            pointer++;

        } else
            return false;

        return true;
    }

    static boolean Expr() throws Exception {
        int tmp_pointer = pointer;
        if (!Value())
            return false;

        if (!RelopChain()) {
            pointer = tmp_pointer;
            return true;
        }

        return true;
    }

    static boolean RelopChain() throws Exception {
        int tmp_pointer = pointer;
        token = get_next_token();
        if (token.getName() != Token.RELOP)
            return true;
        pointer++;

        if (!Value()) {
            pointer = tmp_pointer;
            return true;
        }

        RelopChain();

        return true;
    }

    static boolean Value() throws Exception {
        token = get_next_token();

        if (token.getName() == Token.ID) {
            pointer++;
            return true;
        }

        if (token.getName() == Token.NUMBER) {
            pointer++;
            return true;
        }

        return false;
    }

    private static Token get_next_token() throws Exception {
        if (pointer < tokens.size())
            return tokens.get(pointer);

        Token t = lexer.nextToken();
        tokens.add(t);
        return t;
    }
}