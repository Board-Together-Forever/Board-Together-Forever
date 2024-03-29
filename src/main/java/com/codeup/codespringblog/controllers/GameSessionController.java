package com.codeup.codespringblog.controllers;

import com.codeup.codespringblog.models.GameSession;
import com.codeup.codespringblog.models.User;
import com.codeup.codespringblog.repositories.GameSessionRepository;
import com.codeup.codespringblog.repositories.UserRepository;
import com.codeup.codespringblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

@Controller
public class GameSessionController {
    private final GameSessionRepository gameSessionDao;

    private final UserRepository userDao;

    private final EmailService emailService;

    public GameSessionController(GameSessionRepository gameSessionDao, UserRepository userDao, EmailService emailService) {
        this.gameSessionDao = gameSessionDao;
        this.userDao = userDao;
        this.emailService = emailService;
    }


//    SHOW ALL ACTIVE GAME SESSIONS IN DATABASE
    @GetMapping("/gamesessions")
    public String index(Model model) {
        String query = "";
        List<GameSession> gameSessionsList = gameSessionDao.findAll();
        model.addAttribute("gameSessionsList", gameSessionsList);
        model.addAttribute("query", query);
        return "gamesessions/index";
    }


//    SHOW PAGE GIVES DETAILS ABOUT GAME, LOGGED IN USERS CAN ACCESS AND JOIN GAME
    @GetMapping("/gamesessions/{id}")
    public String idpage(@PathVariable Long id, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GameSession gameSessions = gameSessionDao.findGameSessionsById(id);
        int playerFound = 0;
        model.addAttribute("user", user);
        model.addAttribute("gameSessions", gameSessions);
        if (gameSessions.getUsers().size() > 0) {
            for(User indiv : gameSessions.getUsers()){
                if(indiv.getId() == user.getId()){
                    playerFound++;
                }
            }
            model.addAttribute("playerFound", playerFound);
        }
        else {
            System.out.println("No users");
        }
        return "gamesessions/show";
    }


    //    FIRST STEP OF CREATING GAMESESSION, USED TO FIND GAME IN API
    @GetMapping("/gamesessions/create")
    public String create(Model model) {
        model.addAttribute("gamesession", new GameSession());
        return "gamesessions/apiSearch";
    }

    @GetMapping("/gamesessions/create/{upc}")
    public String createFound(@PathVariable String upc, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("upc", upc);
        model.addAttribute("gamesession", new GameSession());
        return "gamesessions/create";
    }

// POST MAPPING CREATES NEW GAME OR SAVES EDITED GAME WITH EXISTING ID
    @PostMapping("/gamesessions/create")
    public String createGameSession(@ModelAttribute GameSession gamesession) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        gamesession.setGameSessionHost(user);
        gameSessionDao.save(gamesession);
        emailService.prepareAndSend(gamesession);
        return "redirect:/gamesessions/join/" + user.getId() +"/" + gamesession.getId();
    }


//    EDITS EXISTING GAME DETAILS, SAVES EXISTING IDS
    @GetMapping("/gamesessions/{id}/edit")
    public String editGameSession(Model model, @PathVariable long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GameSession gamesession = gameSessionDao.findGameSessionsById(id);
        if (user.getId() != gamesession.getGameSessionHost().getId()){
            List<GameSession> gameSessionsList = gameSessionDao.findAll();
            model.addAttribute("gameSessionsList", gameSessionsList);
            return "redirect:/gamesessions";
        }
        model.addAttribute("id", id);
        model.addAttribute("gamesession", gameSessionDao.findGameSessionsById(id));
        return "gamesessions/edit";
    }

    @GetMapping("/gamesessions/{id}/editplayers")
    public String editGameSessionPlayers(Model model, @PathVariable long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GameSession gamesession = gameSessionDao.findGameSessionsById(id);
        long remainingSpots = parseInt(gamesession.getPlayerLimit()) - gamesession.getUsers().size();
        if (user.getId() != gamesession.getGameSessionHost().getId()){
            List<GameSession> gameSessionsList = gameSessionDao.findAll();
            model.addAttribute("gameSessionsList", gameSessionsList);
            return "redirect:/gamesessions";
        }
        model.addAttribute("id", id);
        model.addAttribute("gamesession", gameSessionDao.findGameSessionsById(id));
        model.addAttribute("remainingSpots", remainingSpots);
        return "gamesessions/editPlayers";
    }


    @GetMapping("/gamesessions/join/{userId}/{id}")
    public String joinGame(@PathVariable long userId, @PathVariable long id){
        User user = userDao.findUserById(userId);
        GameSession gameSession = gameSessionDao.findGameSessionsById(id);
        gameSession.addUser(user);
        gameSessionDao.save(gameSession);
        return "redirect:/gamesessions";
    }

    @GetMapping("/gamesessions/remove/{userId}/{id}")
    public String removePlayer(@PathVariable long userId, @PathVariable long id){
        User user = userDao.findUserById(userId);
        GameSession gameSession = gameSessionDao.findGameSessionsById(id);
        gameSession.removePlayer(user);
        gameSessionDao.save(gameSession);
        return "redirect:/gamesessions";
    }

    @GetMapping("/gamesessions/delete/{id}")
    public String deleteGame(@PathVariable long id){
        System.out.println("id = " + id);
        gameSessionDao.findGameSessionsById(id).removeUserList(gameSessionDao.findGameSessionsById(id).getUsers());
        gameSessionDao.deleteById(id);
//        gameSessionDao.deleteById(7L);
        return "redirect:/gamesessions";
    }

    @GetMapping("/gamesessions/search")
    public String searchGameSessions(@RequestParam(name="q") String query, Model model) {
        if (query.length() > 0){
        model.addAttribute("query", query);
        }
        model.addAttribute("gameSessionsList", gameSessionDao.searchByTitleLike(query));
        return "gamesessions/index";
    }

    @GetMapping("/gamesessions/search/notfull")
    public String searchGameSessions(Model model) {
        List<GameSession> gameSessionList = gameSessionDao.findAll();
        List<GameSession> notfull = new ArrayList<>();
        for (GameSession gamesession : gameSessionList){
            if (gamesession.getUsers().size() < parseInt(gamesession.getPlayerLimit())){
                notfull.add(gamesession);
            }
        }
        model.addAttribute("gameSessionsList", notfull);
        return "gamesessions/index";
    }

}
