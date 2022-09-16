package com.techvify.interview.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "title")
@SQLDelete(sql = "UPDATE title SET deleted_at = TRUE WHERE id = ?")
@Where(clause = "deleted_at = false")
@Getter
@Setter
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "deleted_at")
    private boolean deletedAt = Boolean.FALSE;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "title_id")
    private List<User> userList;

    public Title() {
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
