package com.deviget.devtest.minesweeper.helper;

import com.deviget.devtest.minesweeper.model.Cell;
import com.deviget.devtest.minesweeper.model.FieldTable;

public class GameHelper {

    private GameHelper() {
    }

    public static Cell[][] createFieldTableCellMatrix(FieldTable fieldTable) {
        int height = fieldTable.getHeight();
        int width = fieldTable.getWidth();
        Cell[][] cellMatrix = new Cell[height][width];
        fieldTable.getCells().forEach(cell -> cellMatrix[cell.getColumnLocation()][cell.getLineLocation()] = cell);
        return cellMatrix;
    }
}
