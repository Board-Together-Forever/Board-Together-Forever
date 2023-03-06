package com.codeup.codespringblog.controllers;

import com.codeup.codespringblog.models.GameSession;
import com.codeup.codespringblog.models.Gamer;
import com.codeup.codespringblog.repositories.GameSessionRepository;
import com.codeup.codespringblog.repositories.GamerRepository;
import com.codeup.codespringblog.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GameSessionController {
    private final GameSessionRepository gameSessionDao;

    private final GamerRepository gamerDao;

    private final EmailService emailService;

    public GameSessionController(GameSessionRepository gameSessionDao, GamerRepository gamerDao, EmailService emailService) {
        this.gameSessionDao = gameSessionDao;
        this.gamerDao = gamerDao;
        this.emailService = emailService;
    }



    @GetMapping("/gamesessions")
    public String index(Model model) {
        List<GameSession> gameSessionsList = gameSessionDao.findAll();
        model.addAttribute("gameSessionsList", gameSessionsList);
        return "gamesessions/index";
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

    @PostMapping("gamesessions/create")
    public String createGameSession(@ModelAttribute GameSession gameSession) {
        Gamer gamer = (Gamer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        gameSession.setGamer(gamer);
        gameSessionDao.save(gameSession);
        emailService.prepareAndSend(gameSession);
        return "redirect:/gamesessions";
    }

    @GetMapping("gamesessions/{id}/edit")
    public String editGameSession(Model model, @PathVariable long id) {
        Gamer gamer = (Gamer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GameSession gameSession = gameSessionDao.findGameSessionsById(id);
        if (gamer.getId() != gameSession.getGamer().getId()){
            List<GameSession> gameSessionList = gameSessionDao.findAll();
            model.addAttribute("gameSessionsList", gameSessionList);
            return "redirect:/gamesessions";
        }
        model.addAttribute("gameSessions", gameSessionDao.findGameSessionsById(id));
        return "gamesessions/edit";
    }

}
