package com.example.basicspringnewsfeed.common.entity.config;


import com.example.basicspringnewsfeed.common.entity.security.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



// 2025-11-24 : SecurityContext Setting
@Configuration
@EnableWebSecurity // 2025-11-24 : Enable Spring Security filterChain
@EnableMethodSecurity(prePostEnabled=true) // 2025-11-24 : @PreAuthorize, @PostAuthorize와 같은 Authorization.
@RequiredArgsConstructor
public class SecurityConfig{

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private static final String[] PUBLIC_ENDPOINTS={
            "/api/auth/**",
            "/h2-console/**",
            "/actuator/health"
    };

    // JwtAuthenticationFilter를 Bean에 등록 -> filterChain에 주입.
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter(jwtTokenProvider, customUserDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception{
        http
                .csrf(csrf -> csrf.disable()) // 현 프로젝트는 REST API라 보므로, 쿠키 세션 X, JWT 사용 : 비활성화.
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Security 예외 처리.
                .exceptionHandling(ex->ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                )
                // Public endpoints / authenticated()
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}
