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



    @GetMapping("/gamesessions")
    public String index(Model model) {
        List<GameSession> gameSessionsList = gameSessionDao.findAll();
        model.addAttribute("gameSessionsList", gameSessionsList);
        return "gamesessions/index";
    }

    @GetMapping("/gamesessions/find")
    public String findGame() {
        return "gamesessions/apiSearch";
    }

    @GetMapping("/gamesessions/find/{id}")
    public String editGameSessionGame(Model model, @PathVariable long id) {
        GameSession gamesession = gameSessionDao.findGameSessionsById(id);
        model.addAttribute("gamesession", gamesession);
        return "gamesessions/editGame";
    }

    @GetMapping("/gamesessions/{id}/{upc}")
    public String updatedGame(Model model, @PathVariable long id, @PathVariable String upc) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GameSession gamesession = gameSessionDao.findGameSessionsById(id);
        if (user.getId() != gamesession.getGameSessionHost().getId()){
            List<GameSession> gameSessionsList = gameSessionDao.findAll();
            model.addAttribute("gameSessionsList", gameSessionsList);
            return "redirect:/gamesessions";
        }
        gamesession.setUPC(upc);
        model.addAttribute("upc",upc);
        model.addAttribute("gamesession", gameSessionDao.findGameSessionsById(id));
        return "gamesessions/changedGame";
    }


    @GetMapping("/gamesessions/{id}")
    public String idpage(@PathVariable Long id, Model model) {
        GameSession gameSessions = gameSessionDao.findGameSessionsById(id);
        model.addAttribute("gameSessions", gameSessions);
        return "gamesessions/show";
    }

    @GetMapping("/gamesessions/create")
    public String create(Model model) {
        model.addAttribute("gamesession", new GameSession());
        return "gamesessions/create";
    }

    @GetMapping("/gamesessions/create/{upc}")
    public String createFound(@PathVariable String upc, Model model) {
        model.addAttribute("upc", upc);
        model.addAttribute("gamesession", new GameSession());
        return "gamesessions/create2";
    }


    @PostMapping("/gamesessions/create")
    public String createGameSession(@ModelAttribute GameSession gamesession) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        gamesession.setGameSessionHost(user);
        gameSessionDao.save(gamesession);
        emailService.prepareAndSend(gamesession);
        return "redirect:/gamesessions";
    }

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


}
