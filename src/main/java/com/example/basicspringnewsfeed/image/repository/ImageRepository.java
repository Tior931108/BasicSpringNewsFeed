package com.example.basicspringnewsfeed.image.repository;

import com.example.basicspringnewsfeed.image.entity.Image;
import com.example.basicspringnewsfeed.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByPost(Post post);
}
