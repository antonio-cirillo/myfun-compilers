import java_cup.runtime.Symbol; 		//This is how we pass tokens to the parser

%%

%unicode 								// We wish to read text files
%cupsym CircuitSym
%cup 									// Declare that we expect to use Java CUP

whitespace = [ \r\n\t\f]
digit = [0-9]
number = {digit}+
value1 = {number}("."{number})?

%%

"seq" 		{ return new Symbol(CircuitSym.SEQ); }
"par" 		{ return new Symbol(CircuitSym.PAR); }
"end" 		{ return new Symbol(CircuitSym.END); }
{value1} 	{ return new Symbol(CircuitSym.RESISTOR,yytext()); }
{whitespace} { /* ignore */ }
[^]			{ return new Symbol(CircuitSym.error);}

<<EOF>> {return new Symbol(CircuitSym.EOF);}