package com.atypon.mongodb_service.repositories;

import com.atypon.mongodb_service.models.composed.AnalyzedSurvey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalyzedSurveyRepository extends MongoRepository<AnalyzedSurvey, Integer> {}