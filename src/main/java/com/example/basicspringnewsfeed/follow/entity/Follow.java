package com.example.basicspringnewsfeed.follow.entity;

import com.example.basicspringnewsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "follows")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private User followerId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private User followingId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'WAITING'")
    private IsFollow isFollow;

    public Follow(User followerId, User followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
        this.isFollow = IsFollow.WAITING; // 기본값 : 대기
    }

    // 수락 변경
    public void updateAccepted() {
        this.isFollow = IsFollow.ACCEPTED;
    }

    // 거절 변경
    public void updateRejected() {
        this.isFollow = IsFollow.REJECTED;
    }
}
