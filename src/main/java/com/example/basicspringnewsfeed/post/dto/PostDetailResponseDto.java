package com.example.basicspringnewsfeed.post.dto;

import com.example.basicspringnewsfeed.comment.dto.response.CommentGetResponse;
import com.example.basicspringnewsfeed.comment.entity.Comment;
import com.example.basicspringnewsfeed.common.entity.IsDelete;
import com.example.basicspringnewsfeed.hashtag.entity.Hashtag;
import com.example.basicspringnewsfeed.image.dto.ImageDto;
import com.example.basicspringnewsfeed.image.entity.Image;
import com.example.basicspringnewsfeed.post.entity.Post;

import java.time.LocalDateTime;
import java.util.List;

public record PostDetailResponseDto(
        Long postId,
        String title,
        String content,
        String userName,
        Long userId,
        Long likedCount,
        Long commentCount,
        List<String> hashtags,
        List<ImageDto> images,
        List<CommentGetResponse> comments,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostDetailResponseDto from(Post post, List<Hashtag> hashtags, List<Image> images, List<Comment> comments) {
        List<String> hashtagNames = hashtags.stream()
                .map(Hashtag::getHashtagName)
                .toList();

        List<ImageDto> imageDtos = images.stream()
                .map(ImageDto::new)
                .toList();

        List<CommentGetResponse> commentDtos = comments.stream()
                .filter(c -> c.getIsDelete().equals(IsDelete.N))
                .map(CommentGetResponse::from)
                .toList();

        return new PostDetailResponseDto(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getNickname(),
                post.getUser().getUserId(),
                post.getLikedCount(),
                post.getCommentCount(),
                hashtagNames,
                imageDtos,
                commentDtos,
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}