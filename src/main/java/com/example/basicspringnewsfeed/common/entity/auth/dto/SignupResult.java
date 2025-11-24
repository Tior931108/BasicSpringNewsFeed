package com.example.basicspringnewsfeed.common.entity.auth.dto;

import java.time.LocalDateTime;

public record SignupResult(
        Long id,
        String username,
        String email,
        LocalDateTime createdAt) {}
