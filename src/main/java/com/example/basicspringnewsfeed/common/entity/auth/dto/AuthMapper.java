package com.example.basicspringnewsfeed.common.entity.auth.dto;

import com.example.basicspringnewsfeed.user.entity.User;
import org.springframework.stereotype.Component;


@Component
public class AuthMapper {

    public User ofSignUp(String encodedPassword, SignupRequest request) {// username, email, pw
        return User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(encodedPassword)
                .build();
    }

    public UserResponse toUserResponse(User user) { // 출력 값
        return new UserResponse(
                user.getUserId(),
                user.getNickname(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }
}