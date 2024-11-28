package com.dailycodebuffer.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.userdetails.UserDetails;

import com.dailycodebuffer.security.entity.User;

import io.jsonwebtoken.Claims;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void testGenerateToken() {
        // Given
        User user = new User("testUser", "password", new ArrayList<>());

        // When
        String token = jwtService.generateToken(user);

        // Then
        assertNotNull(token, "Token should not be null");
        assertTrue(token.startsWith("eyJhbGciOiJIUzI1NiJ9."), "Token should start with header part");
    }

//    @Test
//    void testExtractUserName() {
//        User user = new User("SRINI", "password", new ArrayList<>());
//        String token = jwtService.generateToken(user);
//        System.out.println("Generated Token: " + token);  // Print the token
//
//        // Extract the username from the token
//        String username = jwtService.extractUserName(token);
//        System.out.println("Extracted Username: " + username);  // Print the extracted username
//
//        // Assert that the extracted username matches the expected value
//        assertEquals("SRINI", username, "Username should match");
//    }

//    @Test
//    void testIsTokenValid_validToken() {
//        // Given
//        String token = jwtService.generateToken(new User("testUser", "password", new ArrayList<>()));
//
//        when(userDetails.getUsername()).thenReturn("testUser");
//
//        // When
//        boolean isValid = jwtService.isTokenValid(token, userDetails);
//
//        // Then
//        assertTrue(isValid, "Token should be valid");
//    }

//    @Test
//    void testIsTokenValid_invalidToken() {
//        // Given
//        String token = jwtService.generateToken(new User("testUser", "password", new ArrayList<>()));
//
//        when(userDetails.getUsername()).thenReturn("wrongUser");
//
//        // When
//        boolean isValid = jwtService.isTokenValid(token, userDetails);
//
//        // Then
//        assertFalse(isValid, "Token should be invalid for mismatched username");
//    }

//    @Test
//    void testIsTokenExpired() {
//        // Given
//        String token = jwtService.generateToken(new User("testUser", "password", new ArrayList<>()));
//
//        // Simulate expired token
//        Date pastDate = new Date(System.currentTimeMillis() - 100000);
//        when(jwtService.extractExpiration(token)).thenReturn(pastDate);
//
//        // When
//        boolean isExpired = jwtService.isTokenExpired(token);
//
//        // Then
//        assertTrue(isExpired, "Token should be expired");
//    }
//
//    @Test
//    void testExtractClaims() {
//        // Given
//        String token = jwtService.generateToken(new User("testUser", "password", new ArrayList<>()));
//
//        // When
//        Claims claims = jwtService.extractClaims(token);
//
//        // Then
//        assertNotNull(claims, "Claims should not be null");
//        assertEquals("testUser", claims.getSubject(), "Claims should contain the correct username");
//    }
}
