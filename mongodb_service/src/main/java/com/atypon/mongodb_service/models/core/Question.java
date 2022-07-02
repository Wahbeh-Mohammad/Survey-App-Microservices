package com.atypon.mongodb_service.models.core;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "questions")
public class Question {
    @Id
    int questionId;
    @Field
    int surveyId;
    @Field
    String prompt;
    @Field
    String mostChosen;
    @Field
    String leastChosen;

    public Question() {}

    public Question(int questionId, int surveyId, String prompt, String mostChosen, String leastChosen) {
        this.questionId = questionId;
        this.surveyId = surveyId;
        this.prompt = prompt;
        this.mostChosen = mostChosen;
        this.leastChosen = leastChosen;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getSurveyId() {
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

    public String getMostChosen() {
        return mostChosen;
    }

    public void setMostChosen(String mostChosen) {
        this.mostChosen = mostChosen;
    }

    public String getLeastChosen() {
        return leastChosen;
    }

    public void setLeastChosen(String leastChosen) {
        this.leastChosen = leastChosen;
    }

    @Override
    public String toString() {
        return "AnalyzedQuestion{" +
                "questionId=" + questionId +
                ", surveyId=" + surveyId +
                ", prompt='" + prompt + '\'' +
                ", mostChosen='" + mostChosen + '\'' +
                ", leastChosen='" + leastChosen + '\'' +
                '}';
    }
}
