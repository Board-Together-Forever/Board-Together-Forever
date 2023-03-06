package com.codeup.codespringblog.repositories;

import com.codeup.codespringblog.models.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    GameSession findGameSessionsById(Long id);

}
