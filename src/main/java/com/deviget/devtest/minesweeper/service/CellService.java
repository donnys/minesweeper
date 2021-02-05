package com.deviget.devtest.minesweeper.service;

import com.deviget.devtest.minesweeper.dto.CellDto;
import com.deviget.devtest.minesweeper.helper.GameHelper;
import com.deviget.devtest.minesweeper.model.Cell;
import com.deviget.devtest.minesweeper.model.FieldTable;
import com.deviget.devtest.minesweeper.repository.CellRepository;
import com.deviget.devtest.minesweeper.repository.FieldTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
public class CellService {

    @Autowired
    private CellRepository cellRepository;

    @Autowired
    private FieldTableRepository fieldTableRepository;

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

    public CellDto updateCell(Cell updatedCell, long fieldTableId, long cellId) {
        FieldTable fieldTable = fieldTableRepository.findById(fieldTableId).orElseThrow(NotFoundException::new);
        Cell cellToBeUpdated = cellRepository.findById(cellId).orElseThrow(NotFoundException::new);

        updateCellMark(updatedCell, cellToBeUpdated);
        revealCell(updatedCell, fieldTable, cellToBeUpdated);

        cellToBeUpdated
                .withColumnLocation(updatedCell.getColumnLocation())
                .withLineLocation(updatedCell.getLineLocation())
                .withContent(updatedCell.getContent())
                .withMark(updatedCell.getMark())
                .withRevealed(updatedCell.isRevealed());

        return new CellDto(cellRepository.save(cellToBeUpdated));
    }

    public CellDto mergeUpdateCell(Cell updatedCell, long fieldTableId, long cellId) {
        FieldTable fieldTable = fieldTableRepository.findById(fieldTableId).orElseThrow(NotFoundException::new);
        Cell cellToBeUpdated = cellRepository.findById(cellId).orElseThrow(NotFoundException::new);

        updateCellMark(updatedCell, cellToBeUpdated);
        revealCell(updatedCell, fieldTable, cellToBeUpdated);

        return new CellDto(cellRepository.save(cellToBeUpdated));
    }

    private void updateCellMark(Cell updatedCell, Cell cellToBeUpdated) {
        if (updatedCell.getMark() != cellToBeUpdated.getMark()) {
            cellToBeUpdated.setMark(updatedCell.getMark());
        }
    }

    private void revealCell(Cell updatedCell, FieldTable fieldTable, Cell cellToBeUpdated) {
        if (updatedCell.isRevealed() != cellToBeUpdated.isRevealed() && updatedCell.isRevealed()) {
            int height = fieldTable.getHeight();
            int width = fieldTable.getWidth();
            Cell[][] cellMatrix = GameHelper.createFieldTableCellMatrix(fieldTable);
            revealCell(cellMatrix, cellToBeUpdated, height, width);
        }
    }

    private void revealCell(Cell[][] cellMatrix, Cell cellToBeUpdated, int fieldTableHeight, int fieldTableWidth) {
        if (cellToBeUpdated.isRevealed()) {
            return;
        }

        cellToBeUpdated.setRevealed(true);

        if (cellToBeUpdated.getContent() == 0) {
            int cellColumnLocation = cellToBeUpdated.getColumnLocation();
            int cellLineLocation = cellToBeUpdated.getLineLocation();

            int relativeHeightRangeStart = cellColumnLocation == 0 ? 0 : -1;
            int relativeHeightRangeEnd = cellColumnLocation == fieldTableHeight - 1 ? 0 : 1;
            int relativeWidthRangeStart = cellLineLocation == 0 ? 0 : -1;
            int relativeWidthRangeEnd = cellLineLocation == fieldTableWidth - 1 ? 0 : 1;

            for (int i = relativeHeightRangeStart; i <= relativeHeightRangeEnd; i++) {
                for (int j = relativeWidthRangeStart; j <= relativeWidthRangeEnd; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    }

                    revealCell(cellMatrix, cellMatrix[cellColumnLocation + i][cellLineLocation + j], fieldTableHeight,
                            fieldTableWidth);
                }
            }
        }
    }
}
