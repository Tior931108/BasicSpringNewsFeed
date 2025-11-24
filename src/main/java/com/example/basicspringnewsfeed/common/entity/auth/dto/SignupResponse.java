package com.example.basicspringnewsfeed.common.entity.auth.dto;

public record SignupResponse(
        String accessToken,
        String refreshToken,
        SignupResult signupResult) {
}
