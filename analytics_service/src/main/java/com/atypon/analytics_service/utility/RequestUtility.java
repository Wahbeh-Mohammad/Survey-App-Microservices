package com.atypon.analytics_service.utility;

import com.atypon.analytics_service.models.analysis.AnalyzedSurvey;
import com.atypon.analytics_service.models.composed.FullSurvey;
import com.atypon.analytics_service.models.core.Submission;
import jdk.jfr.Frequency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class RequestUtility {
    ApplicationContext applicationContext;
    RestTemplate restTemplate;

    @Autowired
    public RequestUtility(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.restTemplate = new RestTemplate();
    }

    public FullSurvey fetchFullSurvey(Integer surveyId) {
        String uri = (String) applicationContext.getBean("mysqlApiUrl") + "/surveys/survey?surveyId=" + surveyId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity entity = new HttpEntity<>(headers);
        ResponseEntity<FullSurvey> result = restTemplate.exchange(uri, HttpMethod.GET, entity, FullSurvey.class);
        return result.getBody();
    }

    public List<Submission> fetchSurveySubmissions(Integer surveyId) {
        String uri = (String) applicationContext.getBean("mysqlApiUrl") + "/submissions/survey?surveyId=" + surveyId;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<List<Submission>> response = restTemplate.exchange(uri,
                                                                HttpMethod.GET,
                                                                entity,
                                            new ParameterizedTypeReference<List<Submission>>() {});
        return response.getBody();
    }

    public void sendUpdatesToMongoService(AnalyzedSurvey analyzedSurvey) {
        // send update request
        String updateUri = applicationContext.getBean("mongoApiUrl") + "/analysis/new";
        HttpEntity<AnalyzedSurvey> updateEntity = new HttpEntity<>(analyzedSurvey);
        restTemplate.exchange(updateUri, HttpMethod.POST, updateEntity, Boolean.class);
    }
}
