package com.example.basicspringnewsfeed.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;


    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());  // RuntimeException 메시지 설정(로그에 남음)
        this.errorCode = errorCode;
    }

    // 커스텀 메시지 받는 생성자 추가 : 파일 업로드 실패의 다양한 원인 파악을 위함
    public CustomException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
    }
}
