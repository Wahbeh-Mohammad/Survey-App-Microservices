package com.atypon.mongodb_service.controllers;

import com.atypon.mongodb_service.models.core.Survey;
import com.atypon.mongodb_service.repositories.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/surveys")
public class SurveyController {
    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyController(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @CrossOrigin
    @PostMapping("/new") // Create a new Survey
    public ResponseEntity<Boolean> postNewSurvey(@RequestBody Survey survey) {
        surveyRepository.save(survey);
        return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
    }

    @CrossOrigin
    @GetMapping("/") // return all Surveys
    public ResponseEntity<?> getAllSurveys() {
        List<Survey> surveys = surveyRepository.findAll();
        if(surveys.isEmpty())
            return new ResponseEntity<String>("No surveys yet.", HttpStatus.ACCEPTED);
        return new ResponseEntity<List<Survey>>(surveys, HttpStatus.ACCEPTED);
    }
}
