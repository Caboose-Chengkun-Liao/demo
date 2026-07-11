package com.example.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.LoginRequest;
import com.example.entity.User;
import com.example.repository.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        JwtService jwtService
    ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(LoginRequest req) {
        String username = req.getUsername();
        String password = req.getPassword();
        
        User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new BadCredentialsException("Wrong username or password, please check your information."));
        
        if(!(passwordEncoder.matches(password, user.getPassword())))
            throw new BadCredentialsException("Wrong username or password, please check your information.");

        return jwtService.generateToken(user.getUsername());
    }
}
