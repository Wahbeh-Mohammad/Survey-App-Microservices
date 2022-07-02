package com.atypon.analytics_service.models.analysis;

import com.atypon.analytics_service.models.core.Survey;

import java.util.ArrayList;

public class AnalyzedSurvey {
    int id;
    Survey survey;
    ArrayList<AnalyzedQuestion> questions;
    int numberOfSubmissions;

    public AnalyzedSurvey(){}

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
