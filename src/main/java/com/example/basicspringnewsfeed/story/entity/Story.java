package com.example.basicspringnewsfeed.story.entity;

import com.example.basicspringnewsfeed.common.entity.BaseEntity;
import com.example.basicspringnewsfeed.common.entity.IsDelete;
import com.example.basicspringnewsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "stories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Story extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 200, nullable = false)
    private String content;
    private String storyImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'N'")
    private IsDelete isDelete;

    public Story(User user, String storyImageUrl, String content) {
        this.user = user;
        this.storyImageUrl = storyImageUrl;
        this.content = content;
        this.isDelete = IsDelete.N;
    }

    // 삭제 상태 변경
    public void updateIsDelete(IsDelete isDelete) {
        this.isDelete = IsDelete.Y;
    }

}
