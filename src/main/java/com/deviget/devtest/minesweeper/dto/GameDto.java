package com.deviget.devtest.minesweeper.dto;

import com.deviget.devtest.minesweeper.model.Game;

import java.util.List;
import java.util.stream.Collectors;

public class GameDto {

    private long id;
    private int status;
    private FieldTableDto fieldTable;

    public GameDto(Game game) {
        this.id = game.getId();
        this.status = game.getStatus();
        this.fieldTable = new FieldTableDto(game.getFieldTable());
    }

    public static List<GameDto> convert(List<Game> games) {
        return games.stream().map(game -> new GameDto(game)).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public FieldTableDto getFieldTable() {
        return fieldTable;
    }

    public void setFieldTable(FieldTableDto fieldTable) {
        this.fieldTable = fieldTable;
    }
}
