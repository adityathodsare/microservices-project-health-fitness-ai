package com.microservices_project_fitness.user_service.service;


import com.microservices_project_fitness.user_service.Dto.RegisterRequest;
import com.microservices_project_fitness.user_service.Dto.UserResponseDto;
import com.microservices_project_fitness.user_service.model.User;
import com.microservices_project_fitness.user_service.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {



    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public UserResponseDto register(@Valid RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("User Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User savedUser = userRepository.save(user);
        UserResponseDto savedUserResponseDto = new UserResponseDto();
        savedUserResponseDto.setId(savedUser.getId());
        savedUserResponseDto.setFirstName(savedUser.getFirstName());
        savedUserResponseDto.setLastName(savedUser.getLastName());
        savedUserResponseDto.setEmail(savedUser.getEmail());
        savedUserResponseDto.setPassword(savedUser.getPassword());
        savedUserResponseDto.setCreatedAt(savedUser.getCreatedAt());
        savedUserResponseDto.setUpdatedAt(savedUser.getUpdatedAt());

        return savedUserResponseDto;

    }

    public UserResponseDto getUserProfile(String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPassword(user.getPassword());
        userResponseDto.setCreatedAt(user.getCreatedAt());
        userResponseDto.setUpdatedAt(user.getUpdatedAt());

        return userResponseDto;


    }

    public Boolean existsByUserId(String userId) {
        log.info("Checking if user with id {} exists", userId);
        return userRepository.existsById(userId);
    }
}
