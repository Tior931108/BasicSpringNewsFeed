package com.example.basicspringnewsfeed.userpostlike.entity;

import com.example.basicspringnewsfeed.common.entity.BaseEntity;
import com.example.basicspringnewsfeed.post.entity.Post;
import com.example.basicspringnewsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user_post_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userPostLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public UserPostLike(User user, Post post) {
        this.user = user;
        this.post = post;
    }
}
