package com.deviget.devtest.minesweeper.service;

import com.deviget.devtest.minesweeper.dto.CellDto;
import com.deviget.devtest.minesweeper.model.Cell;
import com.deviget.devtest.minesweeper.repository.CellRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.NotFoundException;
import java.util.List;

public class CellService {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CellRepository cellRepository;

    public CellDto getCell(long cellId) {
        return new CellDto(cellRepository.findById(cellId).orElseThrow(NotFoundException::new));
    }

    public List<CellDto> getCells() {
        return CellDto.convert(cellRepository.findAll());
    }

    public CellDto createCell(Cell cell) {
        return new CellDto(cellRepository.save(cell));
    }

    public List<CellDto> createCells(List<Cell> cell) {
        return CellDto.convert(cellRepository.saveAll(cell));
    }

    public void deleteCell(long cellId) {
        cellRepository.deleteById(cellId);
    }

    public CellDto updateCell(Cell updatedCell, long cellId) {
        Cell cellToBeUpdated = cellRepository.findById(cellId).orElseThrow(NotFoundException::new);

        cellToBeUpdated
                .withColumnLocation(updatedCell.getColumnLocation())
                .withLineLocation(updatedCell.getLineLocation())
                .withContent(updatedCell.getContent())
                .withMark(updatedCell.getMark())
                .withRevealed(updatedCell.isRevealed());

        return new CellDto(cellRepository.save(cellToBeUpdated));
    }

    public CellDto mergeUpdateCell(Cell updatedCell, long cellId) {
        Cell cellToBeUpdated = cellRepository.findById(cellId).orElseThrow(NotFoundException::new);

        if (updatedCell.getMark() != cellToBeUpdated.getMark()) {
            cellToBeUpdated.setMark(updatedCell.getMark());
        }
        if (updatedCell.isRevealed() != cellToBeUpdated.isRevealed()) {
            // TODO: Perform updates on collateral effect in other parts of the game
            cellToBeUpdated.setRevealed(updatedCell.isRevealed());
        }

        return new CellDto(cellRepository.save(cellToBeUpdated));
    }
}
