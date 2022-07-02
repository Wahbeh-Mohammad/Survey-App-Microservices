package com.atypon.mongodb_service.models.composed;

public class AnalyzedAnswer {
    String title;
    int value;

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
                "title='" + title + '\'' +
                ", value=" + value +
                '}';
    }
}
