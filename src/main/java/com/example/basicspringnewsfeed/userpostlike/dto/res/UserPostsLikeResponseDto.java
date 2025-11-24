package com.example.basicspringnewsfeed.userpostlike.dto.res;

public record UserPostsLikeResponseDto(
        boolean liked,
        Long likeCount,
        String message
) {
}
