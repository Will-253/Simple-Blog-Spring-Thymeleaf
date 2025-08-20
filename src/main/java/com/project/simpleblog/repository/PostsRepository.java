package com.project.simpleblog.repository;

import com.project.simpleblog.models.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {

}
