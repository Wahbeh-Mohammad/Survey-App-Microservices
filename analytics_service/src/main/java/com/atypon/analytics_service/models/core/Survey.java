package com.atypon.analytics_service.models.core;

public class Survey {
    private int surveyId;
    private String surveyName, surveyDescription;

    public int getSurveyId() { return surveyId; }
    public void setSurveyId(int surveyId) { this.surveyId = surveyId; }

    public String getSurveyName() { return surveyName; }
    public void setSurveyName(String surveyName) { this.surveyName = surveyName; }

    public String getSurveyDescription() { return surveyDescription; }
    public void setSurveyDescription(String surveyDescription) { this.surveyDescription = surveyDescription; }

    @Override
    public String toString() {
        return "Survey{" +
                "SurveyId=" + surveyId +
                ", surveyDescription='" + surveyDescription + '\'' +
                ", surveyName='" + surveyName + '\'' +
                '}';
    }
}
