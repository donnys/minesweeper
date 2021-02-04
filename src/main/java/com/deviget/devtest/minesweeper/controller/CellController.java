package com.deviget.devtest.minesweeper.controller;

import com.deviget.devtest.minesweeper.dto.CellDto;
import com.deviget.devtest.minesweeper.model.Cell;
import com.deviget.devtest.minesweeper.service.CellService;
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
public class CellController {

    @Autowired
    private CellService cellService;

    @PostMapping("/cell")
    public ResponseEntity<CellDto> createCell(@RequestBody @Valid Cell cell,
                                              UriComponentsBuilder uriBuilder) {
        CellDto createdCell = cellService.createCell(cell);

        URI uri = uriBuilder.path("/cell/{id}").buildAndExpand(createdCell.getId()).toUri();
        return ResponseEntity.created(uri).body(createdCell);
    }

    @PostMapping("/cells")
    public ResponseEntity<List<CellDto>> createCells(@RequestBody List<@Valid Cell> cells) {
        return ResponseEntity.created(null).body(cellService.createCells(cells));
    }

    @GetMapping("/cell/{cellId}")
    public ResponseEntity<CellDto> getCell(@PathVariable long cellId) {
        return ResponseEntity.ok(cellService.getCell(cellId));
    }

    @GetMapping("/cell")
    public ResponseEntity<List<CellDto>> getCells() {
        List<CellDto> cells = cellService.getCells();
        return ResponseEntity.ok(cells);
    }

    @PutMapping("/cell/{cellId}")
    public ResponseEntity<CellDto> updateCell(@RequestBody @Valid Cell updatedCell,
                                              @PathVariable long cellId) {
        try {
            return ResponseEntity.ok(cellService.updateCell(updatedCell, cellId));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(path = "/cell/{cellId}", consumes = "application/merge-patch+json")
    public ResponseEntity<CellDto> mergeUpdateCell(@RequestBody Cell updatedCell,
                                                   @PathVariable long cellId) {
        try {
            return ResponseEntity.ok(cellService.mergeUpdateCell(updatedCell, cellId));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/cell/{cellId}")
    public ResponseEntity<?> deleteCell(@PathVariable long cellId) {
        cellService.deleteCell(cellId);
        return ResponseEntity.noContent().build();
    }
}
