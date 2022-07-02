package com.atypon.mysql_service.models.core;

public class Question {
    private Integer questionId, surveyId;
    private String prompt;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", surveyId=" + surveyId +
                ", prompt='" + prompt + '\'' +
                '}';
    }
}
