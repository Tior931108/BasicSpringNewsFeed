package com.example.basicspringnewsfeed.comment.controller;

import com.example.basicspringnewsfeed.comment.dto.request.CommentCreateRequest;
import com.example.basicspringnewsfeed.comment.dto.response.CommentCreateResponse;
import com.example.basicspringnewsfeed.comment.dto.response.CommentGetResponse;
import com.example.basicspringnewsfeed.comment.dto.response.CommentUpdateResponse;
import com.example.basicspringnewsfeed.comment.repository.CommentRepository;
import com.example.basicspringnewsfeed.comment.service.CommentService;
import com.example.basicspringnewsfeed.common.security.AuthUser;
import com.example.basicspringnewsfeed.common.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class Controller {
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    //댓글 생성
    @PostMapping("/{postId}/{userId}")
    public ResponseEntity<CommentCreateResponse> commentCreate(
            @PathVariable Long userId,
            @RequestBody CommentCreateRequest request,
            @AuthUser CurrentUser currentUser)
            {

        return ResponseEntity.ok().body(commentService.save(userId, currentUser.id(), request));
    }

    //댓글 단 건 조회
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentGetResponse> commentGet(
            @PathVariable Long commentId,
            @PathVariable Long postId,
            @AuthUser CurrentUser currentUser) {
        return ResponseEntity.ok().body(commentService.commentOneGet(commentId, currentUser,postId));
    }

    // 댓글 다 건 조회
    @GetMapping
    public ResponseEntity<List<CommentGetResponse>> commentAllGet() {
        List<CommentGetResponse> responses = commentService.commentAllGet();
        {
            return ResponseEntity.ok().body(responses);
        }
    }

    //댓글 내용 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentUpdateResponse> commentUpdate(
            @PathVariable Long commentId,
            @RequestBody CommentCreateRequest request,
            @AuthUser CurrentUser currentUser) {
        return ResponseEntity.ok(commentService.commentUpdate(commentId, request, currentUser));
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> commentDelete(
            @PathVariable Long commentId,
        @AuthUser CurrentUser currentUser) {
        commentService.commentDelete(commentId, currentUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}



