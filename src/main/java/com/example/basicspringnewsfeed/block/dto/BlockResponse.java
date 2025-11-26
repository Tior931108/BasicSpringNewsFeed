package com.example.basicspringnewsfeed.block.dto;

import lombok.Getter;

@Getter
public class BlockResponse {
    private final Long BlockId;
    private final Long UserId;
    private final Long BlockedUserId;

    public BlockResponse(Long blockId, Long userId, Long blockedUserId) {
        BlockId = blockId;
        UserId = userId;
        BlockedUserId = blockedUserId;
    }
}
