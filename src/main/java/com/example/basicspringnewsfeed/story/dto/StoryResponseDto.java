package com.example.basicspringnewsfeed.story.dto;


import com.example.basicspringnewsfeed.story.entity.Story;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StoryResponseDto {
    private Long storyId;
    private String content;
    private String storyImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private Long userId; //유저 아이디
    private String nickname; // 닉네임(=유저 이름)


public StoryResponseDto(Story story){
    this.storyId = story.getStoryId();
    this.content = story.getContent();
    this.storyImageUrl = story.getStoryImageUrl();
    this.createdAt = story.getCreatedAt();
    this.updateAt = story.getUpdatedAt();
    this.userId = story.getUser().getUserId();
    this.nickname = story.getUser().getNickname();
}
}


