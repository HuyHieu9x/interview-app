package com.techvify.interview.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "level_interviewee")
public class LevelInterviewee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "setting",
            joinColumns = @JoinColumn(name = "level_interviewee_id"),
            inverseJoinColumns = @JoinColumn(name = "level_question_id"))
    private List<LevelQuestion> levelQuestions;

    public LevelInterviewee() {

    }

    public LevelInterviewee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public LevelInterviewee(int id, String name, List<LevelQuestion> levelQuestions) {
        this.id = id;
        this.name = name;
        this.levelQuestions = levelQuestions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LevelQuestion> getLevelQuestions() {
        return levelQuestions;
    }

    public void setLevelQuestions(List<LevelQuestion> levelQuestions) {
        this.levelQuestions = levelQuestions;
    }
}
