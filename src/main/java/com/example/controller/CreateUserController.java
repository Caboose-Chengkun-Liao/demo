package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CreateUserRequest;
import com.example.dto.UserResponse;
import com.example.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/users")
public class CreateUserController {
    private final UserService userService;

    public CreateUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse create(@RequestBody CreateUserRequest req) {
        return userService.createUser(req);
    }
    
    @GetMapping
    public List<UserResponse> list() {
        return userService.listUsers();
    }
}
