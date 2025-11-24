package com.example.basicspringnewsfeed.common.entity.security;

import com.example.basicspringnewsfeed.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private Long id;
    private String email;
    private String nickname;
    private String password;

    public CustomUserDetails(User user) {
        this.id=user.getId();
        this.email=user.getEmail();
        this.nickname=user.getNickname();
        this.password=user.getPasswordHash();
    }

    // 2025-11-24 : Role까진 다루지 않을 예정.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return Collections.emptyList();
    }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return email; } // principal = email로.

    // 2025-11-24 : Authorization : 미구현 //
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getNickname() { return nickname; }
}
