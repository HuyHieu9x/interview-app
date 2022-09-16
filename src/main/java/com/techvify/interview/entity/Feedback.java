package com.techvify.interview.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedback")
public class Feedback implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "note", length = 500)
    private String note;

    @NotNull
    @Column(name = "score")
    @Min(0)
    @Max(10)
    private int score;

    @Column(name = "question_id")
    private int questionId;

    @Column(name = "session_id")
    private int sessionId;
}
