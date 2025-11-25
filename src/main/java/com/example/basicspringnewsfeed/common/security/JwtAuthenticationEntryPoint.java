package com.example.basicspringnewsfeed.common.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 2025-11-24 : ErrorResponse, ErrorCode, GlobalExceptionHandler 필요. : 현재는 ServletResponse로 401 출력.
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException {


        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.setContentType("text/plain;charset=UTF-8"); // encode 한국어
        response.getWriter().write("unauthorized");
    }
}
