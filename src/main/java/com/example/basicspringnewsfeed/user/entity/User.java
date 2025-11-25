package com.example.basicspringnewsfeed.user.entity;
import com.example.basicspringnewsfeed.common.entity.BaseEntity;
import com.example.basicspringnewsfeed.common.entity.IsDelete;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLDelete;

@Getter
@Entity
@SQLDelete(sql="UPDATE users SET is_delete = 'Y' WHERE user_id = ?")
@FilterDef(name="notDeletedUserFilter")
@Filter(name="notDeletedUserFilter", condition="is_delete ='N'")
@Table(name="users", // DDL : Index 정의
        uniqueConstraints=@UniqueConstraint(name = "uk_users_email", columnNames="email"),
            indexes={@Index(name="idx_users_nickname", columnList="nickname")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    // 2025-11-23 : 가독성 겸 uk 역할을 위한 id로 변경.
    // passwordHash : 보안 사고 방지 겸 적은 것이니 건들지 말아주세요.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name="email", length = 50, nullable = false)
    private String email;
    @Column(length=254, nullable = false)
    private String password;

    // 2025-11-23 : isDeleted와 nickname unique 충돌 우려.
    @Column(length=50, nullable = false)
    private String nickname;
    @Column(length=254, nullable = false)
    private String profileImageUrl;
    @Column(length = 200)
    private String introduce;
  
    @Enumerated(EnumType.STRING)
    @Column(name = "is_delete", length = 10, nullable = false)
    private IsDelete isDelete = IsDelete.N;

    @Builder
    private User(String email, String password, String nickname, String profileImageUrl, String introduce) {
        this.email = email;
        this.password = password;
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
    public void changePassword(String password){
        this.password = password;
    }

    // 2025-11-23 changeEmail()
    public void changeEmail(String email){
        this.email = email;
    }

    /**
     * 안녕하세요! 진수님! 2조 팀장 정용준입니다.
     *
     * 우선 먼저 진수님이 힘들게 구현하신 시큐리티 필터와 관련하여
     * 전달의도는 이렇게 해보심이 어떻겠느냐 늬앙스로 말씀 드렸지만,
     * 진수님 입장에서 월권 행위일수도 느껴질수 있겠다는점과 기분 나쁠 수 있다는점 충분히 공감합니다.
     * 팀장으로서 팀원 개인의 마음을 잘 살펴보지 못한점 사과 말씀 드립니다.
     *
     * 더불어서 아래 구현한 메소드는 진수님이 걱정하시는 삭제여부 N > Y로 바꾸더라도
     * 회원가입시 이메일 중복 체크를 어떻게 해야되는지 말씀해주셨는데요. 도움이 될까 싶어서 납겨드립니다.
     * 궁금한 점이 있으시면 언제든 저를 찾아와 주세요 ! 같이 해결해 보아요.
     *
     * 개인적으로 찾아보고 계신다고 하셔서
     * 추가적으로 제가 건의드리면 불편할 수 있을것 같아 메시지로 남겨드려요.
     * 사용여부는 진수님이 정하시면 됩니다.
     * 삭제시에 아래 메소드 활용하시면 회원 가입시에 이메일 중복 체크 문제도 해결 될겁니다.
     *
     * 그리고 common entity 패키지안에 있던것 패키지 경로만 옮겼고 그외는 건들지 않았어요!
     * 남은 프로젝트 잘 부탁드립니다!
     */

    // Soft Delete 시 이메일 변조 메서드
    // 이메일 변조: email@example.com → deleted_123456789_email@example.com
    public void softDelete() {
        this.isDelete = IsDelete.Y;
        this.email = "deleted_" + System.currentTimeMillis() + "_" + this.email;
    }


}
