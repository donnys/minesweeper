package com.deviget.devtest.minesweeper.controller;

import com.deviget.devtest.minesweeper.dto.FieldTableDto;
import com.deviget.devtest.minesweeper.model.FieldTable;
import com.deviget.devtest.minesweeper.service.FieldTableService;
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
public class FieldTableController {

    @Autowired
    private FieldTableService fieldTableService;

    @PostMapping("/fieldTable")
    public ResponseEntity<FieldTableDto> createFieldTable(@RequestBody @Valid FieldTable fieldTable,
                                                          UriComponentsBuilder uriBuilder) {
        FieldTableDto createdFieldTable = fieldTableService.createFieldTable(fieldTable);

        URI uri = uriBuilder.path("/fieldTable/{id}").buildAndExpand(createdFieldTable.getId()).toUri();
        return ResponseEntity.created(uri).body(createdFieldTable);
    }

    @PostMapping("/fieldTables")
    public ResponseEntity<List<FieldTableDto>> createFieldTables(@RequestBody List<@Valid FieldTable> fieldTables) {
        return ResponseEntity.created(null).body(fieldTableService.createFieldTables(fieldTables));
    }

    @GetMapping("/fieldTable/{fieldTableId}")
    public ResponseEntity<FieldTableDto> getFieldTable(@PathVariable long fieldTableId) {
        return ResponseEntity.ok(fieldTableService.getFieldTable(fieldTableId));
    }

    @GetMapping("/fieldTable")
    public ResponseEntity<List<FieldTableDto>> getFieldTables() {
        List<FieldTableDto> fieldTables = fieldTableService.getFieldTables();
        return ResponseEntity.ok(fieldTables);
    }

    @PutMapping("/fieldTable/{fieldTableId}")
    public ResponseEntity<FieldTableDto> updateFieldTable(@RequestBody @Valid FieldTable updatedFieldTable,
                                                          @PathVariable long fieldTableId) {
        try {
            return ResponseEntity.ok(fieldTableService.updateFieldTable(updatedFieldTable, fieldTableId));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping(path = "/fieldTable/{fieldTableId}", consumes = "application/merge-patch+json")
    public ResponseEntity<FieldTableDto> mergeUpdateFieldTable(@RequestBody FieldTable updatedFieldTable,
                                                               @PathVariable long fieldTableId) {
        try {
            return ResponseEntity.ok(fieldTableService.mergeUpdateFieldTable(updatedFieldTable, fieldTableId));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/fieldTable/{fieldTableId}")
    public ResponseEntity<?> deleteFieldTable(@PathVariable long fieldTableId) {
        fieldTableService.deleteFieldTable(fieldTableId);
        return ResponseEntity.noContent().build();
    }
}
