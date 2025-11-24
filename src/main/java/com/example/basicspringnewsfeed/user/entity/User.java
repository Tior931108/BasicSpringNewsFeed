package com.example.basicspringnewsfeed.user.entity;

import com.example.basicspringnewsfeed.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@SQLDelete(sql="UPDATE users SET is_delete='Y' WHERE id=?")
@Table(name="users", // DDL : Index 정의
        uniqueConstraints=@UniqueConstraint(name = "uk_users_email", columnNames="email"),
            indexes={@Index(name="idx_users_nickname", columnList="nickname")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    // 2025-11-23 : 가독성 겸 uk 역할을 위한 id로 변경.
    // passwordHash : 보안 사고 방지 겸 적은 것이니 건들지 말아주세요.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="email", length = 50, nullable = false)
    private String email;
    @Column(name="password_hash", length=254, nullable = false)
    private String passwordHash;
    // 2025-11-23 : isDeleted와 nickname unique 충돌 우려.
    @Column(length=50, nullable = false)
    private String nickname;
    @Column(length=254, nullable = false)
    private String profileImageUrl;
    @Column(length = 200)
    private String introduce;

    // 2025-11-23 : isDeleted는 User 기본 CRUD 구현 직후로 따로 알아볼 예정.
    /*@Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'N'")
    private IsDelete isDelete;*/

    @Builder
    private User(String email, String passwordHash, String nickname, String profileImageUrl, String introduce) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.introduce = introduce;
    }

    // 2025-11-23 updateProfile()
    public void updateProfile(String nickname, String profileImageUrl, String introduce){
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.introduce = introduce;
    }

    // 2025-11-23 changePassword()
    public void changePassword(String passwordHash){
        this.passwordHash = passwordHash;
    }

    // 2025-11-23 changeEmail()
    public void changeEmail(String email){
        this.email = email;
    }
}
