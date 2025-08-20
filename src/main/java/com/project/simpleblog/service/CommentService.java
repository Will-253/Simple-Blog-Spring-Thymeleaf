package com.project.simpleblog.service;

import com.project.simpleblog.models.Comments;
import com.project.simpleblog.models.Posts;
import com.project.simpleblog.repository.CommentsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentsRepository commentsRepository;
    private final PostService postService;

    public CommentService(CommentsRepository commentsRepository, PostService postService) {
        this.commentsRepository = commentsRepository;
        this.postService = postService;
    }

    public List<Comments> listForPost(Long id){
        return commentsRepository.findByPostIdOrderByIdAsc( postService.getById(id).get());
    }

    public Comments saveComment(Comments comment) {

           return commentsRepository.save(comment);
    }

}
