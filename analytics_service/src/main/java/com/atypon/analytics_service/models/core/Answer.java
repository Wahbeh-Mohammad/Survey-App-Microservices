package com.atypon.analytics_service.models.core;

public class Answer {
    private int answerId, questionId;
    private String value;

    public int getAnswerId() { return answerId; }
    public void setAnswerId(int answerId) { this.answerId = answerId; }

    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", questionId=" + questionId +
                ", value=" + value +
                '}';
    }
}
