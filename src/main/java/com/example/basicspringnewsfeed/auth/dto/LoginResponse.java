package com.example.basicspringnewsfeed.auth.dto;

public record LoginResponse(
        String accessToken,
        UserResponse user
) {
}
