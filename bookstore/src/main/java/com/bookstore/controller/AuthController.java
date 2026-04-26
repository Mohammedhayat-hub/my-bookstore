package com.bookstore.controller;

import org.springframework.ui.Model;
import com.bookstore.entity.User;
import com.bookstore.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class AuthController {



    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // This method just shows the page
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // We pass an EMPTY user object so the HTML form doesn't crash
        // when it looks for th:object="${user}"
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        // 1. Check for validation errors (e.g., username too short)
        if (result.hasErrors()) {
            return "register";
        }

        // 2. Check if username already exists in DB
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("usernameError", "Username is already taken!");
            return "register";
        }

        // 3. Save the valid user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);

        return "redirect:/login?registered=true";
    }
}