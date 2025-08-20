package com.project.simpleblog.service;

import com.project.simpleblog.models.Posts;
import com.project.simpleblog.repository.PostsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostsRepository postsRepository;

    public PostService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    public List<Posts> getAll() {
        return postsRepository.findAll();
    }

    public Optional<Posts> getById(Long id) {
        return postsRepository.findById(id);
    }

    public Posts createNewPost(Posts post) {
        return postsRepository.save(post);
    }

    public void deleteById(Long id) {
        postsRepository.deleteById(id);
    }
}
