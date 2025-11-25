package com.example.basicspringnewsfeed.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor

public enum ErrorCode {
    // Code, message, status 순서대로 설정

    // 1. Story




    // 2. Comment




    // 3. User
    USER_NOT_FOUND("USER_NOT_FOUND", "존재하지 않는 유저입니다.", HttpStatus.NOT_FOUND),



    // 4. Post
    POST_NOT_FOUND("POST_NOT_FOUND", "게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND),



    // 5. Follower
    FOLLOWER_NOT_FOUND("FOLLOWER_NOT_FOUND", "존재하지 않는 팔로우입니다.", HttpStatus.NOT_FOUND),
    FOLLOWING_NOT_FOUND("FOLLOWING_NOT_FOUND", "존재하지 않는 팔로잉입니다.", HttpStatus.NOT_FOUND),
    FOLLOW_ALREADY_EXIST("FOLLOW_ALREADY_EXIST", "이미 팔로우요청을 한 상태입니다.", HttpStatus.CONFLICT),
    FOLLOW_NOT_FOUND("FOLLOW_NOT_FOUND", "팔로우 요청을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),


    // 6. Block


    // Like
    SELF_LIKE_FORBIDDEN("SELF_LIKE_FORBIDDEN", "본인의 게시글에는 좋아요를 누를 수 없습니다.", HttpStatus.BAD_REQUEST),



    ;
    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
