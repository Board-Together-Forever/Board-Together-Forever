package com.codeup.codespringblog.controllers;

import com.codeup.codespringblog.models.GameSession;
import com.codeup.codespringblog.models.User;
import com.codeup.codespringblog.repositories.GameSessionRepository;
import com.codeup.codespringblog.repositories.UserRepository;
import com.codeup.codespringblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        List<GameSession> gameSessionsList = gameSessionDao.findAll();
        model.addAttribute("gameSessionsList", gameSessionsList);
        return "gamesessions/index";
    }


//    SHOW PAGE GIVES DETAILS ABOUT GAME, LOGGED IN USERS CAN ACCESS AND JOIN GAME
    @GetMapping("/gamesessions/{id}")
    public String idpage(@PathVariable Long id, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GameSession gameSessions = gameSessionDao.findGameSessionsById(id);
        model.addAttribute("user", user);
        model.addAttribute("gameSessions", gameSessions);
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
        model.addAttribute("upc", upc);
        model.addAttribute("gamesession", new GameSession());
        return "gamesessions/create2";
    }

// POST MAPPING CREATES NEW GAME OR SAVES EDITED GAME WITH EXISTING ID
    @PostMapping("/gamesessions/create")
    public String createGameSession(@ModelAttribute GameSession gamesession) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        gamesession.setGameSessionHost(user);
//        List<User> userlist = new ArrayList<>();
//        userlist.add(user);
//        gamesession.setUsers(userlist);
        gameSessionDao.save(gamesession);
        emailService.prepareAndSend(gamesession);
        return "redirect:/gamesessions";
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
        model.addAttribute("gamesession", gameSessionDao.findGameSessionsById(id));
        return "gamesessions/edit";
    }

    @GetMapping("/gamesessions/join/{userId}/{id}")
    public String joinGame(@PathVariable long userId, @PathVariable long id){
        System.out.println(userId);
        System.out.println(id);
        User user = userDao.findUserById(userId);
        GameSession gameSession = gameSessionDao.findGameSessionsById(id);
        gameSession.addUser(user);
        gameSessionDao.save(gameSession);
        return "redirect:/gamesessions";
    }


//        FIRST STEP OF CREATING GAMESESSION, USED TO FIND GAME IN API - USED IN TESTING
//    @GetMapping("/gamesessions/find")
//    public String findGame() {
//        return "gamesessions/apiSearch";
//    }


//    USED TO EDIT GAME OF GAME SESSION - TESTING
//    @GetMapping("/gamesessions/find/{id}")
//    public String editGameSessionGame(Model model, @PathVariable long id) {
//        GameSession gamesession = gameSessionDao.findGameSessionsById(id);
//        model.addAttribute("gamesession", gamesession);
//        return "gamesessions/editGame";
//    }


//    NOT IN USE - ALLOWS CHANGING OF GAME AND RETAINS ID OF SESSION
//    @GetMapping("/gamesessions/{id}/{upc}")
//    public String updatedGame(Model model, @PathVariable long id, @PathVariable String upc) {
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        GameSession gamesession = gameSessionDao.findGameSessionsById(id);
//        if (user.getId() != gamesession.getGameSessionHost().getId()){
//            List<GameSession> gameSessionsList = gameSessionDao.findAll();
//            model.addAttribute("gameSessionsList", gameSessionsList);
//            return "redirect:/gamesessions";
//        }
//        gamesession.setUPC(upc);
//        model.addAttribute("id", id);
//        model.addAttribute("upc",upc);
//        model.addAttribute("gamesession", gameSessionDao.findGameSessionsById(id));
//        return "gamesessions/changedGame";
//    }

}
