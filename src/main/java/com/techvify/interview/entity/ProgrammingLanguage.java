package com.techvify.interview.entity;

import javax.persistence.*;

@Entity
@Table(name = "programming_language")
public class ProgrammingLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;


    public ProgrammingLanguage() {
        // Do nothing because of X and Y.
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
}
