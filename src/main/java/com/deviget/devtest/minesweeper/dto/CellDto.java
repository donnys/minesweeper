package com.deviget.devtest.minesweeper.dto;

import com.deviget.devtest.minesweeper.model.Cell;

import java.util.List;
import java.util.stream.Collectors;

public class CellDto {

    private long id;
    private int columnLocation;
    private int lineLocation;
    private int content;
    private int mark;
    private boolean revealed;

    public CellDto(Cell cell) {
        this.id = cell.getId();
        this.columnLocation = cell.getColumnLocation();
        this.lineLocation = cell.getLineLocation();
        this.content = cell.getContent();
        this.mark = cell.getMark();
        this.revealed = cell.isRevealed();
    }

    public static List<CellDto> convert(List<Cell> cells) {
        return cells.stream().map(cell -> new CellDto(cell)).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public int getColumnLocation() {
        return columnLocation;
    }

    public void setColumnLocation(int columnLocation) {
        this.columnLocation = columnLocation;
    }

    public int getLineLocation() {
        return lineLocation;
    }

    public void setLineLocation(int lineLocation) {
        this.lineLocation = lineLocation;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }
}
