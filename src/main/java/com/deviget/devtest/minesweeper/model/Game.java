package com.deviget.devtest.minesweeper.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int status;
    private LocalDateTime startTime;
    private LocalDateTime lastUpdate;
    private FieldTable fieldTable;

    public long getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Game withStatus(int status) {
        this.status = status;
        return this;
    }

    public FieldTable getFieldTable() {
        return fieldTable;
    }

    public void setFieldTable(FieldTable fieldTable) {
        this.fieldTable = fieldTable;
    }

    public Game withFieldTable(FieldTable fieldTable) {
        this.fieldTable = fieldTable;
        return this;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Game withStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Game withLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }
}
