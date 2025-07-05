package com.microservices_project_fitness.activityservice.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
@EnableMongoAuditing
public class MongoConfig {
    // for Auditing of created and updated at
}
