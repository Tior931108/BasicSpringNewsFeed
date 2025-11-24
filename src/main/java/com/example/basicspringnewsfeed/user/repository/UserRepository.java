package com.example.basicspringnewsfeed.user.repository;

import com.example.basicspringnewsfeed.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>  {
    Optional<User> findByEmail(String email); // 이메일로 찾기

    boolean existsByEmail(String email); // 조건을 만족하는 데이터 찾을 경우 종료.

    boolean existsByEmailAndIdNot(String email, Long id); // 같은 값이면 no-op

}
