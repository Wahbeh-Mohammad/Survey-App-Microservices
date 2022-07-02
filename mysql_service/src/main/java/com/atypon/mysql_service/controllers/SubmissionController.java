package com.atypon.mysql_service.controllers;

import com.atypon.mysql_service.models.core.Submission;
import com.atypon.mysql_service.repositories.SubmissionRepository;
import com.atypon.mysql_service.utility.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {
    private final RequestUtility requestUtility;
    private final SubmissionRepository repository;

    @Autowired
    public SubmissionController(RequestUtility requestUtility, SubmissionRepository repository) {
        this.requestUtility = requestUtility;
        this.repository = repository;
    }

    @CrossOrigin
    @GetMapping("/survey") // ?surveyId={}
    public ResponseEntity<?> getSurveySubmissions(@RequestParam Integer surveyId) {
        if(surveyId == null) return new ResponseEntity<String>("Invalid survey id", HttpStatus.BAD_REQUEST);
        ArrayList<Submission> submissions = repository.fetchSurveySubmissions(surveyId);

        if(submissions == null)
            return new ResponseEntity<String>("No submissions found for the corresponding survey", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<ArrayList<Submission>>(submissions, HttpStatus.ACCEPTED);

    }

    @CrossOrigin
    @PostMapping("/new")
    public ResponseEntity<?> postNewSubmission(@RequestBody ArrayList<Submission> requestSubmission) {
        if(requestSubmission == null) return new ResponseEntity<String>("Invalid request body", HttpStatus.BAD_REQUEST);

        repository.createSurveySubmissions(requestSubmission);

        // Notify analytics service
        requestUtility.notifyAnalyticsService(requestSubmission.get(0).getSurveyId());

        return new ResponseEntity<String>("New submission created", HttpStatus.ACCEPTED);
    }

}
