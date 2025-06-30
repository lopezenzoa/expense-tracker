package com.portfolio.expense_tracker.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    // Secret key to signature the tokens. ¡Never load to GitHub! It's better to use context variables
    private static final String SECRET_KEY = Jwts.SIG.HS256.key().toString();

    // Generate a token for an authenticated user
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // The token owner
                // .claim("roles", userDetails.getAuthorities()) // Additional Info: user roles 
                .setIssuedAt(new Date(System.currentTimeMillis())) // When it was generated
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // The expiration time (1 hour in this case)
                .signWith(getKey(), SignatureAlgorithm.HS256) // Sign the token with SHA-256 and the secret key
                .compact(); // It translates to a jwt format (header.payload.signature)
    }

    //  Verifies if a token is valid for a given user
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // extracts the username from the token
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Extracts the username from the token (field "sub")
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Verifies if the token expired (exp < now)
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Extract all data (claims) from token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody(); // Gets the content (payload)
    }

    // Translate the secret key to a jwt object in order to use it in jjwt library
    private Key getKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
