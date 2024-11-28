package com.dailycodebuffer.security.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dailycodebuffer.security.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private String secretKey = null;

    public String generateToken(User user) {
        Map<String, Object> claims
                = new HashMap<>();
        String token= Jwts.builder().claims().add(claims).subject(user.getUserName()).issuer("SRINI").issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 60*10*1000))
                .and()
                .signWith(generateKey())
                .compact();
        
        System.out.println("Token==========="+token);
        
        return token;
    }

    private SecretKey generateKey() {
        byte[] decode = Decoders.BASE64.decode(getSecretKey());
        System.out.println(decode);

        return Keys.hmacShaKeyFor(decode);
    }


    public String getSecretKey() {
        return secretKey = "RqxPOuVfHoBA8Uq40MhJvfY6qEHOOWWvg6N9W9vt23s=";
    }

//    public String extractUserName(String token) {
//        return extractClaims(token, Claims::getSubject);
//    }
    public String extractUserName(String token) {
        Claims claims = extractClaims(token);
        System.out.println("Extracted Claims: " + claims);  // Print the claims
        return claims.getSubject();  // Return the subject (username)
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimResolver) {
        Claims claims = extractClaims(token);
        return claimResolver.apply(claims);
    }

    Claims extractClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
