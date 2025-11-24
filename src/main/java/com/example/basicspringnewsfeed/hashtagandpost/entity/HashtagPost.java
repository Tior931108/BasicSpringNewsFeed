package com.example.basicspringnewsfeed.hashtagandpost.entity;

import com.example.basicspringnewsfeed.hashtag.entity.Hashtag;
import com.example.basicspringnewsfeed.post.entity.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "hashtags_posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HashtagPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagPostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public HashtagPost(Hashtag hashtag, Post post) {
        this.hashtag = hashtag;
        this.post = post;
    }
}
