package com.deviget.devtest.minesweeper.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class FieldTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int height;
    private int width;
    private int numberOfMines;
    private List<Cell> cells;

    public long getId() {
        return id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public FieldTable withHeight(int height) {
        this.height = height;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public FieldTable withWidth(int width) {
        this.width = width;
        return this;
    }


    public int getNumberOfMines() {
        return numberOfMines;
    }

    public void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }

    public FieldTable withNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
        return this;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public FieldTable withCells(List<Cell> cells) {
        this.cells = cells;
        return this;
    }
}
