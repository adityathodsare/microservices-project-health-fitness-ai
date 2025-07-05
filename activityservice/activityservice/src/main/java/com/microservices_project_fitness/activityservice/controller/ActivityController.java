package com.microservices_project_fitness.activityservice.controller;


import com.microservices_project_fitness.activityservice.Dto.ActivityRequestDto;
import com.microservices_project_fitness.activityservice.Dto.ActivityResponseDto;
import com.microservices_project_fitness.activityservice.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<ActivityResponseDto> trackActivity(@RequestBody ActivityRequestDto activityRequestDto) {
        return ResponseEntity.ok(activityService.trackActivity(activityRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponseDto>> getUserActivities(@RequestHeader("X-User-Id") String userId)  {
        return ResponseEntity.ok(activityService.getUserActivities(userId));
    }

    @GetMapping("/{ActivityId}")
    public ResponseEntity<ActivityResponseDto> getActivity(@PathVariable String ActivityId)  {
        return ResponseEntity.ok(activityService.getActivityById(ActivityId));
    }
}
