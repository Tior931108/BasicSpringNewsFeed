package com.example.basicspringnewsfeed.common.exception;

import java.io.IOException;

public class FileUploadFailException extends CustomException{

    // 기본 생성자
    public FileUploadFailException(ErrorCode errorCode) {
        super(errorCode);
    }

    // IOexcepion을 받는 생성자 추가 _ 파일 업로드 실패시 정확한 이유를 알기 위함.
    public FileUploadFailException(ErrorCode errorCode, IOException e) {
        super(errorCode);
    }
}
