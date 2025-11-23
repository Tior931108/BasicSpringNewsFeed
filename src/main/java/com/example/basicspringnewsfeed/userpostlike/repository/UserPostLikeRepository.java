package com.example.basicspringnewsfeed.userpostlike.repository;

import com.example.basicspringnewsfeed.userpostlike.entity.UserPostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPostLikeRepository extends JpaRepository<UserPostLike, Long> {
}
