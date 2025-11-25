package com.example.basicspringnewsfeed.comment.repository;

import com.example.basicspringnewsfeed.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
