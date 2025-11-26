package com.example.basicspringnewsfeed.hashtag.controller;

import com.example.basicspringnewsfeed.hashtag.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hashtags")
@RequiredArgsConstructor
public class HashtagController {
    private final HashtagService hashtagService;

    /**
     * 인기 해시태그 Top 10 조회
     */
}
