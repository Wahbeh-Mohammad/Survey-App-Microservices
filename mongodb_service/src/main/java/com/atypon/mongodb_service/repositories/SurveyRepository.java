package com.atypon.mongodb_service.repositories;

import com.atypon.mongodb_service.models.core.Survey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends MongoRepository<Survey, Integer> {}