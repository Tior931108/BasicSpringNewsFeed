package com.example.basicspringnewsfeed.common.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    // 2025-11-23 : token 만료일 표시로 인해 꼬일 염려가 있어서 제거.
    @CreatedDate
    @Column(name="created_at", nullable=false, updatable=false)
    protected LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name="updated_at", nullable=false)
    protected LocalDateTime updatedAt;
}