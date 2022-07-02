package com.atypon.mongodb_service.models.composed;

import com.atypon.mongodb_service.models.core.Survey;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;

@Document(collection = "analyzed_surveys")
public class AnalyzedSurvey {
    @Id
    int id;
    @Field
    Survey survey;
    @Field
    ArrayList<AnalyzedQuestion> questions;
    @Field
    int numberOfSubmissions;

    public AnalyzedSurvey() {}

    public AnalyzedSurvey(int id, Survey survey, ArrayList<AnalyzedQuestion> questions, int numberOfSubmissions) {
        this.id = id;
        this.survey = survey;
        this.questions = questions;
        this.numberOfSubmissions = numberOfSubmissions;
    }

    public int getNumberOfSubmissions() {
        return numberOfSubmissions;
    }

    public void setNumberOfSubmissions(int numberOfSubmissions) {
        this.numberOfSubmissions = numberOfSubmissions;
    }

    public Survey getSurvey() {
        return survey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public ArrayList<AnalyzedQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<AnalyzedQuestion> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "AnalyzedSurvey{" +
                "id=" + id +
                ", survey=" + survey +
                ", questions=" + questions +
                ", numberOfSubmissions=" + numberOfSubmissions +
                '}';
    }
}