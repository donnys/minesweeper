package com.deviget.devtest.minesweeper.repository;

import com.deviget.devtest.minesweeper.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
