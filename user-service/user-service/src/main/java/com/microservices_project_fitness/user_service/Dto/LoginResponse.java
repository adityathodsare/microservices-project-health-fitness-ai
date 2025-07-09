package com.microservices_project_fitness.user_service.Dto;

public class LoginResponse {
    private String token;
    private UserResponseDto user;

    // Constructors, getters and setters
    public LoginResponse() {
    }

    public LoginResponse(String token, UserResponseDto user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }
}