// User Code
import java_cup.runtime.*;

%%
// Option and Declaration and
%class Lexer                    // Il tutto verrà scritto nella classe Lexer.java
%unicode                        // Set di caratteri
%line                           // Viene abilitato il contatore di linee. Il valore è contenuto all'interno di yyline
%column                         // Viene abilitato il contatore di colonne. Valore contenuto in yyycolumn
%cup
%cupsym Token

RelOp = ( < ( = | > ) ? ) | ( > ( = ) ? ) | =
Identifier = [A-Za-z][A-Za-z0-9]*
DecIntegerLiteral = 0 | [1-9][0-9]*
Number = {DecIntegerLiteral} (\.[0-9]+)? (E[+-]?[0-9]+)?
LineTerminator = \r\n|\r|\n
WhiteSpace = {LineTerminator} | [ \t\f]
Separator = [(){},;]

%{
    private Symbol symbol(int type) {
      return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object o) {
      return new Symbol(type, yyline, yycolumn, o);
    }
%}

%%

// Lexical rules

/* keywords */
<YYINITIAL> "if"                    { return symbol(Token.IF); }
<YYINITIAL> "then"                  { return symbol(Token.THEN); }
<YYINITIAL> "else"                  { return symbol(Token.ELSE); }
<YYINITIAL> "while"                 { return symbol(Token.WHILE); }
<YYINITIAL> "int"                   { return symbol(Token.INT); }
<YYINITIAL> "float"                 { return symbol(Token.FLOAT); }

<YYINITIAL> {

    /* operators */
    "<--"                           { return symbol(Token.ASSIGN); }
    {RelOp}                         { return symbol(Token.RELOP, yytext()); }

    /* identifier */
    {Identifier}                    { return symbol(Token.IDENTIFIER, yytext()); }

    /* numbers */
    {Number}                        { return symbol(Token.NUMBER_LITERAL, yytext()); }

    /* Separator */
    {Separator}                     { return symbol(Token.SEPARATOR, yytext()); }

    /* whitespace */
    {WhiteSpace}                    { /* no action */ }
}

/* error fallback */
<YYINITIAL> [^]                     { throw new Error("Illegal character: " + yytext()); }
<<EOF>>                             { return symbol(Token.EOF); }