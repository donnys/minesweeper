package com.deviget.devtest.minesweeper.controller;

import com.deviget.devtest.minesweeper.dto.GameDto;
import com.deviget.devtest.minesweeper.model.Game;
import com.deviget.devtest.minesweeper.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/game")
    public ResponseEntity<GameDto> createGame(@RequestBody @Valid Game game,
                                              UriComponentsBuilder uriBuilder) {
        GameDto createdGame = gameService.createGame(game);

        URI uri = uriBuilder.path("/game/{id}").buildAndExpand(createdGame.getId()).toUri();
        return ResponseEntity.created(uri).body(createdGame);
    }

    @PostMapping("/games")
    public ResponseEntity<List<GameDto>> createGames(@RequestBody List<@Valid Game> games) {
        return ResponseEntity.created(null).body(gameService.createGames(games));
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<GameDto> getGame(@PathVariable long gameId) {
        return ResponseEntity.ok(gameService.getGame(gameId));
    }

    @GetMapping("/game")
    public ResponseEntity<List<GameDto>> getGames() {
        List<GameDto> games = gameService.getGames();
        return ResponseEntity.ok(games);
    }

    @PutMapping("/game/{gameId}")
    public ResponseEntity<GameDto> updateGame(@RequestBody @Valid Game updatedGame,
                                              @PathVariable long gameId) {
        try {
            return ResponseEntity.ok(gameService.updateGame(updatedGame, gameId));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(path = "/game/{gameId}", consumes = "application/merge-patch+json")
    public ResponseEntity<GameDto> mergeUpdateGame(@RequestBody Game updatedGame,
                                                   @PathVariable long gameId) {
        try {
            return ResponseEntity.ok(gameService.mergeUpdateGame(updatedGame, gameId));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/game/{gameId}")
    public ResponseEntity<?> deleteGame(@PathVariable long gameId) {
        gameService.deleteGame(gameId);
        return ResponseEntity.noContent().build();
    }
}
