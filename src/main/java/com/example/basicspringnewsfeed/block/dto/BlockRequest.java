package com.example.basicspringnewsfeed.block.dto;

import lombok.Getter;

@Getter
public class BlockRequest {
    private Long userId;
    private Long blockedUserId;
}
