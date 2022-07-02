package com.atypon.mongodb_service.controllers;

import com.atypon.mongodb_service.models.composed.AnalyzedSurvey;
import com.atypon.mongodb_service.repositories.AnalyzedSurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    private final AnalyzedSurveyRepository analyzedSurveyRepository;

    @Autowired
    public AnalysisController(AnalyzedSurveyRepository analyzedSurveyRepository) {
        this.analyzedSurveyRepository = analyzedSurveyRepository;
    }

    @CrossOrigin
    @GetMapping("/survey") // ?surveyId=?
    public ResponseEntity<?> getAnalysis(@RequestParam int surveyId) {
        Optional<AnalyzedSurvey> analyzedSurvey = analyzedSurveyRepository.findById(surveyId);
        if(analyzedSurvey.isPresent())
            return new ResponseEntity<>(analyzedSurvey.get(), HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>("Survey wasn't analyzed yet.", HttpStatus.ACCEPTED);
    }

    @CrossOrigin
    @PostMapping("/new") // Add new Analyzed Surveys to the Database
    public ResponseEntity<?> postNewAnalysis(@RequestBody AnalyzedSurvey analyzedSurvey) {
        AnalyzedSurvey saved = analyzedSurveyRepository.save(analyzedSurvey);
        return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
    }
}
