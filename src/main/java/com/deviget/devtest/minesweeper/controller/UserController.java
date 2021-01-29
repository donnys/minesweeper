package com.deviget.devtest.minesweeper.controller;

import com.deviget.devtest.minesweeper.dto.UserDto;
import com.deviget.devtest.minesweeper.model.User;
import com.deviget.devtest.minesweeper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.ws.rs.NotFoundException;
import java.net.URI;
import java.util.List;

@Validated
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid User user,
                                              UriComponentsBuilder uriBuilder) {
        UserDto createdUser = userService.createUser(user);

        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(uri).body(createdUser);
    }

    @PostMapping("/users")
    public ResponseEntity<List<UserDto>> createUsers(@RequestBody List<@Valid User> users) {
        return ResponseEntity.created(null).body(userService.createUsers(users));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid User updatedUser,
                                              @PathVariable long userId) {
        try {
            return ResponseEntity.ok(userService.updateUser(updatedUser, userId));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(path = "/user/{userId}", consumes = "application/merge-patch+json")
    public ResponseEntity<UserDto> mergeUpdateUser(@RequestBody User updatedUser,
                                                   @PathVariable long userId) {
        try {
            return ResponseEntity.ok(userService.mergeUpdateUser(updatedUser, userId));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
