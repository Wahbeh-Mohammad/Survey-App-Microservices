package com.atypon.mongodb_service.models.core;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "answers")
public class Answer {
    @Id
    int answerId;
    @Field
    int questionId;
    @Field
    String title;
    @Field
    int value;

    public Answer() {}

    public Answer(int answerId, int questionId, String title, int value) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.title = title;
        this.value = value;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "AnalyzedAnswer{" +
                "answerId=" + answerId +
                ", title='" + title + '\'' +
                ", value=" + value +
                '}';
    }
}
