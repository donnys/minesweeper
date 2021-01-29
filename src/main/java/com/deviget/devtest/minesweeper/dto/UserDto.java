package com.deviget.devtest.minesweeper.dto;

import com.deviget.devtest.minesweeper.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {

    private long id;
    private String name;
    private String userName;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.game = new GameDto(user.getGame());
    }

    public static List<UserDto> convert(List<User> users) {
        return users.stream().map(user -> new UserDto(user)).collect(Collectors.toList());
    }

    private String password;
    private GameDto game;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GameDto getGame() {
        return game;
    }

    public void setGame(GameDto game) {
        this.game = game;
    }
}
