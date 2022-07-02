package com.atypon.mysql_service.controllers;

import com.atypon.mysql_service.models.composed.FullSurvey;
import com.atypon.mysql_service.models.core.Survey;
import com.atypon.mysql_service.repositories.SurveyRepository;
import com.atypon.mysql_service.utility.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/surveys")
public class SurveyController {
    private final RequestUtility requestUtility;
    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyController(RequestUtility requestUtility, SurveyRepository repository) {
        this.requestUtility = requestUtility;
        this.surveyRepository = repository;
    }

    @CrossOrigin
    @GetMapping("/")
    public ResponseEntity<?> getAllSurveys() {
        ArrayList<Survey> surveys = surveyRepository.fetchAllSurveys();

        if(surveys == null)
            return new ResponseEntity<String>("No surveys found", HttpStatus.ACCEPTED);
        return new ResponseEntity<ArrayList<Survey>>(surveys, HttpStatus.ACCEPTED);
    }

    @CrossOrigin
    @GetMapping("/survey") // ?surveyId={}
    public ResponseEntity<?> getSpecificSurvey(@RequestParam Integer surveyId) {
        if(surveyId == null) return new ResponseEntity<String>("Invalid survey id", HttpStatus.BAD_REQUEST);

        FullSurvey survey = surveyRepository.fetchSpecificSurvey(surveyId);

        if(survey == null)
            return new ResponseEntity<String>("No survey with Specified Id", HttpStatus.ACCEPTED);
        return new ResponseEntity<FullSurvey>(survey, HttpStatus.ACCEPTED);
    }

    @CrossOrigin
    @PostMapping("/new")
    public ResponseEntity<?> postNewSurvey(@RequestBody Survey requestSurvey) {
        if(requestSurvey == null) return new ResponseEntity<String>("Survey cannot be empty/null", HttpStatus.BAD_REQUEST);

        Integer newSurveyId = surveyRepository.createNewSurvey(requestSurvey);

        if(newSurveyId == null)
            return new ResponseEntity<String>("Invalid survey details", HttpStatus.BAD_REQUEST);

        // Forward new survey details to mongo service.
        requestSurvey.setSurveyId(newSurveyId);
        requestUtility.forwardSurveyToMongo(requestSurvey);

        return new ResponseEntity<Integer>(newSurveyId, HttpStatus.ACCEPTED);
    }

}
