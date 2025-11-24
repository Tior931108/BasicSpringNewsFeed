package com.example.basicspringnewsfeed.comment.dto.response;

import com.example.basicspringnewsfeed.post.entity.Post;
import com.example.basicspringnewsfeed.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentUpdateResponse {
    private final Long commentId;
    private final User user;
    private final Post post;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentUpdateResponse(Long commentId, User user, Post post, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.user = user;
        this.post = post;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
