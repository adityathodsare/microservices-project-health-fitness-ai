package com.microservices_project_fitness.user_service.controller;


import com.microservices_project_fitness.user_service.Dto.LoginRequest;
import com.microservices_project_fitness.user_service.Dto.LoginResponse;
import com.microservices_project_fitness.user_service.Dto.RegisterRequest;
import com.microservices_project_fitness.user_service.Dto.UserResponseDto;
import com.microservices_project_fitness.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserProfile(@PathVariable String userId) {
        return  ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @PostMapping("/register")
    public  ResponseEntity<UserResponseDto> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> validateUser(@PathVariable String userId) {
        return  ResponseEntity.ok(userService.existsByUserId(userId));
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> validateCredentials(@RequestBody LoginRequest request) {
        boolean isValid = userService.validateCredentials(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(isValid);
    }
}
