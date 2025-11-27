package com.example.basicspringnewsfeed.common.security;

public record CurrentUser(Long id, String email, String username) {

    // 2025-11-24 : 정적 팩토리 메서드
    public static CurrentUser from(CustomUserDetails principal){
        return new CurrentUser(
                principal.getId(),
                principal.getEmail(),
                principal.getNickname()
        );
    }
}
