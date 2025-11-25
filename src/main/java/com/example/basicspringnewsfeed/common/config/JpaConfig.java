package com.example.basicspringnewsfeed.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// 2025-11-24 : 있어야 돌아갑니다. 없으면 createdAt updatedAt NULL 처리.
@Configuration
@EnableJpaAuditing
public class JpaConfig {}
