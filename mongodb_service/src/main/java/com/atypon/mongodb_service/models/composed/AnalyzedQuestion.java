package com.atypon.mongodb_service.models.composed;

import com.atypon.mongodb_service.models.core.Question;
import java.util.ArrayList;

public class AnalyzedQuestion {
    Question question;
    ArrayList<AnalyzedAnswer> answers;
    String mostChosen, leastChosen;

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
                ", answers=" + answers +
                ", mostChosen='" + mostChosen + '\'' +
                ", leastChosen='" + leastChosen + '\'' +
                '}';
    }
}
