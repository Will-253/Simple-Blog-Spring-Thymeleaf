package com.project.simpleblog.service;

import com.project.simpleblog.models.Author;
import com.project.simpleblog.models.Posts;
import com.project.simpleblog.repository.PostsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostsRepository postsRepository;
    private final AuthorService authorService;

    public PostService(PostsRepository postsRepository, AuthorService authorService) {
        this.postsRepository = postsRepository;
        this.authorService = authorService;
    }

    public List<Posts> getAll() {
        return postsRepository.findAll();
    }

    public Optional<Posts> getById(Long id) {
        return postsRepository.findById(id);
    }

    public Posts createNewPost(Posts post) {
        if (post==null) {
            throw new IllegalArgumentException("Post cannot be null");
        }
        if (post.getAuthor()==null || post.getAuthor().getId()==null) {
            throw new IllegalArgumentException("Post must have a valid author");
        }
        Author author=authorService.GetById(post.getAuthor().getId())
                .orElseThrow(()->new IllegalArgumentException("Author with ID"+post.getAuthor().getId()+" not found"));
        post.setAuthor(author);
        if (author.getPosts()==null) {
            author.setPosts(new ArrayList<>());
        }
        author.getPosts().add(post);

        return postsRepository.save(post);
    }

    public void deleteById(Long id) {
        if(!postsRepository.existsById(id)){
            throw new IllegalArgumentException("Post with ID"+id+" not found");
        }
        postsRepository.deleteById(id);
    }
    public Optional<Author> findAuthorById(Long id){
        return authorService.GetById(id);
    }

    public List<Posts> searchByTitle(String query) {
        return postsRepository.findByTitleContainingIgnoreCase(query);
    }
}
