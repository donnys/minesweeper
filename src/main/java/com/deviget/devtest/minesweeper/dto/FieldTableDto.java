package com.deviget.devtest.minesweeper.dto;

import com.deviget.devtest.minesweeper.model.FieldTable;

import java.util.List;
import java.util.stream.Collectors;

public class FieldTableDto {

    private long id;
    private int height;
    private int width;
    private int numberOfMines;
    private List<CellDto> cells;

    public FieldTableDto(FieldTable fieldTable) {
        this.id = fieldTable.getId();
        this.height = fieldTable.getHeight();
        this.width = fieldTable.getWidth();
        this.numberOfMines = fieldTable.getNumberOfMines();
        this.cells = CellDto.convert(fieldTable.getCells());
    }

    public static List<FieldTableDto> convert(List<FieldTable> fieldTables) {
        return fieldTables.stream().map(fieldTable -> new FieldTableDto(fieldTable)).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }

    public List<CellDto> getCells() {
        return cells;
    }

    public void setCells(List<CellDto> cells) {
        this.cells = cells;
    }
}
