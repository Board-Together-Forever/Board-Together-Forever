package com.codeup.codespringblog.controllers;

import com.codeup.codespringblog.models.GameSession;
import com.codeup.codespringblog.models.User;
import com.codeup.codespringblog.repositories.GameSessionRepository;
import com.codeup.codespringblog.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class UserController {
    private final UserRepository userDao;
    private final GameSessionRepository gameSessionDao;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userDao, GameSessionRepository gameSessionDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.gameSessionDao = gameSessionDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "users/register";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "users/login";
    }


    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/login";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        userDao.save(user);
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = userDao.findUserById(user.getId());
        List<GameSession> gameSessionsList = gameSessionDao.findAll();
        model.addAttribute("gameSessionsList", gameSessionsList);
        model.addAttribute("user", user);
        return "users/profile";
    }

    @GetMapping("/settings/{id}")
    public String editProfile(@PathVariable Long id, Model model) {
        User sec = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findUserById(id);
        model.addAttribute("user", user);
        return "users/settings";
    }

    @GetMapping("/about-user/{id}")
    public String viewAboutUser(@PathVariable Long id, Model model) {
        User user = userDao.findUserById(id);
        model.addAttribute("user", user);
        return "users/aboutUser";
    }

    @GetMapping("/forgot/password")
    public String forgotPasswordShow() {

        return "/emails/forgotPassword";
    }

    @PostMapping("/forgot/password")
    public String forgotPasswordSubmit() {

        return "redirect:/login";
    }

    @GetMapping("/forgot/username")
    public String forgotEmailShow() {

        return "/emails/forgotEmail";
    }

    @PostMapping("/forgot/username")
    public String forgotEmailSubmit() {

        return "redirect:/login";
    }


}
