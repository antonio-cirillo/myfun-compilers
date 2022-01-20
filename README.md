# Struttura del progetto
All'interno del progeto vi sono diverse directory:
- **hand-coded-lexer**: contine il progetto relativo al primo assignment del corso, ovvero produrre un lexer a mano. L'esercitazione si basava sul simulare un NFA tramite l'utilizzo del costrutto switch;
- **jflex-coded-lexer**: contiene il progetto relativo al secondo assignment del corso, ovvero riprodurre l'esercitazione uno tramite l'utilizzo del tool JFlex;
- **hand-coded-parser**: contiene il progetto relativo al terzo assignment del corso, ovvero produrre un parser a mano. L'esercitazione si basava sulla produzione di una funzione booleane per ogni non terminale, producendo in questo modo un parser a discesa ricorsiva;
- **myfun-language**: contiene il progetto relativo al quarto e quinto assignment del corso, ovvero un compilatore completo per il linguaggio MyFun. Tramite l'utilizzo di JFlex e JCup, viene costruito quello che è l'Abstact Syntax Tree del linguaggio. Una volta prodotto l'AST, vengono utilizzati due implementazioni dell'interfaccia Visitor, utili per l'analisi semantica e la traduzione del codice in linguaggio C.
# Modifiche apportate al linguaggio
All'interno del linguaggio MyFun è possibile effettuare l'overload delle funzioni. Risulta invece impossibile assegnare valori non costanti alle variabili locali al di fuori del corpo del main o di una funzione.  
Di seguito sono riportate tutte le modifiche effettuate al type system della traccia.
# Type system
<p>
  Chiamata a funzione<br>
  <img src="documentation/call_function.png"><br>
  <img src="documentation/call_function_with_value.png"><br>
  If then else<br>
  <img src="documentation/if_then_else.png"><br>
  Istruzione read<br>
  <img src="documentation/read.png"><br>
  <img src="documentation/read_string.png"><br>
  Istruzione write<br>
  <img src="documentation/write.png"><br>
  Operazioni binarie<br>
  <img src="documentation/optype1.png"><br>
  <img src="documentation/divint.png"><br>
  <img src="documentation/str_concat.png"><br>
  <img src="documentation/optype2.png"><br>
  <img src="documentation/and_or.png"><br>
  Operazioni unarie<br>
  <img src="documentation/optype3.png"><br>
  <img src="documentation/not.png"><br>
</p>

## Tabella optype1
Relativa alle operazioni **PLUS**, **MINUS**, **TIMES**, **DIV**, **POW**.
|  | integer | real | bool | string |
| --- | --- | --- | --- | --- |
| integer | integer | real | - | - |
| real | real | real | - | - |
| bool | - | - | - | - |
| string | - | - | - | - |

## Tabella optype2
Relative alle operazioni **EQ**, **NE**, **LT**, **LE**, **GT**, **GE**.
|  | integer | real | bool | string |
| --- | --- | --- | --- | --- |
| integer | bool | bool | - | - |
| real | bool | bool | - | - |
| bool | - | - | bool | - |
| string | - | - | - | bool |

## Tabella optype3
Relative all'operazione unaria **MINUS**.
|  | integer | real | bool | string |
| --- | --- | --- | --- | --- |
| minus | integer | real | - | - |
