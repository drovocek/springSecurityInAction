package ru.soft.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class JwtManagerImpl implements JwtManager {

    @Value("${jwt.signing.key}")
    private String signingKey;

    public String buildJWT(String username) {
        SecretKey key = Keys.hmacShaKeyFor(this.signingKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setClaims(Map.of("username", username))
                .signWith(key)
                .compact();
    }

    public Claims extractClaims(String jwt) {
        SecretKey key = Keys.hmacShaKeyFor(this.signingKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}
