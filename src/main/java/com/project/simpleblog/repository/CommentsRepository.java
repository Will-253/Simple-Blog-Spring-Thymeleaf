package com.project.simpleblog.repository;

import com.project.simpleblog.models.Comments;
import com.project.simpleblog.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

    List<Comments> findByPostIdOrderByIdAsc(Posts postId);
}
