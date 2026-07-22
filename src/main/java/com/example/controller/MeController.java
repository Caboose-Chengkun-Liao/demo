package com.example.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class MeController {
    @GetMapping("/api/me")
    public Map<String, String> me(Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        return Map.of("username", username);
    }
    
}
