package com.project.simpleblog.controllers;

import com.project.simpleblog.models.Author;
import com.project.simpleblog.models.Comments;
import com.project.simpleblog.models.Posts;
import com.project.simpleblog.service.AuthorService;
import com.project.simpleblog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("api/posts")
public class PostsController {

    private final PostService postService;
    private final AuthorService authorService;

    public PostsController(PostService postService, AuthorService authorService) {
        this.postService = postService;
        this.authorService = authorService;

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
    public String CreateNewPost(Model model,@RequestParam(value = "authorId", required = false) Long authorId) {
        Posts post=new Posts();
        if (authorId!=null) {
            Author author=authorService.GetById(authorId).orElseThrow(()->new IllegalArgumentException("Author with ID"+authorId+" not found"));
            post.setAuthor(author);
        }
        model.addAttribute("post",post);
        model.addAttribute("selectAuthor",authorService.findAllAuthors());
        model.addAttribute("authorType","existing");
        return "create_post";
    }

//    api for creating a new post(http://localhost:8080/api/posts)
    @PostMapping
    public String AddNewPost(@ModelAttribute("post") Posts post, BindingResult result,
                             @RequestParam(value = "authorType",required = false) String authorType,
                             @RequestParam(value = "newAuthorName",required = false) String newAuthorName, Model model) {
//        Validate author selection or new author name
        if ("new".equals(authorType)) {
            if (newAuthorName==null || newAuthorName.trim().isEmpty()) {
                model.addAttribute("newAuthorError","New author name is required");
                model.addAttribute("selectAuthor",authorService.findAllAuthors());
                model.addAttribute("authorType","new");
                return "create_post";
            }
            Author newAuthor=new Author();
            newAuthor.setFullName(newAuthorName);
            Author savedAuthor=authorService.addAuthor(newAuthor);
            post.setAuthor(savedAuthor);
        } else{
            if(post.getAuthor()==null || post.getAuthor().getId()==null){
                model.addAttribute("newAuthorError","Please select an author");
                model.addAttribute("selectAuthor",authorService.findAllAuthors());
                model.addAttribute("authorType","existing");
                return "create_post";
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("selectAuthor",authorService.findAllAuthors());
            model.addAttribute("authorType",  authorType);
            return "create_post";
        }
        Posts savedPost=postService.createNewPost(post);
        return "redirect:/api/posts/"+savedPost.getId() ;

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
            model.addAttribute("errorMessage",
                    "Post with ID " + id + " not found");
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
            model.addAttribute("errorMessage",
                    "Post with ID " + id + " not found");
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

    @GetMapping("/search")
    public String searchPosts(@RequestParam("query") String query, Model model) {
        List<Posts> posts = postService.searchByTitle(query); // Implement in PostService
        model.addAttribute("posts", posts);
        return "index";
    }
}
