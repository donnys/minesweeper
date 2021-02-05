package com.deviget.devtest.minesweeper.service;

import com.deviget.devtest.minesweeper.dto.FieldTableDto;
import com.deviget.devtest.minesweeper.model.Cell;
import com.deviget.devtest.minesweeper.model.FieldTable;
import com.deviget.devtest.minesweeper.repository.FieldTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;

@Service
public class FieldTableService {

    @Autowired
    private FieldTableRepository fieldTableRepository;

    @Autowired
    private CellService cellService;

    public FieldTableDto getFieldTable(long fieldTableId) {
        return new FieldTableDto(fieldTableRepository.findById(fieldTableId).orElseThrow(NotFoundException::new));
    }

    public List<FieldTableDto> getFieldTables() {
        return FieldTableDto.convert(fieldTableRepository.findAll());
    }

    public FieldTableDto createFieldTable(FieldTable fieldTable) {
        return new FieldTableDto(fieldTableRepository.save(fieldTable));
    }

    public List<FieldTableDto> createFieldTables(List<FieldTable> fieldTable) {
        return FieldTableDto.convert(fieldTableRepository.saveAll(fieldTable));
    }

    public void deleteFieldTable(long fieldTableId) {
        fieldTableRepository.deleteById(fieldTableId);
    }

    public FieldTableDto updateFieldTable(FieldTable updatedFieldTable, long fieldTableId) {
        FieldTable fieldTableToBeUpdated = fieldTableRepository.findById(fieldTableId).orElseThrow(NotFoundException::new);

        fieldTableToBeUpdated
                .withHeight(updatedFieldTable.getHeight())
                .withWidth(updatedFieldTable.getWidth())
                .withNumberOfMines(updatedFieldTable.getNumberOfMines())
                .withCells(updatedFieldTable.getCells());

        return new FieldTableDto(fieldTableRepository.save(fieldTableToBeUpdated));
    }

    public FieldTableDto mergeUpdateFieldTable(FieldTable updatedFieldTable, long fieldTableId) {
        FieldTable fieldTableToBeUpdated = fieldTableRepository.findById(fieldTableId).orElseThrow(NotFoundException::new);

        List<Cell> cells = updatedFieldTable.getCells();
        if (cells != null) {
            cells.forEach(cell -> {
                cellService.mergeUpdateCell(cell, fieldTableId, cell.getId());
            });
        }
        return new FieldTableDto(fieldTableRepository.save(fieldTableToBeUpdated));
    }

    public FieldTable mergeUpdateFieldTable(FieldTable updatedFieldTable) {
        FieldTable fieldTableToBeUpdated = fieldTableRepository.findById(updatedFieldTable.getId()).orElseThrow(NotFoundException::new);

        List<Cell> cells = updatedFieldTable.getCells();
        if (cells != null) {
            cells.forEach(cell -> {
                cellService.mergeUpdateCell(cell, updatedFieldTable.getId(), cell.getId());
            });
        }
        return fieldTableRepository.save(fieldTableToBeUpdated);
    }
}
