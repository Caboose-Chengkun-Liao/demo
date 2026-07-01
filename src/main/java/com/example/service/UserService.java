package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.CreateUserRequest;
import com.example.dto.UserResponse;
import com.example.entity.User;
import com.example.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse createUser(CreateUserRequest req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(req.getPassword());

        User save = userRepository.save(user);

        return toResponse(save);
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
