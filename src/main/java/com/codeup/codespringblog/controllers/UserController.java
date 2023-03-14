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
import java.security.Principal;
import java.util.ArrayList;
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
    public String updateUser(@ModelAttribute User user, @RequestParam(name = "irl_name") String irlName, @RequestParam(name = "email") String email) {
        user.setIrl_name(irlName);
        user.setEmail(email);
        userDao.save(user);
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = userDao.findUserById(user.getId());
        List<GameSession> gameSessionsList = gameSessionDao.findAll();
        List<GameSession> userJoinedList = new ArrayList<>();
        for (GameSession gameSession : gameSessionsList) {
            for (User indiv : gameSession.getUsers()) {
                if (indiv.getId() == user.getId() && gameSession.getGameSessionHost().getId() != user.getId()) {
                    userJoinedList.add(gameSessionDao.findGameSessionsById((long) gameSession.getId()));
                }
            }
        }
        model.addAttribute("userJoinedList", userJoinedList);
        model.addAttribute("gameSessionsList", gameSessionsList);
        model.addAttribute("user", user);
        return "users/profile";
    }

    @GetMapping("/settings/{id}")
    public String editProfile(@PathVariable Long id, Model model) {
        User user = userDao.findUserById(id);
        model.addAttribute("user", user);
        return "users/settings";
    }

    @GetMapping("/aboutuser/{id}")
    public String viewAboutUser(@PathVariable Long id, Model model) {
        User user = userDao.findUserById(id);
        List<GameSession> gameSessionsList = gameSessionDao.findAll();
        List<GameSession> userJoinedList = new ArrayList<>();
        for (GameSession gameSession : gameSessionsList) {
            for (User indiv : gameSession.getUsers()) {
                if (indiv.getId() == user.getId()) {
                    userJoinedList.add(gameSessionDao.findGameSessionsById((long) gameSession.getId()));
                }
            }
        }
        model.addAttribute("userJoinedList", userJoinedList);
        model.addAttribute("gameSessionsList", gameSessionsList);
        model.addAttribute("user", user);
        return "users/aboutUser";
    }

    @GetMapping("/users/search")
    public String searchUsers(@RequestParam(name = "qu") String query, Model model) {
        if (query.length() > 0) {
            model.addAttribute("query", query);
        }
        model.addAttribute("userList", userDao.searchUsersBySearch(query));
        return "users/index";
    }

    @GetMapping("/aboutCreators")
    public String aboutCreators() {
        return "aboutUs";
    }

    @GetMapping("/update-pw")
    public String showForm(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "users/update-pw";
    }

    @PostMapping("/update-pw")
    public String updatePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 Principal principal) {
        String username = principal.getName();
        User user = userDao.findByUsername(username);
        if (passwordEncoder.matches(currentPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userDao.save(user);
            return "redirect:/profile";
        } else {
            // Current password is incorrect, show an error message
            // Can update this with query for error page or whatever eg redirect:/update?error
            return "redirect:/update-pw";
        }
    }
}
