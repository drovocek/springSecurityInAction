package ru.soft.security.filter;

import io.jsonwebtoken.Claims;

public interface JwtManager {

    String buildJWT(String username);

    Claims extractClaims(String jwt);
}
