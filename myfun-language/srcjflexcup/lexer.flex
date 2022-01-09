// C:/JFLEX/bin/jflex -d src srcjflexcup/lexer.flex // Windows
// jflex -d src srcjflexcup/lexer.flex // Ubuntu

// User Code
import java.util.HashMap;
import java_cup.runtime.*;

%%
// Option and Declaration
%unicode                        // Set di caratteri
%line                           // Viene abilitato il contatore di linee. Il valore è contenuto all'interno di yyline
%column                         // Viene abilitato il contatore di colonne. Valore contenuto in yyycolumn
%cup

Identifier = [$_A-Za-z] [$_@A-Za-z0-9]*

IntLiteral = [0-9]+ (e-?[0-9]+)? | 0x[0-9a-f] | 0b[01]+
RealLiteral = [0-9]+ \. [0-9]+ (e-?[0-9]+)?

Write = \? ( \. | , | : )?

LineTerminator = \r\n|\r|\n
WhiteSpace = {LineTerminator} | [ \t\f]

%{
    StringBuffer string = new StringBuffer();

    static final HashMap <String, Symbol> stringTable = new HashMap <> ();

    private Symbol installID(String lessema) {

        if(stringTable.containsKey(lessema)) {
            return symbol(sym.ID, lessema);
        }

        else {
            Symbol s = symbol(sym.ID, lessema);
            stringTable.put(lessema, s);
            return s;
        }

    }

    private Symbol symbol(int type) {
      return new Symbol(type, yyline + 1, yycolumn);
    }

    private Symbol symbol(int type, Object o) {
      return new Symbol(type, yyline + 1, yycolumn, o);
    }
%}

%init{

%init}

%state STRING_SINGLE_QUOTE
%state STRING_DOUBLE_QUOTE
%state TRADITIONAL_COMMENT
%state ONE_LINE_COMMENT

%%

// Lexical rules

// MAIN, IF, THEN, ELSE, WHILE, LOOP, END
<YYINITIAL> "main"                      { return symbol(sym.MAIN); }
<YYINITIAL> "if"                        { return symbol(sym.IF); }
<YYINITIAL> "then"                      { return symbol(sym.THEN); }
<YYINITIAL> "else"                      { return symbol(sym.ELSE); }
<YYINITIAL> "while"                     { return symbol(sym.WHILE); }
<YYINITIAL> "loop"                      { return symbol(sym.LOOP); }
<YYINITIAL> "end"                       { return symbol(sym.END); }

// VAR, INTEGER, REAL, STRING, BOOL
<YYINITIAL> "var"                       { return symbol(sym.VAR); }
<YYINITIAL> "integer"                   { return symbol(sym.INTEGER); }
<YYINITIAL> "real"                      { return symbol(sym.REAL); }
<YYINITIAL> "string"                    { return symbol(sym.STRING); }
<YYINITIAL> "bool"                      { return symbol(sym.BOOL); }

// ASSIGN, PLUS, MINUS, TIMES, DIVINT, DIV, POW
<YYINITIAL> "+"                         { return symbol(sym.PLUS); }
<YYINITIAL> "-"                         { return symbol(sym.MINUS); }
<YYINITIAL> "*"                         { return symbol(sym.TIMES); }
<YYINITIAL> "div"                       { return symbol(sym.DIVINT); }
<YYINITIAL> "/"                         { return symbol(sym.DIV); }
<YYINITIAL> "^"                         { return symbol(sym.POW); }

// STR_CONCAT, EQ, NE, LT, LE, GT, GE, AND, OR, NOT
<YYINITIAL> "&"                         { return symbol(sym.STR_CONCAT); }
<YYINITIAL> "="                         { return symbol(sym.EQ); }
<YYINITIAL> "<"                         { return symbol(sym.LT); }
<YYINITIAL> "<="                        { return symbol(sym.LE); }
<YYINITIAL> "<>" | "!="                 { return symbol(sym.NE); }
<YYINITIAL> ":="                        { return symbol(sym.ASSIGN); }
<YYINITIAL> ">"                         { return symbol(sym.GT); }
<YYINITIAL> ">="                        { return symbol(sym.GE); }
<YYINITIAL> "and"                       { return symbol(sym.AND); }
<YYINITIAL> "or"                        { return symbol(sym.OR); }
<YYINITIAL> "not"                       { return symbol(sym.NOT); }

// FUN, RETURN
<YYINITIAL> "fun"                       { return symbol(sym.FUN); }
<YYINITIAL> "out"                       { return symbol(sym.OUT); }
<YYINITIAL> "@"                         { return symbol(sym.OUTPAR); }
<YYINITIAL> "return"                    { return symbol(sym.RETURN); }

// TRUE, FALSE, NULL
<YYINITIAL> "true"                      { return symbol(sym.BOOL_CONST, yytext()); }
<YYINITIAL> "false"                     { return symbol(sym.BOOL_CONST, yytext()); }

<YYINITIAL> {

      /* identifier */
      {Identifier}                      { return installID(yytext()); }

      /* numbers */
      {IntLiteral}                      { return symbol(sym.INTEGER_CONST, yytext()); }
      {RealLiteral}                     { return symbol(sym.REAL_CONST, yytext()); }

      /* strings */
      \"                                { string.setLength(0); yybegin(STRING_DOUBLE_QUOTE); }
      '                                 { string.setLength(0); yybegin(STRING_SINGLE_QUOTE); }

      /* print commands */
      {Write}                           { return symbol(sym.WRITE, yytext()); }
      "%"                               { return symbol(sym.READ, yytext()); }

      /* separators */
      "("                               { return symbol(sym.LPAR); }
      ")"                               { return symbol(sym.RPAR); }
      ":"                               { return symbol(sym.COLON); }
      ","                               { return symbol(sym.COMMA); }
      ";"                               { return symbol(sym.SEMI); }

      /* whitespace */
      {WhiteSpace}                      { /* no action */ }

      /* comments */
      "#*"                              { yybegin(TRADITIONAL_COMMENT); }
      "//" | "#"                        { yybegin(ONE_LINE_COMMENT); }

}

<STRING_DOUBLE_QUOTE> {

      \"                                { yybegin(YYINITIAL);
                                          return symbol(sym.STRING_CONST,
                                          string.toString()); }

      [^\n\r\"\\]+                      { string.append(yytext()); }

      \\t                               { string.append("\\t"); }
      \\n                               { string.append("\\n"); }
      \\r                               { string.append("\\r"); }
      \\\"                              { string.append("\""); }
      \\                                { string.append("\\"); }

      <<EOF>>                           { throw new Error("Illegal line end in string literal"); }

}

<STRING_SINGLE_QUOTE> {

      '                                 { yybegin(YYINITIAL);
                                          return symbol(sym.STRING_CONST,
                                          string.toString()); }

      [^\n\r'\"\\]+                     { string.append(yytext()); }

      \\t                               { string.append("\\t"); }
      \\n                               { string.append("\\n"); }
      \\r                               { string.append("\\r"); }
      \\'                               { string.append("'"); }
      \"                                { string.append("\""); }
      \\                                { string.append("\\"); }

    <<EOF>>                             { throw new Error("Illegal line end in string literal"); }

}

<TRADITIONAL_COMMENT> {

     #                                  { yybegin(YYINITIAL); }
     [^#]                               { /* no action */ }

     <<EOF>>                            { throw new Error("Unclosed comment"); }

}

<ONE_LINE_COMMENT> {

    {LineTerminator}                    { yybegin(YYINITIAL); }
    [^]                                 { /* no action */ }

}

/* error fallback */
<YYINITIAL> [^]                         { throw new Error("Illegal character: " + yytext()); }
<<EOF>>                                 { return symbol(sym.EOF); }