package com.example.basicspringnewsfeed.post.entity;

import com.example.basicspringnewsfeed.common.entity.BaseEntity;
import com.example.basicspringnewsfeed.common.entity.IsDelete;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 400, nullable = false)
    private String content;
    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private Integer likedCount;
    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private Integer commentCount;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'N'")
    private IsDelete isDelete;

}
