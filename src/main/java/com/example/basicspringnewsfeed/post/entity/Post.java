package com.example.basicspringnewsfeed.post.entity;

import com.example.basicspringnewsfeed.comment.entity.Comment;
import com.example.basicspringnewsfeed.common.entity.BaseEntity;
import com.example.basicspringnewsfeed.common.entity.IsDelete;
import com.example.basicspringnewsfeed.hashtagandpost.entity.HashtagPost;
import com.example.basicspringnewsfeed.image.entity.Image;
import com.example.basicspringnewsfeed.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 400, nullable = false)
    private String content;

    // 좋아요, 댓글 갯수
    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long likedCount;
    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long commentCount;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'N'")
    private IsDelete isDelete;

    public Post(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }



    // 좋아요 증가
    public void increaseLikeCount() {
        this.likedCount++;
    }

    // 좋아요 감소
    public void decreaseLikeCount() {
        if (this.likedCount > 0) {
            this.likedCount--;
        }
    }

    // 댓글 수 증가
    public void increaseCommentCount() {
        this.commentCount++;
    }

    // 댓글 수 감소
    public void decreaseCommentCount() {
        if (this.commentCount > 0) {
            this.commentCount--;
        }
    }

    // 삭제 상태 변경
    public void updateIsDelete(IsDelete isDelete) {
        this.isDelete = IsDelete.Y;
    }

    public void update(
            @NotBlank(message = "제목을 입력해주세요.")
            @Size(max = 100, message = "제목은 100자 이하로 입력해주세요.")
            String title,
            @NotBlank(message = "내용을 입력해주세요.")
            @Size(max = 400, message = "내용은 400자 이하로 입력해주세요.") String content) {
        this.title = title;
        this.content = content;
    }

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HashtagPost> postHashtags = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

}
