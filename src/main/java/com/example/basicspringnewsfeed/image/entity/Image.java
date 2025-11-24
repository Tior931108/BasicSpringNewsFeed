package com.example.basicspringnewsfeed.image.entity;

import com.example.basicspringnewsfeed.post.entity.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "images")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(length = 100, nullable = false)
    private String fileName;

    @Column(length = 255, nullable = false)
    private String imageUrl;

    public Image(Post post, String fileName, String imageUrl) {
        this.post = post;
        this.fileName = fileName;
        this.imageUrl = imageUrl;
    }
}
