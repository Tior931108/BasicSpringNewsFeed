package com.example.basicspringnewsfeed.common.security;

import com.example.basicspringnewsfeed.common.exception.ErrorCode;
import com.example.basicspringnewsfeed.common.exception.ErrorResponse;
import com.example.basicspringnewsfeed.common.exception.JwtAuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/*SecurityConfig.java 내용 중
.exceptionHandling(ex->ex
.authenticationEntryPoint(jwtAuthenticationEntryPoint)) < 401
상속 걸어서 재정의
*/
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // JSON 직렬화 MapperClass.
    private final ObjectMapper objectMapper;

    // Security가 인증 실패를 감지했을 때
    /* 예시
    1. JWT FILTER : JwtAuthenticationException -> Security -> commence
    2. SecurityConfig으로 보호한 URL : .authorizeHttpRequests(auth->auth
    .requestMatchers("/api/schedules/**").hasRole("USER"));
    3. 기타 : AuthenticationException(BadCredentials 등)
    */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException {
        ErrorCode errorCode;

        if(authenticationException instanceof JwtAuthenticationException jwtAuthenticationException){
            errorCode=jwtAuthenticationException.getErrorCode(); // JWT_EXPIRED - JWT_INVALID
        } else {
            errorCode= ErrorCode.JWT_TOKEN_EXPIRED; // 401
        }

        // code, message, path -> JSON으로 변환할 DTO 완성.
        ErrorResponse body = ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getHttpStatus().value())
                .build();

        response.setStatus(errorCode.getHttpStatus().value()); // errorCode 상태.
        response.setContentType("application/json"); // Jackson 형태로
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), body); // body 객체를 JSON으로 직렬화 -> HTTP 응답 body로 사용.


    }
}
