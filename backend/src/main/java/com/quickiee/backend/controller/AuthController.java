package com.quickiee.backend.controller;

import com.quickiee.backend.entity.User;
import com.quickiee.backend.service.AuthService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public User signup(@RequestBody Map<String, String> body) {
        return authService.signup(
                body.get("name"),
                body.get("email"),
                body.get("password")
        );
    }

    @PostMapping("/login")
    public User login(@RequestBody Map<String, String> body) {
        return authService.login(
                body.get("email"),
                body.get("password")
        );
    }
}
