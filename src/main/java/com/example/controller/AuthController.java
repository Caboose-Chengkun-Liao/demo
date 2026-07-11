package com.example.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.dto.LoginRequest;
import com.example.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest req) {
        String token = authService.login(req);

        ResponseCookie cookie = ResponseCookie.from("token", token)
            .httpOnly(true)
            .path("/api")
            .secure(true)
            .sameSite("None")
            .maxAge(Duration.ofHours(1))
            .build();
        
        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(Map.of("message", "Successfully login"));
            
    }
    
}
