package com.codeup.codespringblog.repositories;

import com.codeup.codespringblog.models.Post;
import com.codeup.codespringblog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserById(long id);
    User findByUsername(String username);
}