package com.deviget.devtest.minesweeper.repository;

import com.deviget.devtest.minesweeper.model.FieldTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldTableRepository extends JpaRepository<FieldTable, Long> {
}
