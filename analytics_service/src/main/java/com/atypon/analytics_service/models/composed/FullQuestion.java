package com.atypon.analytics_service.models.composed;

import com.atypon.analytics_service.models.core.Answer;
import com.atypon.analytics_service.models.core.Question;

import java.util.ArrayList;

public class FullQuestion {
    private Question question;
    private ArrayList<Answer> answers;

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

    public ArrayList<Answer> getAnswers() { return answers; }
    public void setAnswers(ArrayList<Answer> answers) { this.answers = answers; }

    @Override
    public String toString() {
        return "FullQuestion{" +
                "question=" + question +
                ", answers=" + answers +
                '}';
    }
}
