package com.microservices_project_fitness.user_service.Dto;

import lombok.Data;


import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
