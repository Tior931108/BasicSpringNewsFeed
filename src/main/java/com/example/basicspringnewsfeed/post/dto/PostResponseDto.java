package com.example.basicspringnewsfeed.post.dto;

import com.example.basicspringnewsfeed.hashtag.entity.Hashtag;
import com.example.basicspringnewsfeed.image.dto.ImageDto;
import com.example.basicspringnewsfeed.image.entity.Image;
import com.example.basicspringnewsfeed.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {

    private final Long postId;
    private final String title;
    private final String content;
    private final List<String> hashtags; //해시태그 - 성주연
    private final Long likedCount;
    private final Long commentCount;
    private final List<ImageDto> images;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    // 생성자
    public PostResponseDto(Post post, List<Image> images,List<Hashtag> hashtags) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.hashtags = hashtags.stream()
                .map(Hashtag::getHashtagName)
                .toList();
        this.likedCount = post.getLikedCount();
        this.commentCount = post.getCommentCount();
        this.images = images.stream()
                .map(ImageDto::new)
                .toList();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}
