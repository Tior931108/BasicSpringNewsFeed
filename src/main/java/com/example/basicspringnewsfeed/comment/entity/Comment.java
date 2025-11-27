package com.example.basicspringnewsfeed.comment.entity;

import com.example.basicspringnewsfeed.common.entity.BaseEntity;
import com.example.basicspringnewsfeed.common.entity.IsDelete;
import com.example.basicspringnewsfeed.common.security.CurrentUser;
import com.example.basicspringnewsfeed.post.entity.Post;
import com.example.basicspringnewsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    //Post의 외래키 가지고옴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //Uer의 외래키 가지고옴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 자기 참조: 부모 댓글 -> 후 순위
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    //댓글 내용
    @Column(length = 200, nullable = false)
    private String content;

    //Eunm 값을 DB에 저장.문자열
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'N'")
    private IsDelete isDelete;

    //Comment 생성자
    public Comment(Post post, User user, String content) {
        this.post = post;
        this.user = user;
        this.parent = null; // 대댓글 추후 고도화
        this.content = content;
        this.isDelete = IsDelete.N;

    }

    // 수정 메서드
    public void commentUpdate(String content) {
        this.content = content;
    }

    // 삭제 상태 변경 메서드 (Y:삭제완료, N:삭제 아님)
    public void updateIsDelete(IsDelete isDelete) {
        this.isDelete = IsDelete.Y;
    }
}
