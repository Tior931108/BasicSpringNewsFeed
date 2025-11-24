package com.example.basicspringnewsfeed.hashtag.repository;

import com.example.basicspringnewsfeed.hashtag.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
}
