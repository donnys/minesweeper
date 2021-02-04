package com.deviget.devtest.minesweeper.service;

import com.deviget.devtest.minesweeper.dto.UserDto;
import com.deviget.devtest.minesweeper.model.User;
import com.deviget.devtest.minesweeper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto getUser(long userId) {
        return new UserDto(userRepository.findById(userId).orElseThrow(NotFoundException::new));
    }

    public List<UserDto> getUsers() {
        return UserDto.convert(userRepository.findAll());
    }

    public UserDto createUser(User user) {
        return new UserDto(userRepository.save(user));
    }

    public List<UserDto> createUsers(List<User> user) {
        return UserDto.convert(userRepository.saveAll(user));
    }

    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    public UserDto updateUser(User updatedUser, long userId) {
        User userToBeUpdated = userRepository.findById(userId).orElseThrow(NotFoundException::new);

        userToBeUpdated
                .withName(updatedUser.getName())
                .withUserName(updatedUser.getUserName())
                .withPassword(updatedUser.getPassword())
                .withGames(updatedUser.getGames());

        return new UserDto(userRepository.save(userToBeUpdated));
    }

    public UserDto mergeUpdateUser(User updatedUser, long userId) {
        User userToBeUpdated = userRepository.findById(userId).orElseThrow(NotFoundException::new);

        if (updatedUser.getName() != null) {
            userToBeUpdated.setName(updatedUser.getName());
        }
        if (updatedUser.getGames() != null) {
            userToBeUpdated.setGames(updatedUser.getGames());
        }
        return new UserDto(userRepository.save(userToBeUpdated));
    }
}
