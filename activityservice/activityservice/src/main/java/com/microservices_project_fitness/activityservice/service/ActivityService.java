package com.microservices_project_fitness.activityservice.service;


import com.microservices_project_fitness.activityservice.Dto.ActivityRequestDto;
import com.microservices_project_fitness.activityservice.Dto.ActivityResponseDto;
import com.microservices_project_fitness.activityservice.model.Activity;
import com.microservices_project_fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    private final UserValidationService userValidationService;

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.queue.name}")
    private String queueName;




    public ActivityResponseDto trackActivity(ActivityRequestDto activityRequestDto) {

        Boolean isValid = userValidationService.validateUser(activityRequestDto.getUserId());
        if (!isValid) {
            throw new RuntimeException("User not found");
        }
        Activity activity = Activity.builder()
                .userId(activityRequestDto.getUserId())
                .activityType(activityRequestDto.getActivityType())
                .duration(activityRequestDto.getDuration())
                .caloriesBurned(activityRequestDto.getCaloriesBurned())
                .startTime(activityRequestDto.getStartTime())
                .additionalMatrices(activityRequestDto.getAdditionalMatrices())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        // publish to rabbit mq for AI recommendation

        try{
            rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);

        } catch (Exception e) {
            log.error("Error sending message to RabbitMQ: {}", e.getMessage());

        }
        return mapToDto(savedActivity);


    }

    private ActivityResponseDto mapToDto(Activity activity) {
        
        ActivityResponseDto activityResponseDto = new ActivityResponseDto();
        activityResponseDto.setId(activity.getId());
        activityResponseDto.setUserId(activity.getUserId());
        activityResponseDto.setActivityType(activity.getActivityType());
        activityResponseDto.setDuration(activity.getDuration());
        activityResponseDto.setCaloriesBurned(activity.getCaloriesBurned());
        activityResponseDto.setStartTime(activity.getStartTime());
        activityResponseDto.setAdditionalMatrices(activity.getAdditionalMatrices());
        activityResponseDto.setCreatedAt(activity.getCreatedAt());
        activityResponseDto.setUpdatedAt(activity.getUpdatedAt());
        return activityResponseDto;
    }

    public List<ActivityResponseDto> getUserActivities(String userId) {
       List<Activity>  activities = activityRepository.findByUserId(userId);

        return activities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ActivityResponseDto getActivityById(String activityId) {
         return activityRepository
                .findById(activityId)
                .map(this::mapToDto)
                .orElseThrow(()-> new RuntimeException("Activity not found"));

    }

}
