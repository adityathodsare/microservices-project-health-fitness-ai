package com.microservices_project_fitness.aisevice.service;


import com.microservices_project_fitness.aisevice.model.Activity;
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




    @RabbitListener(queues = "activity-queue")
    public void processActivity( Activity activity) {
        log.info("Received activity for recommendation : {}", activity);
    }
}
