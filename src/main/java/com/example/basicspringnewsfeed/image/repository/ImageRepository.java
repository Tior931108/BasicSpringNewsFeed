package com.example.basicspringnewsfeed.image.repository;

import com.example.basicspringnewsfeed.image.entity.Image;
import com.example.basicspringnewsfeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    // 게시글 기준 이미지 조회
    List<Image> findByPost(Post post);
    // 게시글 ID 기준 이미지 조회
    List<Image> findByPost_PostId(Long postId);
}
