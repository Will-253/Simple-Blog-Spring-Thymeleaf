package com.project.simpleblog.controllers;

import com.project.simpleblog.models.Author;
import com.project.simpleblog.models.Comments;
import com.project.simpleblog.models.Posts;
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

    public PostsController(PostService postService) {
        this.postService = postService;

    }

//    api for viewing all posts(http://localhost:8080/api/posts)
    @GetMapping
    public String getAllPosts(Model model) {
        List<Posts> posts=postService.getAll();
        model.addAttribute("posts",posts);
        return "index";
    }

//    api for creating a new post form(http://localhost:8080/api/posts/createPost)
    @GetMapping("/createPost")
    public String CreateNewPost(Model model) {
        model.addAttribute("post",new Posts());
        model.addAttribute("author",new Author());
        return "create_post";
    }

//    api for creating a new post(http://localhost:8080/api/posts)
    @PostMapping
    public String AddNewPost(@ModelAttribute("post") Posts post) {
        return "redirect:/api/posts/"+(post.getId());
    }

//    view a single post and comment form(http://localhost:8080/api/posts/{id})
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
//    api for a delete and cancel form(http://localhost:8080/api/posts/{id}/delete)
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
    //    api for a delete(http://localhost:8080/api/posts/{id}/remove)
    @GetMapping("/{id}/remove")
    public String deletePostById(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        postService.deleteById(id);
        redirectAttributes.addFlashAttribute("message","Post Deleted Successfully");
        return "redirect:/api/posts";
    }
}
