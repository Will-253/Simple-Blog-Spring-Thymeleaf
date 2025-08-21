package com.project.simpleblog.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullname;

    @OneToMany(mappedBy = "visitor")
    private List<Comments> comments;

    public Visitor() {
    }

    public Visitor(Long id, String fullname, List<Comments> comments) {
        this.id = id;
        this.fullname = fullname;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
