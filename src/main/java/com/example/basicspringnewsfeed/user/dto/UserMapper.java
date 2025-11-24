package com.example.basicspringnewsfeed.user.dto;

import com.example.basicspringnewsfeed.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserProfileResponse toUserProfile(User user){ // 출력 값
        return new UserProfileResponse(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getProfileImageUrl(),
                user.getIntroduce(),
                user.getCreatedAt()
        );
    }
}
