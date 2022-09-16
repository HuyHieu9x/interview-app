package com.techvify.interview.entity;

import javax.persistence.*;

@Entity
@Table(name = "setting")
public class Setting {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "number_question")
    private int numberQuestion;

    @Column(name = "level_question_id")
    private int levelQuestionId;

    @Column(name = "level_interviewee_id")
    private int levelIntervieweeId;

    public Setting() {
    }

    public Setting(int id, int numberQuestion, int levelQuestionId, int levelIntervieweeId) {
        this.id = id;
        this.numberQuestion = numberQuestion;
        this.levelQuestionId = levelQuestionId;
        this.levelIntervieweeId = levelIntervieweeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberQuestion() {
        return numberQuestion;
    }

    public void setNumberQuestion(int numberQuestion) {
        this.numberQuestion = numberQuestion;
    }

    public int getLevelQuestionId() {
        return levelQuestionId;
    }

    public void setLevelQuestionId(int levelQuestionId) {
        this.levelQuestionId = levelQuestionId;
    }

    public int getLevelIntervieweeId() {
        return levelIntervieweeId;
    }

    public void setLevelIntervieweeId(int levelIntervieweeId) {
        this.levelIntervieweeId = levelIntervieweeId;
    }
}
