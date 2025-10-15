package com.codewithmosh.store.auth;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.codewithmosh.store.users.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class JwtService {
    

    private final JwtConfig jwtConfig;

    public Jwt generateAccessToken(User user) {
        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    public Jwt generateRefreshToken(User user) {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    private Jwt generateToken(User user, final long tokenExpiration) {
        var claims = Jwts.claims()
                    .subject(user.getId().toString())
                    .add("email", user.getEmail())
                    .add("name", user.getName())
                    .add("role", user.getRole())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                    .build();

        return new Jwt(claims, jwtConfig.getSecretKey());
    }

    public Jwt parseToken(String token) {
        try {
            var claims = getClaims(token);
            return new Jwt(claims, jwtConfig.getSecretKey());
        } catch (JwtException e) {
            return null;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                        .verifyWith(jwtConfig.getSecretKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();
    }

    
}
