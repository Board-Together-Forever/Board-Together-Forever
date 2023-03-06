package com.codeup.codespringblog.models;

import jakarta.persistence.*;

@Entity
@Table(name = "gameSession")
public class GameSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 100)
    private String gameSessionName;
    @Column(nullable = false)
    private String gameSessionHost;

    @Column(nullable = false)
    private String playerLimit;

    @Column(nullable = false)
    private String gameDuration;

    @Column(nullable = false)
    private String gameSessionTitle;

    @Column(nullable = false)
    private String gameSessionDescription;

    @ManyToOne
    @JoinColumn(name = "gamers_id")
    private Gamer gamer;

    public GameSession() {
    }

    public GameSession(int id, String gameSessionName, String gameSessionHost, String playerLimit, String gameDuration, String gameSessionTitle, String gameSessionDescription, Gamer gamer) {
        this.id = id;
        this.gameSessionName = gameSessionName;
        this.gameSessionHost = gameSessionHost;
        this.playerLimit = playerLimit;
        this.gameDuration = gameDuration;
        this.gameSessionTitle = gameSessionTitle;
        this.gameSessionDescription = gameSessionDescription;
        this.gamer = gamer;
    }

    public GameSession(String gameSessionName, String gameSessionHost, String playerLimit, String gameDuration, String gameSessionTitle, String gameSessionDescription, Gamer gamer) {
        this.gameSessionName = gameSessionName;
        this.gameSessionHost = gameSessionHost;
        this.playerLimit = playerLimit;
        this.gameDuration = gameDuration;
        this.gameSessionTitle = gameSessionTitle;
        this.gameSessionDescription = gameSessionDescription;
        this.gamer = gamer;
    }

    public GameSession(int id, String gameSessionName, String gameSessionHost, String playerLimit, String gameDuration, String gameSessionTitle, String gameSessionDescription) {
        this.id = id;
        this.gameSessionName = gameSessionName;
        this.gameSessionHost = gameSessionHost;
        this.playerLimit = playerLimit;
        this.gameDuration = gameDuration;
        this.gameSessionTitle = gameSessionTitle;
        this.gameSessionDescription = gameSessionDescription;
    }

    public GameSession(String gameSessionName, String gameSessionHost, String playerLimit, String gameDuration, String gameSessionTitle, String gameSessionDescription) {
        this.gameSessionName = gameSessionName;
        this.gameSessionHost = gameSessionHost;
        this.playerLimit = playerLimit;
        this.gameDuration = gameDuration;
        this.gameSessionTitle = gameSessionTitle;
        this.gameSessionDescription = gameSessionDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGameSessionName() {
        return gameSessionName;
    }

    public void setGameSessionName(String gameSessionName) {
        this.gameSessionName = gameSessionName;
    }

    public String getGameSessionHost() {
        return gameSessionHost;
    }

    public void setGameSessionHost(String gameSessionHost) {
        this.gameSessionHost = gameSessionHost;
    }

    public String getPlayerLimit() {
        return playerLimit;
    }

    public void setPlayerLimit(String playerLimit) {
        this.playerLimit = playerLimit;
    }

    public String getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(String gameDuration) {
        this.gameDuration = gameDuration;
    }

    public String getGameSessionTitle() {
        return gameSessionTitle;
    }

    public void setGameSessionTitle(String gameSessionTitle) {
        this.gameSessionTitle = gameSessionTitle;
    }

    public String getGameSessionDescription() {
        return gameSessionDescription;
    }

    public void setGameSessionDescription(String gameSessionDescription) {
        this.gameSessionDescription = gameSessionDescription;
    }


    public Gamer getGamer() {
        return gamer;
    }

    public void setGamer(Gamer gamer) {
        this.gamer = gamer;
    }
}
