package com.project.simpleblog.repository;

import com.project.simpleblog.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    List<Posts> findByTitleContainingIgnoreCase(String query);
}
