package com.example.basicspringnewsfeed.follow.dto;

import lombok.Getter;

@Getter
public class FollowerInfo {
    private final Long followerId;
    // nickname이나 다른 정보를 추가할지?

    public FollowerInfo(Long followerId) {
        this.followerId = followerId;
    }
}
