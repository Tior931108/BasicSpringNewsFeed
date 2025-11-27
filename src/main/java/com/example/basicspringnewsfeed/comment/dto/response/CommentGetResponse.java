package com.example.basicspringnewsfeed.comment.dto.response;

import com.example.basicspringnewsfeed.comment.entity.Comment;
import com.example.basicspringnewsfeed.post.entity.Post;
import com.example.basicspringnewsfeed.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentGetResponse {
    private final Long commentId;
    private final Long userId;
    private final String nickName;
    private final String content;
    private final LocalDateTime createdAt;

    public CommentGetResponse(Long commentId, Long userId,String nickName ,String content, LocalDateTime createdAt) {
        this.commentId = commentId;
        this. userId = userId;
        this.nickName = nickName;
        this.content = content;
        this.createdAt = createdAt;
    }

    // 11.26 성주연 - from 메서드 추가
    public static CommentGetResponse from(Comment comment) {
        return new CommentGetResponse(
                comment.getCommentId(),
                comment.getUser().getUserId(),
                comment.getUser().getNickname(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}
