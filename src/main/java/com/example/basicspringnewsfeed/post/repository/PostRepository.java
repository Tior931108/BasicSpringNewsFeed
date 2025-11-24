package com.example.basicspringnewsfeed.post.repository;

import com.example.basicspringnewsfeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
