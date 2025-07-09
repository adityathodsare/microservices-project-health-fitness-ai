package com.microservices_project_fitness.healthcare_service.repository;

import com.microservices_project_fitness.healthcare_service.model.SymptomRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SymptomRepository extends MongoRepository<SymptomRecord, String> {
    List<SymptomRecord> findByUserId(String userId);
}