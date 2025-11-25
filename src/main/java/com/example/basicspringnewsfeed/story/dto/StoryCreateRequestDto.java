package com.example.basicspringnewsfeed.story.dto;

import lombok.Getter;

@Getter
public class StoryCreateRequestDto {
    private Long userId;
    private String content;
    private String storyImageUrl;
}
