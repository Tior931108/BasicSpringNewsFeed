package com.example.basicspringnewsfeed.hashtagandpost.repository;

import com.example.basicspringnewsfeed.hashtag.entity.Hashtag;
import com.example.basicspringnewsfeed.hashtagandpost.entity.HashtagPost;
import com.example.basicspringnewsfeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HashtagPostRepository extends JpaRepository<HashtagPost, Long> {
    List<HashtagPost> findByPost(Post post);

    void deleteByPost(Post post);

    @Query("SELECT DISTINCT hp.hashtag FROM HashtagPost hp WHERE hp.post = :post")
    List<Hashtag> findHashtagsByPost(@Param("post") Post post);
}
