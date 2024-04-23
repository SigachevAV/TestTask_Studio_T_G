package com.example.Minesweeper.repository;

import com.example.Minesweeper.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID>
{
    Optional<Game> findById(UUID uuid);
}
