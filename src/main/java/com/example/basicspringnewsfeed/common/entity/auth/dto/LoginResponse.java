package com.example.basicspringnewsfeed.common.entity.auth.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        SignupResult signupResult
) {
}
