package com.example.basicspringnewsfeed.comment.dto.request;

import com.example.basicspringnewsfeed.post.entity.Post;
import com.example.basicspringnewsfeed.user.entity.User;
import lombok.Getter;

@Getter
public class CommentCreateRequest {
    private String content;
}
