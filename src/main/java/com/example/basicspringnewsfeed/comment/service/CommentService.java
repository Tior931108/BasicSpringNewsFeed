package com.example.basicspringnewsfeed.comment.service;

import com.example.basicspringnewsfeed.comment.dto.request.CommentCreateRequest;
import com.example.basicspringnewsfeed.comment.dto.response.CommentCreateResponse;
import com.example.basicspringnewsfeed.comment.dto.response.CommentGetResponse;
import com.example.basicspringnewsfeed.comment.dto.response.CommentUpdateResponse;
import com.example.basicspringnewsfeed.comment.entity.Comment;
import com.example.basicspringnewsfeed.comment.repository.CommentRepository;
import com.example.basicspringnewsfeed.common.entity.IsDelete;
import com.example.basicspringnewsfeed.post.entity.Post;
import com.example.basicspringnewsfeed.post.repository.PostRepository;
import com.example.basicspringnewsfeed.user.entity.User;
import com.example.basicspringnewsfeed.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    //생성
    @Transactional
    public CommentCreateResponse save(Long postId, Long userId, CommentCreateRequest request) {
        // user 예외처리
        User user = userRepository.findById(userId).orElseThrow(
                // 예외처리 custom으로 변경 예정
                () -> new IllegalArgumentException("유저을 찾을 수 없습니다."));

        //post 예외처리
        Post post = postRepository.findById(postId).orElseThrow(
                // 예외처리 custom으로 변경 예정
                () -> new IllegalStateException("게시글를 찾을 수 없습니다."));
        Comment comment = new Comment(
                post,
                user,
                request.getContent()
        );
        Comment commentSave = commentRepository.save(comment);

        //댓글 카운트 (post 클래스에서 호출)
        post.increaseCommentCount();

        return new CommentCreateResponse(
                commentSave.getCommentId(),
                commentSave.getUser().getUserId(),
                commentSave.getUser().getNickname(),
                commentSave.getContent(),
                commentSave.getCreatedAt());
    }

    //단 건 조회
    //Todo 삭제 시 조회 목록에서 제거하기 구현 예정
    @Transactional(readOnly = false)
    public CommentGetResponse commentOneGet(@PathVariable Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                // 예외처리 변경할 부분
                () -> new IllegalArgumentException("댓글을 조회할 수 없습니다.")
        );

        {
            // 라인화 하기
            CommentGetResponse commentGetResponse = new CommentGetResponse(
                    comment.getCommentId(),
                    comment.getUser().getUserId(),
                    comment.getUser().getNickname(),
                    comment.getContent(),
                    comment.getCreatedAt()
            );
            return commentGetResponse;
        }
    }

    //다 건 조회
    //Todo 삭제 시 조회 목록에서 제거하기 구현 예정
    @Transactional(readOnly = false)
    public List<CommentGetResponse> commentAllGet() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentGetResponse> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentGetResponse dto = new CommentGetResponse(
                    comment.getCommentId(),

                    comment.getUser().getUserId(),
                    comment.getUser().getNickname(),
                    comment.getContent(),
                    comment.getCreatedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    //수정
    @Transactional
    public CommentUpdateResponse commentUpdate(Long commentId, CommentCreateRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                // 예외 처리 부분 변경하기
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );
        comment.commentUpdate(
                request.getContent()
        );

        return new CommentUpdateResponse(
                comment.getCommentId(),
                comment.getUser().getUserId(),
                comment.getUser().getNickname(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    //삭제
    @Transactional
    public void commentDelete(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                //에러 발생 변경
                () -> new IllegalArgumentException("존재하지 않은 댓글입니다.")
        );

        //IsDelete 겂을 No -> Yes(비활성)로 변경
        comment.updateIsDelete(IsDelete.Y);

        //post 값을 comment로 불러옴
        Post post = comment.getPost();

        //post에서 불러온 기능으로 함수 카운터를 마이너스함
        post.decreaseCommentCount();
    }
}

