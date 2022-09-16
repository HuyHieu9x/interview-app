package com.techvify.interview.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Question")
@Getter
@Setter
@NoArgsConstructor
public class Question {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "content", length = 500, nullable = false, unique = true)
    private String content;

    @ManyToOne
    @JoinColumn(name = "level_question_id", nullable = false)
    private LevelQuestion levelQuestion;

    @ManyToOne
    @JoinColumn(name = "programming_language_id", nullable = false)
    private ProgrammingLanguage language;

    @ManyToOne
    @JoinColumn(name = "framework_id", nullable = false)
    private Framework framework;

    @Column(name = "created_at")
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;

    @Column(name = "answer", length = 200, nullable = false)
    private String answer;

    @JsonIgnore
    @ManyToMany(mappedBy = "questions")
    Set<InterviewSession> interviewSessions;

    private Feedback feedback;

    public Question(int id, String content, LevelQuestion levelQuestion, ProgrammingLanguage language, Framework framework, LocalDateTime createdAt, LocalDateTime updatedAt, String answer, Feedback feedback) {
        this.id = id;
        this.content = content;
        this.levelQuestion = levelQuestion;
        this.language = language;
        this.framework = framework;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.answer = answer;
        this.feedback = feedback;
    }
}
