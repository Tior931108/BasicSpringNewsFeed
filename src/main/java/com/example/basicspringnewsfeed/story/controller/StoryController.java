package com.example.basicspringnewsfeed.story.controller;

import com.example.basicspringnewsfeed.story.dto.StoryCreateRequestDto;
import com.example.basicspringnewsfeed.story.dto.StoryResponseDto;
import com.example.basicspringnewsfeed.story.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stories")
@RequiredArgsConstructor
public class StoryController {
    private final StoryService storyService;

    // 1. 스토리 작성
    @PostMapping("")
    public ResponseEntity<StoryResponseDto> createStory(@RequestBody StoryCreateRequestDto requestDto){
        return ResponseEntity.ok(storyService.createStory(requestDto));
    }

    // 2. 스토리 단건 조회
    @GetMapping("/{storyId}")
    public ResponseEntity<StoryResponseDto> getStory(@PathVariable Long storyId){
        return ResponseEntity.ok(storyService.getStory(storyId));
    }

    // 3. 전체 스토리 목록 조회
    @GetMapping("")
    public ResponseEntity<List<StoryResponseDto>> getAllStories(){
        return ResponseEntity.ok(storyService.getAllStories());
    }




}
