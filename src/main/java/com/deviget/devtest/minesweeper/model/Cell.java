package com.deviget.devtest.minesweeper.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Cell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private int columnLocation;

    @Column
    private int lineLocation;

    @Column
    private int content;

    @Column
    private int mark;

    @Column
    private boolean revealed;

    public Cell(int columnLocation, int lineLocation, int content) {
        this.columnLocation = columnLocation;
        this.lineLocation = lineLocation;
        this.content = content;
        this.mark = 0;
        this.revealed = false;
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


    public Cell withColumnLocation(int columnLocation) {
        this.columnLocation = columnLocation;
        return this;
    }

    public int getLineLocation() {
        return lineLocation;
    }

    public void setLineLocation(int lineLocation) {
        this.lineLocation = lineLocation;
    }

    public Cell withLineLocation(int lineLocation) {
        this.lineLocation = lineLocation;
        return this;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public Cell withContent(int content) {
        this.content = content;
        return this;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Cell withMark(int mark) {
        this.mark = mark;
        return this;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public Cell withRevealed(boolean revealed) {
        this.revealed = revealed;
        return this;
    }
}
