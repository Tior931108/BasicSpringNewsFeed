package com.example.basicspringnewsfeed.hashtag.dto;

import com.example.basicspringnewsfeed.hashtag.entity.Hashtag;

//인기해시태그조회api(필요하면사용)
public record HashtagDto(
        Long hashtagId,
        String hashtagName,
        Long hashtagCount
) {
    public static HashtagDto from(Hashtag hashtag) {
        return new HashtagDto(
                hashtag.getHashtagId(),
                hashtag.getHashtagName(),
                hashtag.getHashtagCount()
        );
    }
}
