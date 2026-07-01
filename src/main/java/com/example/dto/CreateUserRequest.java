package com.example.dto;

public class CreateUserRequest {
    private String username;
    private String password;

    public CreateUserRequest() {}

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
