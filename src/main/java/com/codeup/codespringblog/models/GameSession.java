package com.codeup.codespringblog.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "gameSession")
public class GameSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 100)
    private String gameSessionName;

    @Column(nullable = false)
    private String playerLimit;

    @Column(nullable = false)
    private String gameDuration;

    @Column(nullable = false)
    private String gameSessionTitle;

    @Column(nullable = false)
    private String gameSessionDescription;

    @Column
    private String UPC;

    //    @Column(nullable = false)
//    private String gameSessionHost;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private User gameSessionHost;



    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "gameSessions_users",
            joinColumns = {@JoinColumn(name = "gameSession_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user){
        users.add(user);
    }
    public void removeUserList(List<User> user){
        users.removeAll(user);
    }

    public GameSession() {
    }

    public GameSession(int id, String gameSessionName, String playerLimit, String gameDuration, String gameSessionTitle, String gameSessionDescription, String UPC, User gameSessionHost) {
        this.id = id;
        this.gameSessionName = gameSessionName;
        this.playerLimit = playerLimit;
        this.gameDuration = gameDuration;
        this.gameSessionTitle = gameSessionTitle;
        this.gameSessionDescription = gameSessionDescription;
        this.UPC = UPC;
        this.gameSessionHost = gameSessionHost;
    }

    public GameSession(int id, String gameSessionName, User gameSessionHost, String playerLimit, String gameDuration, String gameSessionTitle, String gameSessionDescription) {
        this.id = id;
        this.gameSessionName = gameSessionName;
        this.gameSessionHost = gameSessionHost;
        this.playerLimit = playerLimit;
        this.gameDuration = gameDuration;
        this.gameSessionTitle = gameSessionTitle;
        this.gameSessionDescription = gameSessionDescription;
    }

    public GameSession(String gameSessionName, String playerLimit, String gameDuration, String gameSessionTitle, String gameSessionDescription, String UPC, User gameSessionHost) {
        this.gameSessionName = gameSessionName;
        this.playerLimit = playerLimit;
        this.gameDuration = gameDuration;
        this.gameSessionTitle = gameSessionTitle;
        this.gameSessionDescription = gameSessionDescription;
        this.UPC = UPC;
        this.gameSessionHost = gameSessionHost;
    }

    public String getUPC() {
        return UPC;
    }

    public void setUPC(String UPC) {
        this.UPC = UPC;
    }

    public GameSession(String gameSessionName, User gameSessionHost, String playerLimit, String gameDuration, String gameSessionTitle, String gameSessionDescription) {
        this.gameSessionName = gameSessionName;
        this.gameSessionHost = gameSessionHost;
        this.playerLimit = playerLimit;
        this.gameDuration = gameDuration;
        this.gameSessionTitle = gameSessionTitle;
        this.gameSessionDescription = gameSessionDescription;
    }

//    public GameSession(int id, String gameSessionName, User gameSessionHost, String playerLimit, String gameDuration, String gameSessionTitle, String gameSessionDescription) {
//        this.id = id;
//        this.gameSessionName = gameSessionName;
//        this.gameSessionHost = gameSessionHost;
//        this.playerLimit = playerLimit;
//        this.gameDuration = gameDuration;
//        this.gameSessionTitle = gameSessionTitle;
//        this.gameSessionDescription = gameSessionDescription;
//    }

//    public GameSession(String gameSessionName, User gameSessionHost, String playerLimit, String gameDuration, String gameSessionTitle, String gameSessionDescription) {
//        this.gameSessionName = gameSessionName;
//        this.gameSessionHost = gameSessionHost;
//        this.playerLimit = playerLimit;
//        this.gameDuration = gameDuration;
//        this.gameSessionTitle = gameSessionTitle;
//        this.gameSessionDescription = gameSessionDescription;
//    }

    public GameSession(int id, String gameSessionName, String playerLimit, String gameDuration, String gameSessionTitle, String gameSessionDescription, String UPC, User gameSessionHost, List<User> users) {
        this.id = id;
        this.gameSessionName = gameSessionName;
        this.playerLimit = playerLimit;
        this.gameDuration = gameDuration;
        this.gameSessionTitle = gameSessionTitle;
        this.gameSessionDescription = gameSessionDescription;
        this.UPC = UPC;
        this.gameSessionHost = gameSessionHost;
        this.users = users;
    }

    public GameSession(String gameSessionName, String playerLimit, String gameDuration, String gameSessionTitle, String gameSessionDescription, String UPC, User gameSessionHost, List<User> users) {
        this.gameSessionName = gameSessionName;
        this.playerLimit = playerLimit;
        this.gameDuration = gameDuration;
        this.gameSessionTitle = gameSessionTitle;
        this.gameSessionDescription = gameSessionDescription;
        this.UPC = UPC;
        this.gameSessionHost = gameSessionHost;
        this.users = users;
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

    public User getGameSessionHost() {
        return gameSessionHost;
    }

    public void setGameSessionHost(User gameSessionHost) {
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
}


//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//}
