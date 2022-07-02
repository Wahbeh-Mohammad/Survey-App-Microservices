package com.atypon.mysql_service.models.core;

public class Submission {
    private int submissionId, surveyId, questionId;
    private String answer;

    public Integer getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Submission{" +
                "submissionId=" + submissionId +
                ", surveyId=" + surveyId +
                ", questionId=" + questionId +
                ", answer='" + answer + '\'' +
                '}';
    }
}
