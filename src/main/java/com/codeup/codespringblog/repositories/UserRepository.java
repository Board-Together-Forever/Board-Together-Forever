package com.codeup.codespringblog.repositories;

import com.codeup.codespringblog.models.GameSession;
import com.codeup.codespringblog.models.Post;
import com.codeup.codespringblog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(long id);
    User findByUsername(String username);

    @Query("from User a where a.username LIKE  %:term% OR a.email LIKE  %:term%")
    List<User> searchUsersBySearch(@Param("term") String term);
}