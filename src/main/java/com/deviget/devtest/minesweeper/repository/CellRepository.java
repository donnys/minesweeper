package com.deviget.devtest.minesweeper.repository;

import com.deviget.devtest.minesweeper.model.Cell;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CellRepository extends JpaRepository<Cell, Long> {
}
