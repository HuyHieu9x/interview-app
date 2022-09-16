package com.techvify.interview.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Getter
@Setter
@NoArgsConstructor
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column
    @Size(max = 20)
    private String username;

    @NotBlank
    @Column
    private String name;

    @NotBlank
    @Column
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    @Column
    @Size(max = 120)
    private String password;

    @Column
    @Min(0)
    @Max(60)
    private int age;

    @NotBlank
    @Column
    private String address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private Set<InterviewSession> interviewSessions;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String name, int age, String address, Set<Role> roles, Set<InterviewSession> interviewSessions) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.roles = roles;
        this.interviewSessions = interviewSessions;
    }
    public User(String username, String name, String email, String password, int age, String address) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.address = address;
    }
}
