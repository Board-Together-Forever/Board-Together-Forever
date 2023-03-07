package com.codeup.codespringblog.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="users")
public class GameSessionUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "users")
    private List<GameSession> gamesessions;
}
