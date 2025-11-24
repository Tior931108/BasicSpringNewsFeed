package com.example.basicspringnewsfeed.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;


    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());  // RuntimeException 메시지 설정(로그에 남음)
        this.errorCode = errorCode;
    }
}
