package com.project.simpleblog.controllers;

import com.project.simpleblog.models.Comments;
import com.project.simpleblog.models.Posts;
import com.project.simpleblog.service.CommentService;
import com.project.simpleblog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("api/posts")
public class PostsController {

    private final PostService postService;
    private final CommentService commentService;

    public PostsController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping
    public String getAllPosts(Model model) {
        List<Posts> posts=postService.getAll();
        model.addAttribute("posts",posts);
        return "index";
    }

    @GetMapping("/createPost")
    public String CreateNewPost(Model model) {
        Posts post=new Posts();
        model.addAttribute("post",post);
        return "create_post";
    }

    @PostMapping
    public String AddNewPost(@ModelAttribute("post") Posts post) {
        postService.createNewPost(post);
        return "redirect:/api/posts/"+(post.getId());
    }

    @GetMapping("/{id}")
    public String getPostById(@PathVariable Long id, Model model){
        Optional<Posts> optionalPost= postService.getById(id);
        if(optionalPost.isPresent()){
            Posts post=optionalPost.get();
            model.addAttribute("post",post);
            model.addAttribute("comments",new Comments());
            return "post";
        } else {
        return "404";
        }
    }
    @GetMapping("/{id}/delete")
    public String confirmDelete(@PathVariable Long id, Model model) {
        Optional<Posts> optionalPost=postService.getById(id);
        if(optionalPost.isPresent()){
            Posts post=optionalPost.get();
            model.addAttribute("post",post);
            return "delete_post";
        } else {
            return "404";
        }
    }
    @GetMapping("/{id}/remove")
    public String deletePostById(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        postService.deleteById(id);
        redirectAttributes.addFlashAttribute("message","Post Deleted Successfully");
        return "redirect:/api/posts";
    }
}
