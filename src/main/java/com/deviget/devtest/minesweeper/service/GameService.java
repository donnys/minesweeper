package com.deviget.devtest.minesweeper.service;

import com.deviget.devtest.minesweeper.dto.GameDto;
import com.deviget.devtest.minesweeper.model.Cell;
import com.deviget.devtest.minesweeper.model.FieldTable;
import com.deviget.devtest.minesweeper.model.Game;
import com.deviget.devtest.minesweeper.repository.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public GameDto getGame(long gameId) {
        return new GameDto(gameRepository.findById(gameId).orElseThrow(NotFoundException::new));
    }

    public List<GameDto> getGames() {
        return GameDto.convert(gameRepository.findAll());
    }

    public GameDto createGame(Game game) {
//        FieldTable fieldTable = game.getFieldTable();
//        int height = fieldTable.getHeight();
//        int width = fieldTable.getWidth();
//        int numberOfMinesToBeDistributed = fieldTable.getNumberOfMines();
//        List<Cell> cells = fieldTable.getCells();
//
//        for(int i = 0; i < height; i++) {
//            for(int j = 0; j < width; j++) {
//                cells.add(new Cell(i, j, ));
//            }
//        }


        return new GameDto(gameRepository.save(game));
    }

    public List<GameDto> createGames(List<Game> game) {
        return GameDto.convert(gameRepository.saveAll(game));
    }

    public void deleteGame(long gameId) {
        gameRepository.deleteById(gameId);
    }

    public GameDto updateGame(Game updatedGame, long gameId) {
        Game gameToBeUpdated = gameRepository.findById(gameId).orElseThrow(NotFoundException::new);

        gameToBeUpdated
                .withStatus(updatedGame.getStatus())
                .withStartTime(updatedGame.getStartTime())
                .withLastUpdate(updatedGame.getLastUpdate())
                .withFieldTable(updatedGame.getFieldTable());

        return new GameDto(gameRepository.save(gameToBeUpdated));
    }

    public GameDto mergeUpdateGame(Game updatedGame, long gameId) {
        Game gameToBeUpdated = gameRepository.findById(gameId).orElseThrow(NotFoundException::new);

        if (updatedGame.getStartTime() != null) {
            gameToBeUpdated.setStartTime(updatedGame.getStartTime());
        }
        if (updatedGame.getLastUpdate() != null) {
            gameToBeUpdated.setLastUpdate(updatedGame.getLastUpdate());
        }
        if (updatedGame.getFieldTable() != null) {
            // TODO: probably better to use FieldTable service
            gameToBeUpdated.setFieldTable(updatedGame.getFieldTable());
        }

        return new GameDto(gameRepository.save(gameToBeUpdated));
    }
}
