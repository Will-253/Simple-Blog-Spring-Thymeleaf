package com.project.simpleblog.controllers;

import com.project.simpleblog.models.Comments;
import com.project.simpleblog.models.Posts;
import com.project.simpleblog.models.Visitor;
import com.project.simpleblog.service.CommentService;
import com.project.simpleblog.service.PostService;
import com.project.simpleblog.service.VisitorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("api/posts")
public class CommentsController {

    private final CommentService commentService;
    private final PostService postService;
    private final VisitorService visitorService;

    public CommentsController(CommentService commentService, PostService postService,VisitorService visitorService) {
        this.commentService = commentService;
        this.postService = postService;
        this.visitorService=visitorService;
    }
//  api for creating a new comment(http://localhost:8080/api/posts/{id}/comments)
    @PostMapping("/{id}/comments")
    public String addCommentToPost(@PathVariable Long id, @ModelAttribute("comment") @Valid Comments comment,
                                   BindingResult result, @RequestParam("visitorName") String visitorName, Model model) {

        Optional<Posts> optionalPost = postService.getById(id);
        if (optionalPost.isEmpty()) {
            model.addAttribute("errorMessage",
                    "Post with ID " + id + " not found");
            return "404";
        }
        Posts post = optionalPost.get();

//        Validate visitor name
        if (visitorName == null || visitorName.trim().isEmpty()) {
            model.addAttribute("visitorError", "Visitor name is required");
            model.addAttribute("post", post);
            model.addAttribute("comments", comment);
            return "post";
        }
//        Validate comment content
        if (result.hasErrors()) {
            model.addAttribute("post", post);
            model.addAttribute("comments", comment);
            return "post";
        }
//        Find or create a visitor
        Visitor visitor = visitorService.findByFullname(visitorName)
                .orElseGet(() -> {
                    Visitor newVisitor = new Visitor();
                    newVisitor.setFullname(visitorName);
                    return visitorService.saveVisitor(newVisitor);
                });

//        Set Comment properties
        comment.setId(null);
        comment.setPost(post);
        comment.setVisitor(visitor);
        commentService.saveComment(comment);

        return "redirect:/api/posts/" + post.getId();

    }
}
