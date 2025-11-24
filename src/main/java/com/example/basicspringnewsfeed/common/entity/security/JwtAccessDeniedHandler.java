package com.example.basicspringnewsfeed.common.entity.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 2025-11-24 : ErrorResponse, ErrorCode, GlobalExceptionHandler 필요. : 현재는 ServletResponse로 403 출력.
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler{

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write("unauthorized");
    }
}
