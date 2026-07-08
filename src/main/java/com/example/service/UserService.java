package com.example.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.CreateUserRequest;
import com.example.dto.UserResponse;
import com.example.entity.User;
import com.example.exception.UsernameAlreadyExistsException;
import com.example.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
        UserRepository userRepository, 
        PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse createUser(CreateUserRequest req) {
        if(userRepository.existsByUsername(req.getUsername())) {
            throw new UsernameAlreadyExistsException("Username " + req.getUsername() + " already exists!");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        User save = userRepository.save(user);

        return toResponse(save);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public List<UserResponse> listUsers() {
        return userRepository.findAll().stream()
            .map(u -> toResponse(u))
            .toList();
    }

    private UserResponse toResponse(User user) {
        UserResponse res = new UserResponse();
        res.setUsername(user.getUsername());
        res.setId(user.getId());
        res.setCreatedAt(user.getCreatedAt());

        return res;
    }
}
