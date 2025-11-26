package com.example.basicspringnewsfeed.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor

public enum ErrorCode {
    // Code, message, status 순서대로 설정
    // 0. JWT
    JWT_TOKEN_EXPIRED("JWT_TOKEN_EXPIRED", "토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    JWT_TOKEN_INVALID("JWT_TOKEN_INVALID", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    AUTH_INVALID_CREDENTIAL("AUTH_INVALID_CREDENTIAL", "이메일 혹은 비밀번호가 다릅니다.", HttpStatus.UNAUTHORIZED),
    AUTH_EMAIL_NOT_FOUND("AUTH_EMAIL_NOT_FOUND", "이메일이 다릅니다.", HttpStatus.NOT_FOUND),


    // 1. Story




    // 2. Comment




    // 3. User
    USER_NOT_FOUND("USER_NOT_FOUND", "존재하지 않는 유저입니다.", HttpStatus.NOT_FOUND),
    ONLY_OWNER_UPDATE("ONLY_OWNER_USER_UPDATE", "본인 정보만 수정할 수 있습니다.", HttpStatus.FORBIDDEN),
    ONLY_OWNER_DELETE("ONLY_OWNER_USER_DELETE", "본인 정보만 삭제할 수 있습니다.", HttpStatus.FORBIDDEN),



    // 4. Post & Image
    POST_NOT_FOUND("POST_NOT_FOUND", "게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    IMAGE_NOT_FOUND("IMAGE_NOT_FOUND", "이미지가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    ONLY_IMAGE_FILE("ONLY_IMAGE_FILE", "이미지 파일형식만 가능합니다.", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    PAYLOAD_TOO_LARGE("PAYLOAD_TOO_LARGE", "파일 크기는 5MB까지만 가능합니다.", HttpStatus.PAYLOAD_TOO_LARGE),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "파일 업로드에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR),


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
