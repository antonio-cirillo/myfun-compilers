# Type system
<p align="center">
  <img src="documentation/optype1.png"><br>
  <img src="documentation/divint.png"><br>
  <img src="documentation/str_concat.png"><br>
  <img src="documentation/optype2.png"><br>
  <img src="documentation/and_or.png"><br>
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
