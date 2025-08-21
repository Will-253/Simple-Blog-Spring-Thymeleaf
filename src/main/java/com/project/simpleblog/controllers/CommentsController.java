package com.project.simpleblog.controllers;

import com.project.simpleblog.models.Comments;
import com.project.simpleblog.models.Posts;
import com.project.simpleblog.service.CommentService;
import com.project.simpleblog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("api/posts")
public class CommentsController {

    private final CommentService commentService;
    private final PostService postService;

    public CommentsController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }
//  api for creating a new comment(http://localhost:8080/api/posts/{id}/comments)
    @PostMapping("/{id}/comments")
    public String addCommentToPost(@PathVariable Long id, @ModelAttribute("comment") Comments comment){
        Optional<Posts> optionalPost=postService.getById(id);
        if(optionalPost.isPresent()){
            Posts post=optionalPost.get();
            comment.setId(null);
            comment.setPost(post);
            commentService.saveComment(comment);
            return "redirect:/api/posts/"+(post.getId());
        }
        else {
            return "404";
        }
    }

}
