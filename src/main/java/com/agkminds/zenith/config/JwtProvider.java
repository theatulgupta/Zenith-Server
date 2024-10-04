package com.agkminds.zenith.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {
    private static final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
    private static final String ISSUER = "AGKMINDS";
    private static final long EXPIRATION_TIME = 86400000; // 24 hours

    public static String generateToken(Authentication authentication) {
        return Jwts.builder()
                .issuer(ISSUER)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("email", authentication.getName())
                .signWith(key)
                .compact();
    }

    public static String getEmailFromJwtToken(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt.replace("Bearer ", ""))
                .getBody();

        return claims.get("email", String.class);
    }
}