package com.codeup.codespringblog.repositories;

import com.codeup.codespringblog.models.Gamer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamerRepository extends JpaRepository<Gamer, Long> {
    Gamer findGamerById(long id);
    Gamer findByUsername(String username);
}
