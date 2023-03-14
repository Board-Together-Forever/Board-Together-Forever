package com.codeup.codespringblog.repositories;

import com.codeup.codespringblog.models.GameSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameSessionRepository extends JpaRepository<GameSession, Long> {
    GameSession findGameSessionsById(Long id);

    @Query("from GameSession a where a.gameSessionTitle LIKE  %:term% OR a.gameSessionDescription LIKE  %:term% OR a.gameSessionName like %:term%")
    List<GameSession> searchByTitleLike(@Param("term") String term);

}
