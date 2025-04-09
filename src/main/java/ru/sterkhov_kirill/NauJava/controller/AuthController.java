package ru.sterkhov_kirill.NauJava.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sterkhov_kirill.NauJava.dto.UserRegister;
import ru.sterkhov_kirill.NauJava.exception.UsernameExists;
import ru.sterkhov_kirill.NauJava.service.UserService;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserRegister());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegister userRegister, Model model) {
        try {
            userService.createUser(userRegister);
        } catch (UsernameExists e) {
            model.addAttribute("message", e.getMessage());
            return "register";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
