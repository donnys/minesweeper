package com.deviget.devtest.minesweeper.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FieldTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private int numberOfMines;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Cell> cells = new ArrayList<>();

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
