package com.atypon.mysql_service.utility;

import com.atypon.mysql_service.models.core.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class RequestUtility {
    ApplicationContext applicationContext;
    RestTemplate restTemplate;

    @Autowired
    public RequestUtility(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.restTemplate = new RestTemplate();
    }

    public void forwardSurveyToMongo(Survey survey) {
        // Get the uri from the global storage and add request path
        String uri = (String) applicationContext.getBean("mongoApiUrl") + "/surveys/new";
        // Setup the headers to send and recieve json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        // Create the entity and set the survey as body.
        HttpEntity<Survey> entity = new HttpEntity<>(survey, headers);
        // send request & receive response
        ResponseEntity<Boolean> result = restTemplate.exchange(uri, HttpMethod.POST, entity, Boolean.class);
        if(result.getBody() != null && result.getBody().equals(Boolean.TRUE)) {
            System.out.println("Mongo document created");
        } else {
            System.out.println("Mongo document wasn't created");
        }
    }

    public void notifyAnalyticsService(int surveyId) {
        // send surveyId to the analytics service to re-analyze the survey submissions.
        String uri = (String) applicationContext.getBean("analyticsApiUrl") + "/updates/new?surveyId=" + surveyId;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Boolean> result = restTemplate.exchange(uri, HttpMethod.GET, entity, Boolean.class);
        if(result.getBody() != null && result.getBody().equals(Boolean.TRUE)) {
            System.out.println("Mongo document created/updated");
        } else {
            System.out.println("Mongo document wasn't created/updated");
        }
    }
}
