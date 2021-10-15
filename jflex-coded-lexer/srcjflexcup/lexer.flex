// User Code
import java_cup.runtime.*;

%%
// Option and Declaration and
%class Lexer                    // Il tutto verrà scritto nella classe Lexer.java
%unicode                        // Set di caratteri
%line                           // Viene abilitato il contatore di linee. Il valore è contenuto all'interno di yyline
%column                         // Viene abilitato il contatore di colonne. Valore contenuto in yyycolumn
%cup
%cupsym Sym

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
<YYINITIAL> "if"                    { return symbol(Sym.IF); }
<YYINITIAL> "then"                  { return symbol(Sym.THEN); }
<YYINITIAL> "else"                  { return symbol(Sym.ELSE); }
<YYINITIAL> "while"                 { return symbol(Sym.WHILE); }
<YYINITIAL> "int"                   { return symbol(Sym.INT); }
<YYINITIAL> "float"                 { return symbol(Sym.FLOAT); }

<YYINITIAL> {

    /* operators */
    "<--"                           { return symbol(Sym.ASSIGN); }
    {RelOp}                         { return symbol(Sym.RELOP, yytext()); }

    /* identifier */
    {Identifier}                    { return symbol(Sym.IDENTIFIER, yytext()); }

    /* numbers */
    {Number}                        { return symbol(Sym.NUMBER_LITERAL, yytext()); }

    /* Separator */
    {Separator}                     { return symbol(Sym.SEPARATOR, yytext()); }

    /* whitespace */
    {WhiteSpace}                    { /* no action */ }
}

/* error fallback */
<YYINITIAL> [^]                     { throw new Error("Illegal character: " + yytext()); }
<<EOF>>                             { return symbol(Sym.EOF); }