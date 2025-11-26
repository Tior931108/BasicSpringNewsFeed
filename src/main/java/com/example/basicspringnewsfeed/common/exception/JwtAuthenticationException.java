package com.example.basicspringnewsfeed.common.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

// message + cause 버전, message - only 버전
@Getter
public class JwtAuthenticationException extends AuthenticationException {

    private final ErrorCode errorCode;

    public JwtAuthenticationException(ErrorCode errorCode, Throwable cause){
        super(errorCode.getMessage(), cause);
        this.errorCode=errorCode;
    }


}
