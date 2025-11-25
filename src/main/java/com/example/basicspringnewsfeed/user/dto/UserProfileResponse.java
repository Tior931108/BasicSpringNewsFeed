package com.example.basicspringnewsfeed.user.dto;

import java.time.LocalDateTime;

public record UserProfileResponse(
        Long id,
        String username,
        String email,
        String profileImageUrl,
        String bio,
        LocalDateTime createdAt) {}
