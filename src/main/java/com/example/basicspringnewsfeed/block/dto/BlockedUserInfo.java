package com.example.basicspringnewsfeed.block.dto;

import lombok.Getter;

@Getter
public class BlockedUserInfo {
    private final Long blockedUserId;
    // 다른 정보도 추가할지?

    public BlockedUserInfo(Long blockedUserId) {
        this.blockedUserId = blockedUserId;
    }
}
