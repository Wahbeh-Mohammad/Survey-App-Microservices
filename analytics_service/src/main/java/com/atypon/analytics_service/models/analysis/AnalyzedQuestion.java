package com.atypon.analytics_service.models.analysis;

import com.atypon.analytics_service.models.core.Question;

import java.util.ArrayList;

public class AnalyzedQuestion {
    Question question;
    String mostChosen, leastChosen;
    ArrayList<AnalyzedAnswer> answers;

    public AnalyzedQuestion() {}

    public AnalyzedQuestion(Question question, String mostChosen, String leastChosen, ArrayList<AnalyzedAnswer> answers) {
        this.question = question;
        this.mostChosen = mostChosen;
        this.leastChosen = leastChosen;
        this.answers = answers;
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public ArrayList<AnalyzedAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<AnalyzedAnswer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "AnalyzedQuestion{" +
                "question=" + question +
                ", mostChosen='" + mostChosen + '\'' +
                ", leastChosen='" + leastChosen + '\'' +
                ", answers=" + answers +
                '}';
    }
}
