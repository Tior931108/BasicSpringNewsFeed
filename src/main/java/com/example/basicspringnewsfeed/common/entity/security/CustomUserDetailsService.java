package com.example.basicspringnewsfeed.common.entity.security;

import com.example.basicspringnewsfeed.user.entity.User;
import com.example.basicspringnewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    // 2025-11-24 : email 조회.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials" + email)); // 시큐리티 전용 예외
        return new CustomUserDetails(user);
    }

    // 2025-11-24 : filter
    @Override
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("Invalid credentials" + id)); // 시큐리티 전용 예외
        return new CustomUserDetails(user);
    }
}
