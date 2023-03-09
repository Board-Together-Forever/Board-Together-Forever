package com.codeup.codespringblog.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String primary_game;

    @Column
    private String irl_name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gameSessionHost")
    private List<GameSession> gameSessions;

    @ManyToMany(mappedBy = "users")
    private List<GameSession> joinedSessions;

    public User() {
    }
    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        email = copy.email;
        username = copy.username;
        password = copy.password;
        primary_game = copy.primary_game;
        irl_name = copy.irl_name;
    }

    public User(long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }
    public User(long id, String username, String email, String password, List<GameSession> gameSessions) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gameSessions = gameSessions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrimary_game() {
        return primary_game;
    }

    public void setPrimary_game(String primary_game) {
        this.primary_game = primary_game;
    }

    public String getIrl_name() {
        return irl_name;
    }

    public void setIrl_name(String irl_name) {
        this.irl_name = irl_name;
    }

    public List<GameSession> getGameSessions() {
        return gameSessions;
    }

    public void setGameSessions(List<GameSession> gameSessions) {
        this.gameSessions = gameSessions;
    }
}
