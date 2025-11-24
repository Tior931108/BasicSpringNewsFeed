package com.example.basicspringnewsfeed.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {
    private String code;
    private String message;
    private int status;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponse.builder()
                        .code(e.getCode())
                        .message(e.getMessage())
                        .status(e.getHttpStatus().value())
                        .build());
    }
}
