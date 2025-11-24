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




    // 4. Post




    // 5. Follower
    USER_NOT_FOUND("USER_NOT_FOUND", "존재하지 않는 유저입니다.", HttpStatus.NOT_FOUND);


    // 6. Block




    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
