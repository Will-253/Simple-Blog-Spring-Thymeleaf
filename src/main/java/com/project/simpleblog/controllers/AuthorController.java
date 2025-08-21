package com.project.simpleblog.controllers;

import com.project.simpleblog.models.Author;
import com.project.simpleblog.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/posts/author")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
//    api for the form for selecting or entering the author name(http://localhost:8080/api/posts/author)
    @GetMapping
    public String createNewAuthor(Model model){
        List<Author> authors=authorService.findAllAuthors();
        model.addAttribute("newAuthor",new Author());
        model.addAttribute("selectAuthor",authors);
        return "create_author";
    }
//api for selecting or adding new
    @PostMapping
    public String saveNewAuthor(@ModelAttribute("author") Author author){
        authorService.addAuthor(author);
        return "redirect:/api/posts/createPost";
    }

}
