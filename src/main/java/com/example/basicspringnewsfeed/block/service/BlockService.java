package com.example.basicspringnewsfeed.block.service;

import com.example.basicspringnewsfeed.block.dto.BlockRequest;
import com.example.basicspringnewsfeed.block.dto.BlockResponse;
import com.example.basicspringnewsfeed.block.dto.BlockedUserInfo;
import com.example.basicspringnewsfeed.block.entity.Block;
import com.example.basicspringnewsfeed.block.repository.BlockRepository;
import com.example.basicspringnewsfeed.common.exception.CustomException;
import com.example.basicspringnewsfeed.common.exception.ErrorCode;
import com.example.basicspringnewsfeed.common.security.CurrentUser;
import com.example.basicspringnewsfeed.user.entity.User;
import com.example.basicspringnewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class BlockService {
    private final UserRepository userRepository;
    private final BlockRepository blockRepository;

    // 차단 유저 생성
    public BlockResponse block(BlockRequest blockRequest) {
        // User 조회하기
        User user = userRepository.findById(blockRequest.getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)  // 없는 유저 조회 불가
        );
        User blockedUser = userRepository.findById(blockRequest.getBlockedUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.BLOCKED_USER_NOT_FOUND)  // 차단 유저 조회 불가
        );

        // 이미 차단한 유저인지 확인하기
        if(blockRepository.existsByUserAndBlockedUser(user, blockedUser)) {
            throw new CustomException(ErrorCode.BLOCK_ALREADY_EXIST);
        }

        // 차단 요청 생성하기
        Block block = new Block(user, blockedUser);
        block = blockRepository.save(block);  // 차단기록 DB에 저장
        return new BlockResponse(
                block.getBlockId(),
                block.getUser().getUserId(),
                block.getBlockedUser().getUserId()
        );
    }


    // 차단 유저 목록 조회
    @Transactional(readOnly = true)
    public List<BlockedUserInfo> getBlockedUsers(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        // 해당 user의 Block 목록
        List<Block> blocks = blockRepository.findByUser(user);

        // 반환할 Dto 리스트
        List<BlockedUserInfo> blockeddtos = new ArrayList<>();
        for (Block block : blocks) {
            BlockedUserInfo dto = new BlockedUserInfo(
                    block.getBlockedUser().getUserId()
            );
            blockeddtos.add(dto);
        }
        return blockeddtos;
    }

    // 차단 유저 삭제
    @Transactional
    public void deleteBlock(Long blockId, CurrentUser currentUser) {
        Block block = blockRepository.findById(blockId).orElseThrow(
                () -> new CustomException(ErrorCode.BLOCK_NOT_FOUND)  // 없는 차단유저 예외처리
        );

        if (!block.getUser().getUserId().equals(currentUser.id())) {
            throw new CustomException(ErrorCode.BLOCK_UNAUTHORIZED_ACCESS);
        }
        blockRepository.delete(block);
    }
}
