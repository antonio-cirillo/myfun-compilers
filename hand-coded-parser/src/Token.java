public class Token {

    private int name;
    private String attribute;

    public Token(int name, String attribute) {
        this.name = name;
        this.attribute = attribute;
    }

    public Token(int name) {
        this.name = name;
        this.attribute = null;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String toString() {
        return attribute == null ? "" + name : "(" + name + ", \"" + attribute + "\")";
    }

    public static final int EOF = 0;
    public static final int IF = 1;
    public static final int THEN = 2;
    public static final int ELSE = 3;
    public static final int WHILE = 4;
    public static final int END = 5;
    public static final int ID = 6;
    public static final int COMMA = 7;
    public static final int ASSIGN = 8;
    public static final int LOOP = 9;
    public static final int RELOP = 10;
    public static final int NUMBER = 11;

    public static final String[] TOKENS = {
            "EOF", "IF", "THEN", "ELSE", "WHILE", "END", "ID", ";", "ASSIGN", "LOOP", "RELOP", "NUMBER"
    };

}