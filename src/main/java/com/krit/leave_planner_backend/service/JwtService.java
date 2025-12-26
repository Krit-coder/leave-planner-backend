package com.krit.leave_planner_backend.service;

import com.krit.leave_planner_backend.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET =
            "krit_leave_planner_backend_jwt_secret_256bit_key_1234567890";

    private static final Key SECRET_KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    private static final long EXPIRATION_TIME =
            1000 * 60 * 60 * 24;

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .claim("manager", user.getManagerAccess() == 1)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
