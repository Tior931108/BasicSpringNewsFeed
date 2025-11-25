package com.example.basicspringnewsfeed.follow.repository;

import com.example.basicspringnewsfeed.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
