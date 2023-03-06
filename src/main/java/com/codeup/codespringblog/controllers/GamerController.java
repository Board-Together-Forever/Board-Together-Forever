package com.codeup.codespringblog.controllers;

import com.codeup.codespringblog.models.Gamer;
import com.codeup.codespringblog.repositories.GamerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GamerController {
    private final GamerRepository gamerDao;
    private final PasswordEncoder passwordEncoder;

    public GamerController(GamerRepository gamerDao, PasswordEncoder passwordEncoder) {
        this.gamerDao = gamerDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/gamers/register")
    public String showSignupForm(Model model){
        model.addAttribute("gamer", new Gamer());
        return "gamers/register";
    }

    @GetMapping("/gamers/login")
    public String loginPage(){
        return "gamers/login";
    }

    @PostMapping("/gamers/register")
    public String saveGamer(@ModelAttribute Gamer gamer){
        String hash = passwordEncoder.encode(gamer.getPassword());
        gamer.setPassword(hash);
        gamerDao.save(gamer);
        return "redirect:gamers/login";
    }

    @GetMapping("/gamers/profile")
    public String showProfile() {return "gamers/profile";}
}
