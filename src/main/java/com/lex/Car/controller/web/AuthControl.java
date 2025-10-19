package com.lex.Car.controller.web;

import com.lex.Car.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthControl {

    private final UserService userService;

    public AuthControl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String)) {
            return "redirect:/"; // change "/" to "/dashboard" if you prefer
        }

        return "login"; // login.html
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new Object());
        return "register"; // register.html
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        userService.registerUser(username, password);
        return "redirect:/login";
    }
}