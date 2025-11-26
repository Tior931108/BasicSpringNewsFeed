package com.example.basicspringnewsfeed.follow.repository;

import com.example.basicspringnewsfeed.follow.entity.Follow;
import com.example.basicspringnewsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    // 중복 팔로우 확인하기(팔로잉 방지)
    boolean existsByFollowerIdAndFollowingId(User followerId, User followingId);

    List<Follow> findByFollowerId(User followerId);  // 나를 팔로우하는 유저(팔로워 목록 조회)
    List<Follow> findByFollowingId(User followingId);  // 내가 팔로우하는 유저(팔로잉 목록 조회)
}
