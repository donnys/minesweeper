package com.deviget.devtest.minesweeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableJpaRepositories("com.deviget.devtest.minesweeper.repository")
@EntityScan("com.deviget.devtest.minesweeper.model")
@SpringBootApplication
public class MinesweeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinesweeperApplication.class, args);
    }

}
