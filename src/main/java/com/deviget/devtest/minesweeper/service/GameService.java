package com.deviget.devtest.minesweeper.service;

import com.deviget.devtest.minesweeper.dto.GameDto;
import com.deviget.devtest.minesweeper.helper.GameHelper;
import com.deviget.devtest.minesweeper.model.Cell;
import com.deviget.devtest.minesweeper.model.FieldTable;
import com.deviget.devtest.minesweeper.model.Game;
import com.deviget.devtest.minesweeper.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Service
public class GameService {

    private Random rand = new Random();

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private FieldTableService fieldTableService;

    public GameDto getGame(long gameId) {
        return new GameDto(gameRepository.findById(gameId).orElseThrow(NotFoundException::new));
    }

    public List<GameDto> getGames() {
        return GameDto.convert(gameRepository.findAll());
    }

    public GameDto createGame(Game game) {
        FieldTable fieldTable = game.getFieldTable();
        int height = fieldTable.getHeight();
        int width = fieldTable.getWidth();
        int numberOfMinesToBeDistributed = fieldTable.getNumberOfMines();

        // First validation:
        if (height * width < numberOfMinesToBeDistributed) {
            throw new IllegalArgumentException("The number of mines should be smaller than the number of available cells");
        }

        List<Cell> cells = fieldTable.getCells();

        Cell[][] cellMatrix = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = new Cell(i, j);
                cells.add(cell);
                cellMatrix[i][j] = cell;
            }
        }

        IntStream.range(0, fieldTable.getNumberOfMines()).forEach(bomb -> {
            int i = this.rand.nextInt(height);
            int j = this.rand.nextInt(width);
            Cell cell = cellMatrix[i][j];

            while (cell.getContent() == 9) {
                i = this.rand.nextInt(height);
                j = this.rand.nextInt(width);
                cell = cellMatrix[i][j];
            }

            cell.setContent(9);
        });

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell currentCell = cellMatrix[i][j];

                if (currentCell.getContent() == 9) {
                    continue;
                }

                int numberOfAdjacentBombs = countNumberOfAdjacentBombs(cellMatrix, i, j, height, width);

                currentCell.setContent(numberOfAdjacentBombs);
            }
        }

        printTableContent(height, width, cellMatrix);
        printTableInPlay(fieldTable);

        return new GameDto(gameRepository.save(game));
    }

    private int countNumberOfAdjacentBombs(Cell[][] cellMap, int cellColumnLocation, int cellLineLocation,
                                           int fieldTableHeight, int fieldTableWidth) {
        int relativeHeighttRangeStart = cellColumnLocation == 0 ? 0 : -1;
        int relativeHeightRangeEnd = cellColumnLocation == fieldTableHeight - 1 ? 0 : 1;
        int relativeWidthRangeStart = cellLineLocation == 0 ? 0 : -1;
        int relativeWidthRangeEnd = cellLineLocation == fieldTableWidth - 1 ? 0 : 1;

        int numberOfAdjacentBombs = 0;
        for (int i = relativeHeighttRangeStart; i <= relativeHeightRangeEnd; i++) {
            for (int j = relativeWidthRangeStart; j <= relativeWidthRangeEnd; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                if (cellMap[cellColumnLocation + i][cellLineLocation + j].getContent() == 9) {
                    numberOfAdjacentBombs++;
                }
            }
        }

        return numberOfAdjacentBombs;
    }

    private void printTableInPlay(FieldTable fieldTable) {
        int height = fieldTable.getHeight();
        int width = fieldTable.getWidth();
        Cell[][] cellMatrix = GameHelper.createFieldTableCellMatrix(fieldTable);

        for (int i = 0; i < height; i++) {
            System.out.println();
            for (int j = 0; j < width; j++) {
                Cell cell = cellMatrix[i][j];
                if (cell.getMark() != 0) {
                    System.out.print("M" + cell.getMark() + " ");
                } else if (cell.isRevealed()) {
                    System.out.print(cell.getContent() + " ");
                } else {
                    System.out.print("X ");
                }
            }
        }
        System.out.println();
    }

    private void printTableContent(int height, int width, Cell[][] cellMap) {
        for (int i = 0; i < height; i++) {
            System.out.println();
            for (int j = 0; j < width; j++) {
                System.out.print(cellMap[i][j].getContent() + " ");
            }
        }
        System.out.println();
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
        FieldTable fieldTable = updatedGame.getFieldTable();
        if (fieldTable != null) {
            fieldTable = fieldTableService.mergeUpdateFieldTable(fieldTable);
            printTableInPlay(fieldTable);
        }

        Game updateGame = gameRepository.save(gameToBeUpdated);
        return new GameDto(updateGame);
    }
}
