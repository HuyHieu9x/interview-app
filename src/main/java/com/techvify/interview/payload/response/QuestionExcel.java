package com.techvify.interview.payload.response;

public class QuestionExcel {
    private String answer;
    private String content;
    private int score;
    private String note;

    public QuestionExcel(String answer, String content, int score, String note) {
        this.answer = answer;
        this.content = content;
        this.score = score;
        this.note = note;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
