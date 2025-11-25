package com.example.basicspringnewsfeed.common.security;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;

import java.util.Date;



@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long accessTokenValidityMs;
    private static final long TEST_EXPIRATION_TOKEN_TIME = 1000L*60;

    // 2025-11-24 : accessToken 1 Hour.
    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret, // properties - yml : jwt : secret
            @Value("${jwt.access-token-validity-ms:3600000}") long accessTokenValidityMs) // access-token-validity-ms: 3600000
    {
        this.secretKey=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); // hash
        this.accessTokenValidityMs=accessTokenValidityMs;
    }
    public String generateToken(CustomUserDetails userDetails){
        Date now=new Date();
        Date expiration=new Date(now.getTime()+accessTokenValidityMs);

        return Jwts.builder()
                .subject(userDetails.getId().toString())
                .claim("email", userDetails.getUsername()) // email.
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    // 2025-11-24 : .signWith(), .verifyWith()
    private SecretKey getSigningKey(){
        return secretKey;
    }

    // 2025-11-24 : 서명 검증 - 서명된 JWS Parsing - Claims 객체 반환.
    public Claims parseClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 2025-11-24 : getUserId()
    public Long getUserId(String token){
        Claims claims=parseClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    // 2025-11-24 : validateTokenOrThrow : 만료된 token, 유효하지 않은 token 검증
    public void validateTokenOrThrow(String token){
        try{
            parseClaims(token);
        }catch(ExpiredJwtException e){
            throw new IllegalArgumentException("token expired");
        }catch(JwtException|IllegalArgumentException e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    // 2025-11-24 : generateExpiredToken() : Test 용도 : - 1분
    public String generateExpiredToken(Long userId){
        Date now=new Date();
        Date past=new Date(now.getTime()-TEST_EXPIRATION_TOKEN_TIME); // - 1 minute

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(past)
                .expiration(past) // 만료
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }
}
