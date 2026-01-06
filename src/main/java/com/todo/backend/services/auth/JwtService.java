package com.todo.backend.services.auth;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
    private static final String SECRET = "jnupreha[gib;wiugb[aubbwhg;iajewhpuh;feawb;iufbvsiubr;visbiuzgbupwez;bviyf";
    private static final long TOKEN_EXPIRATION_MILLIS = 1000L * 60 * 60 * 24; // 24 hours

    public String generateToken(String subject) {
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims, subject);
    }

    public String extractUser(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private String createToken(Map<String, Object> extraClaims, String subject) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + TOKEN_EXPIRATION_MILLIS);

        return Jwts.builder()
                .claims(extraClaims)
                .subject(subject)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }
}
