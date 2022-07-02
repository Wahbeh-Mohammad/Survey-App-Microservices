package com.atypon.mysql_service.models.composed;

import com.atypon.mysql_service.models.core.Survey;

import java.util.ArrayList;

public class FullSurvey {
    private Survey survey;
    private ArrayList<FullQuestion> questions;

    public FullSurvey() {}

    public FullSurvey(Survey survey, ArrayList<FullQuestion> questions) {
        this.survey = survey;
        this.questions = questions;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public ArrayList<FullQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<FullQuestion> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "FullSurvey{" +
                "survey=" + survey +
                ", questions=" + questions +
                '}';
    }
}
