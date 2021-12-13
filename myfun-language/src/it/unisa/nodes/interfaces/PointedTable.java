package it.unisa.nodes.interfaces;

import it.unisa.symboltable.row.Row;

public interface PointedTable <T extends Row> {

    T getPointerToRow();
    void setPointerToRow(T row);

}
