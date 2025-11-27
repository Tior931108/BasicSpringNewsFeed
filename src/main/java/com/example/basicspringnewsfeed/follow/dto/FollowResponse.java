package com.example.basicspringnewsfeed.follow.dto;

import com.example.basicspringnewsfeed.follow.entity.IsFollow;
import lombok.Getter;

@Getter
public class FollowResponse {
    private final Long followId;  // 팔로우 Id
    private final Long followerId;  // 팔로워 Id
    private final Long followingId;  // 팔로잉 Id
    private final IsFollow status;  // 상태 표시


    public FollowResponse(Long followId, Long followerId, Long followingId, IsFollow status) {
        this.followId = followId;
        this.followerId = followerId;
        this.followingId = followingId;
        this.status = status;
    }
}
