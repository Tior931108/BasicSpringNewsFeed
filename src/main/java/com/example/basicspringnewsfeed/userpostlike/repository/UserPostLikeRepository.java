package com.example.basicspringnewsfeed.userpostlike.repository;

import com.example.basicspringnewsfeed.post.entity.Post;
import com.example.basicspringnewsfeed.user.entity.User;
import com.example.basicspringnewsfeed.userpostlike.entity.UserPostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPostLikeRepository extends JpaRepository<UserPostLike, Long> {

    Optional<UserPostLike> findByUserAndPost(User user, Post post);

    boolean existsByUserAndPost(User user, Post post);
}


