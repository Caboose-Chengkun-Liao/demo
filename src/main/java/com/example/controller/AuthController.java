package com.example.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.dto.LoginRequest;
import com.example.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest req) {
        return authService.login(req);
    }
    
}
