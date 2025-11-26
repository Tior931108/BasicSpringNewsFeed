package com.example.basicspringnewsfeed.block.controller;

import com.example.basicspringnewsfeed.block.dto.BlockRequest;
import com.example.basicspringnewsfeed.block.dto.BlockResponse;
import com.example.basicspringnewsfeed.block.dto.BlockedUserInfo;
import com.example.basicspringnewsfeed.block.service.BlockService;
import com.example.basicspringnewsfeed.common.security.AuthUser;
import com.example.basicspringnewsfeed.common.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BlockController {
    private final BlockService blockService;

    // 차단 유저 생성
    @PostMapping("/blocks")
    public ResponseEntity<BlockResponse> block(@RequestBody BlockRequest blockRequest) {
        BlockResponse blockResponse = blockService.block(blockRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(blockResponse);
    }

    // 차단 유저 조회
    @GetMapping("/blocks/{userId}")
    public ResponseEntity<List<BlockedUserInfo>> getBlockedUsers(@PathVariable Long userId) {
        List<BlockedUserInfo> blockedUsers = blockService.getBlockedUsers(userId);
        return ResponseEntity.ok(blockedUsers);
    }

    // 차단 유저 삭제(차단해제)
    @DeleteMapping("/blocks/{blockId}")
    public ResponseEntity<String> deleteBlock(@PathVariable Long blockId, @AuthUser CurrentUser currentUser) {
        blockService.deleteBlock(blockId, currentUser);
        return ResponseEntity.ok("차단이 해제되었습니다.");  // 차단 해제 문구 출력
    }
}
