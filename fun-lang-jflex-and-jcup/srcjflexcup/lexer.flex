// C:/JFLEX/bin/jflex -d src srcjflexcup/lexer.flex

// User Code
import java.util.HashMap;
import java_cup.runtime.*;

%%
// Option and Declaration
%unicode                        // Set di caratteri
%line                           // Viene abilitato il contatore di linee. Il valore è contenuto all'interno di yyline
%column                         // Viene abilitato il contatore di colonne. Valore contenuto in yyycolumn
%cup

Identifier = [$_@A-Za-z] [$_@A-Za-z0-9]*

IntLiteral = [0-9]+ (e-?[0-9]+)? | 0x[0-9a-f] | 0b[01]+
RealLiteral = [0-9]+ \. [0-9]+ (e-?[0-9]+)?

NumberOp = \+ | - | \* | div | \^
StringOp = &
RelOp =  ( < ( > | = ) ? ) | ( > ( = ) ? ) | \!= | =
BoolOp = not | and | or

Print = \? ( \. | , | : )?

LineTerminator = \r\n|\r|\n
WhiteSpace = {LineTerminator} | [ \t\f]

%{
    StringBuffer string = new StringBuffer();

    static final HashMap <String, Symbol> stringTable = new HashMap <> ();

    private Symbol installID(String lessema) {

        if(stringTable.containsKey(lessema)) {
            return stringTable.get(lessema);
        }

        else {
            Symbol s = symbol(sym.IDENTIFIER, lessema);
            stringTable.put(lessema, s);
            return s;
        }

    }

    private Symbol symbol(int type) {
      return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object o) {
      return new Symbol(type, yyline, yycolumn, o);
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

<YYINITIAL> {

      "<-"                              { return symbol(sym.ASSIGN); }
      {NumberOp}                        { return symbol(sym.NUMBEROP, yytext()); }
      {StringOp}                        { return symbol(sym.STRINGOP, yytext()); }
      {RelOp}                           { return symbol(sym.RELOP, yytext()); }
      {BoolOp}                          { return symbol(sym.BOOLOP, yytext()); }

      {Identifier}                      { return installID(yytext()); }

      {IntLiteral}                      { return symbol(sym.INT_LITERAL, yytext()); }
      {RealLiteral}                     { return symbol(sym.REAL_LITERAL, yytext()); }

      \"                                { string.setLength(0); yybegin(STRING_DOUBLE_QUOTE); }
      '                                 { string.setLength(0); yybegin(STRING_SINGLE_QUOTE); }

      {Print}                           { return symbol(sym.PRINT, yytext()); }

      "("                               { return symbol(sym.LPAR); }
      ")"                               { return symbol(sym.RPAR); }
      ","                               { return symbol(sym.COMMA); }
      ";"                               { return symbol(sym.SEMI); }

      {WhiteSpace}                      { /* no action */ }

      "#*"                              { yybegin(TRADITIONAL_COMMENT); }
      "//" | "#"                        { yybegin(ONE_LINE_COMMENT); }
}

<STRING_DOUBLE_QUOTE> {

      \"                                { yybegin(YYINITIAL);
                                          return symbol(sym.STRING_LITERAL,
                                          string.toString()); }

      [^\n\r\"\\]+                      { string.append(yytext()); }

      \\t                               { string.append('\t'); }
      \\n                               { string.append('\n'); }
      \\r                               { string.append('\r'); }
      \\\"                              { string.append('\"'); }
      \\                                { string.append('\\'); }

}

<STRING_SINGLE_QUOTE> {

      '                                 { yybegin(YYINITIAL);
                                          return symbol(sym.STRING_LITERAL,
                                          string.toString()); }

      [^\n\r'\"\\]+                     { string.append(yytext()); }

      \\t                               { string.append('\t'); }
      \\n                               { string.append('\n'); }
      \\r                               { string.append('\r'); }
      \\'                               { string.append('\''); }
      \"                                { string.append('\"'); }
      \\                                { string.append('\\'); }

}

<TRADITIONAL_COMMENT> {

     #                                  { yybegin(YYINITIAL); }
     [^#]                               { /* no action */ }

     <<EOF>>                            { throw new Error("IL COMMENTO DEVE ESSERE CHIUSO!"); }

}

<ONE_LINE_COMMENT> {

    {LineTerminator}                    { yybegin(YYINITIAL); }
    [^{LineTerminator}]                 { /* no action */ }

}

/* error fallback */
<YYINITIAL> [^]                         { throw new Error("Illegal character: " + yytext()); }
<<EOF>>                                 { return symbol(sym.EOF); }