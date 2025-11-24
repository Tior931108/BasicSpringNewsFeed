package com.example.basicspringnewsfeed.hashtag.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "hashtags")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagId;
    @Column(length = 100, nullable = false, unique = true)
    private String hashtagName;

    @Column(nullable = false)
    private Long hashtagCount = 0L;

    // 해시태그 사용 횟수 증가
    public void increaseCount() {
        this.hashtagCount++;
    }

    // 해시태그 사용 횟수 감소
    public void decreaseCount() {
        if (this.hashtagCount > 0) {
            this.hashtagCount--;
        }
    }

    public Hashtag(String hashtagName) {
        this.hashtagName = hashtagName;
        this.hashtagCount = 0L;
    }

}
