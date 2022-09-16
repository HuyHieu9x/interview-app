package com.techvify.interview.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "framework")
@NoArgsConstructor
@Getter
@Setter
public class Framework {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "programming_language_id", nullable = false)
    private ProgrammingLanguage programmingLanguage;

}
