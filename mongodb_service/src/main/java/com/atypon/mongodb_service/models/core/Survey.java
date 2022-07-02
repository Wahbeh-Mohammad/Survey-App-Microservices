package com.atypon.mongodb_service.models.core;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "survey")
public class Survey {
    @Id
    private int surveyId; // Integer Id from SQL db.
    @Field
    private String surveyName;
    @Field
    private String surveyDescription;

    public Survey(){}

    public Survey(int surveyId, String surveyName, String surveyDescription) {
        this.surveyId = surveyId;
        this.surveyName = surveyName;
        this.surveyDescription = surveyDescription;
    }

    public int getSurveyId() { return surveyId; }
    public void setSurveyId(int surveyId) { this.surveyId = surveyId; }

    public String getSurveyName() { return surveyName; }
    public void setSurveyName(String surveyName) { this.surveyName = surveyName; }

    public String getSurveyDescription() { return surveyDescription; }
    public void setSurveyDescription(String surveyDescription) { this.surveyDescription = surveyDescription; }

    @Override
    public String toString() {
        return "Survey{" +
                "surveyId=" + surveyId +
                ", surveyName='" + surveyName + '\'' +
                ", surveyDescription='" + surveyDescription + '\'' +
                '}';
    }
}
