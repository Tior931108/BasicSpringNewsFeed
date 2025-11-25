package com.example.basicspringnewsfeed.follow.dto;

import lombok.Getter;

@Getter
public class FollowingInfo {
    private final Long followingId;
    // nickname이나 다른 정보를 추가할지?

    public FollowingInfo(Long followingId) {
        this.followingId = followingId;
    }
}
