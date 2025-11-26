package com.example.basicspringnewsfeed.block.repository;

import com.example.basicspringnewsfeed.block.entity.Block;
import com.example.basicspringnewsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlockRepository extends JpaRepository<Block, Long> {
    // 이미 차단한 유저인지 확인하기
    boolean existsByUserAndBlockedUser(User user, User blockedUser);

    List<Block> findByUser(User user);  // 차단한 유저 목록
}
