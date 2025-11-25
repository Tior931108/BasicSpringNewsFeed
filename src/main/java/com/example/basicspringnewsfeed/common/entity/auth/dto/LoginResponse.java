package com.example.basicspringnewsfeed.common.entity.auth.dto;

import java.time.LocalDateTime;

public record LoginResponse(
        String accessToken,
        UserResponse user
) {
}
