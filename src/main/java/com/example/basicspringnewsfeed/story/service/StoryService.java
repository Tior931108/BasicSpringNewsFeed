package com.example.basicspringnewsfeed.story.service;

import com.example.basicspringnewsfeed.common.entity.IsDelete;
import com.example.basicspringnewsfeed.common.exception.CustomException;
import com.example.basicspringnewsfeed.common.exception.ErrorCode;
import com.example.basicspringnewsfeed.common.security.AuthUser;
import com.example.basicspringnewsfeed.common.security.CurrentUser;
import com.example.basicspringnewsfeed.story.dto.MessageResponseDto;
import com.example.basicspringnewsfeed.story.dto.StoryCreateRequestDto;
import com.example.basicspringnewsfeed.story.dto.StoryResponseDto;
import com.example.basicspringnewsfeed.story.dto.StoryUpdateRequestDto;
import com.example.basicspringnewsfeed.story.entity.Story;
import com.example.basicspringnewsfeed.story.repository.StoryRepository;
import com.example.basicspringnewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.basicspringnewsfeed.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoryService{
    private final StoryRepository storyRepository;
    private final UserRepository userRepository; // 유저 조회용

    // 1. 스토리 작성
    @Transactional
    public StoryResponseDto createStory(StoryCreateRequestDto requestDto){
        // 유저 조회
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 스토리에 유저 객체 넣기 작업
        Story story  = new Story(user, requestDto.getStoryImageUrl(), requestDto.getContent());

        //  저장
        Story savedStory = storyRepository.save(story);

        //  DTO 변환
        return new StoryResponseDto(savedStory);
    }


    // 2. 스토리 단건 조회
    @Transactional(readOnly = true)
    public StoryResponseDto getStory(Long storyId) {
        Story story = storyRepository.findByStoryIdAndIsDelete(storyId, IsDelete.N)
                .orElseThrow(()-> new CustomException(ErrorCode.STORY_NOT_FOUND));


        return new StoryResponseDto(story);
    }

    // 3. 전체 스토리 목록 조회 (다건 조회)
    @Transactional(readOnly = true)
    public List<StoryResponseDto> getAllStories(){
        List<Story> storyList =storyRepository.findByIsDeleteOrderByCreatedAtDesc(IsDelete.N);
        return storyList.stream()
                .map(StoryResponseDto::new)
                .collect(Collectors.toList());
    }

    // 4. 스토리 수정
    @Transactional
    public StoryResponseDto updateStory(Long storyId, StoryUpdateRequestDto requestDto,CurrentUser currentUser){
        Story story = storyRepository.findByStoryIdAndIsDelete(storyId, IsDelete.N)
                .orElseThrow(() -> new CustomException(ErrorCode.STORY_NOT_FOUND));
        if(!story.getUser().getUserId().equals(currentUser.id())){
            throw new CustomException(ErrorCode.STORY_UNAUTHORIZED_ACCESS);
        }
        story.updateStory(requestDto.getContent(),requestDto.getStoryImageUrl());
        return new StoryResponseDto(story);

    }

    // 5. 스토리 삭제
    /// IsDelete.Y (삭제됨)  IsDelete.N (삭제 안 됨)
    @Transactional
    public MessageResponseDto deleteStory(Long storyId, CurrentUser currentUser) {
        Story story = storyRepository.findByStoryIdAndIsDelete(storyId, IsDelete.N)
                .orElseThrow(() -> new CustomException(ErrorCode.STORY_NOT_FOUND));
        if(!story.getUser().getUserId().equals(currentUser.id())){
            throw new CustomException(ErrorCode.STORY_UNAUTHORIZED_ACCESS);
        }

        story.updateIsDelete(IsDelete.Y);
        return new MessageResponseDto("스토리가 삭제되었습니다");
    }

}