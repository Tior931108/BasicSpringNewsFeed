package com.example.basicspringnewsfeed.comment.dto.response;

import com.example.basicspringnewsfeed.post.entity.Post;
import com.example.basicspringnewsfeed.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentCreateResponse {
    private final Long commentId;
    private final Long userId;
    private final String nickName;
    private final String content;
    private final LocalDateTime createdAt;

    public CommentCreateResponse(Long commentId, Long userId, String nickName, String content, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.userId = userId;
        this.nickName = nickName;
        this.content = content;
        this.createdAt = createdAt;
    }
}
