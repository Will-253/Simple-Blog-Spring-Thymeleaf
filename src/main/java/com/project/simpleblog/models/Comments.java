package com.project.simpleblog.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Comment content is required")
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts post;

    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private Visitor visitor;

    public Comments() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }
}
