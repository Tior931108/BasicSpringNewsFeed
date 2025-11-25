package com.example.basicspringnewsfeed.comment.controller;

import com.example.basicspringnewsfeed.comment.dto.request.CommentCreateRequest;
import com.example.basicspringnewsfeed.comment.dto.response.CommentCreateResponse;
import com.example.basicspringnewsfeed.comment.dto.response.CommentGetResponse;
import com.example.basicspringnewsfeed.comment.dto.response.CommentUpdateResponse;
import com.example.basicspringnewsfeed.comment.repository.CommentRepository;
import com.example.basicspringnewsfeed.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class Controller {
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    //생성
    @PostMapping("/{postId}/{userId}")
    public ResponseEntity<CommentCreateResponse> commentCreate(
            @PathVariable Long postId,
            @PathVariable Long userId,
//            @AuthenticationPrincipal 추가 예정 //JWT시큐리티 관련
            @RequestBody CommentCreateRequest request) {

        return ResponseEntity.ok().body(commentService.save(postId, userId, request));
    }

    // 단건조회
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentGetResponse> commentGet(
            @PathVariable Long commentId) {
        return ResponseEntity.ok().body(commentService.commentOneGet(commentId));
    }

    //다 건 조회
    @GetMapping
    public ResponseEntity<List<CommentGetResponse>> commentAllGet() {
        List<CommentGetResponse> responses = commentService.commentAllGet();
        {
            return ResponseEntity.ok().body(responses);
        }
    }

    //수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentUpdateResponse> commentUpdate(
            @PathVariable Long commentId,
            @RequestBody CommentCreateRequest request) {
        return ResponseEntity.ok(commentService.commentUpdate(commentId, request));
    }
}



