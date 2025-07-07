package com.microservices_project_fitness.aisevice.service;


import com.microservices_project_fitness.aisevice.model.Activity;
import com.microservices_project_fitness.aisevice.model.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMsgListener {

    private final ActivityAiService activityAiService;
    private final RecommendationService recommendationService;



//    @RabbitListener(queues = "activity-queue")
//    public void processActivity( Activity activity) {
//        log.info("Received activity for recommendation : {}", activity);
//        log.info("Recommendation: {}", activityAiService.generateRecommendation(activity));
//    }

    @RabbitListener(queues = "activity-queue")
    public void processActivity(Activity activity) {
        log.info("Received activity for recommendation: {}", activity);
        Recommendation recommendation = activityAiService.generateRecommendation(activity);
        recommendationService.saveRecommendation(recommendation);
        log.info("Recommendation saved: {}", recommendation);
    }
}
