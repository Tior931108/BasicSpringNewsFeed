package com.example.basicspringnewsfeed.hashtagandpost.repository;

import com.example.basicspringnewsfeed.hashtagandpost.entity.HashtagPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagPostRepository extends JpaRepository<HashtagPost, Long> {
}
