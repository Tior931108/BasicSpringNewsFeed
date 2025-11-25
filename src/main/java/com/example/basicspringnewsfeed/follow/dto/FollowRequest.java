package com.example.basicspringnewsfeed.follow.dto;

import lombok.Getter;

@Getter
public class FollowRequest {
    private Long followerId;
    private Long followingId;
}
