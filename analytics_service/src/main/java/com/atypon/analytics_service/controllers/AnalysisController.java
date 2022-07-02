package com.atypon.analytics_service.controllers;

import com.atypon.analytics_service.models.analysis.AnalyzedSurvey;
import com.atypon.analytics_service.models.composed.FullSurvey;
import com.atypon.analytics_service.models.core.Submission;
import com.atypon.analytics_service.utility.Analyzer;
import com.atypon.analytics_service.utility.RequestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/updates")
public class AnalysisController {
    RequestUtility requestUtility;
    Analyzer analyzer;

    @Autowired
    public AnalysisController(RequestUtility requestUtility) {
        this.requestUtility = requestUtility;
        analyzer = new Analyzer();
    }

    @CrossOrigin
    @GetMapping("/new") // ?surveyId={}
    public ResponseEntity<?> postNewAnalysis(@RequestParam Integer surveyId) {
        if(surveyId == null)
            return new ResponseEntity<String>("Invalid request body.", HttpStatus.BAD_REQUEST);
        // Fetch the survey & its submissions.
        FullSurvey fullSurvey = requestUtility.fetchFullSurvey(surveyId);
        ArrayList<Submission> submissions = (ArrayList<Submission>) requestUtility.fetchSurveySubmissions(surveyId);
        // Create new Analysis.
        AnalyzedSurvey analyzedSurvey = analyzer.createNewAnalyzedSurvey(fullSurvey, submissions);
        // Send new Analysis to Mongo and return a response.
        requestUtility.sendUpdatesToMongoService(analyzedSurvey);
        return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
    }
}
