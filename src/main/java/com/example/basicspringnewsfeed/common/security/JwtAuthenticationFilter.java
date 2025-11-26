package com.example.basicspringnewsfeed.common.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 2025-11-24 : OncePerRequestFilter : 1회 요청 당 한 번 실행 보장.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 1). request header : Authorization
        String header=request.getHeader(AUTHORIZATION_HEADER);

        // 2).header!=null || header 접두사
        if(header==null||!header.startsWith(AUTHORIZATION_HEADER_PREFIX)){
            filterChain.doFilter(request, response);
            return;
        }
        String token=header.substring(AUTHORIZATION_HEADER_PREFIX.length());

        // 3). token 검증 : 만료, 위조, 형식
        jwtTokenProvider.validateTokenOrThrow(token);

        // 4). parseClaims(token) : parsing 거친 후, JWT payload(claims)에서 claim 꺼내기.
        Long userId=jwtTokenProvider.getUserId(token);

        // 5). DB에서 id로 조회. Spring Security에 맞는 abstract type 사용.
        UserDetails userDetails=customUserDetailsService.loadUserById(userId);

        UsernamePasswordAuthenticationToken authentication
                =new UsernamePasswordAuthenticationToken(
                        userDetails, // principal : 로그인 주체.
                null, // credentials : PW. 이미 인증 상태이므로 제거.
                userDetails.getAuthorities() // Collections.emptyList(); : Role 미구현
        );

        // 6). 요청 정보를 details에 채우기. : auditing log, 어디서 로그인 했는지, 보안 정책 등.
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        filterChain.doFilter(request, response);
    }

    // 1. shouldNotFilter : /api/auth/** ~ Skip
    // 1). JwtAuthenticationFilter 자체를 건너 뛸 것인지 결정.
    // 2). true : 해당 요청은 JWT 파싱, 검증을 아예 하지 않는다. 따라서 SecurityContext도 건드리지 않으며, 다음 필터로 보낸다.
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        String path=request.getRequestURI();
        return path.startsWith("/h2-console") // DB
                || path.startsWith("/actuator/health"); // Health Checking용 Endpoint
    }
}
