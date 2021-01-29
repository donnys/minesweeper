package com.deviget.devtest.minesweeper.repository;

import com.deviget.devtest.minesweeper.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
