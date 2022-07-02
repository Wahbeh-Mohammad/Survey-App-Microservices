package com.atypon.analytics_service.models.core;

public class Submission {
    private int submissionId, surveyId, questionId;
    private String answer;

    public int getSubmissionId() { return submissionId; }
    public void setSubmissionId(int submissionId) { this.submissionId = submissionId; }

    public int getSurveyId() { return surveyId; }
    public void setSurveyId(int surveyId) { this.surveyId = surveyId; }

    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) {this.answer = answer;}

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
