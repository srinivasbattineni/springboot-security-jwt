package com.dailycodebuffer.security.service;

import com.dailycodebuffer.security.CustomUserDetails;
import com.dailycodebuffer.security.entity.User;
import com.dailycodebuffer.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customUserDetailsService = new CustomUserDetailsService(userRepository);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        // Arrange
        String username = "testUser";
        User user = new User(username, username, null);
        user.setUserName(username);
        user.setPassword("testPassword");
        when(userRepository.findByUserName(username)).thenReturn(user);

        // Act
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // Assert
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(username);
        verify(userRepository, times(1)).findByUserName(username);
    }

    @Test
    void testLoadUserByUsername_UserDoesNotExist() {
        // Arrange
        String username = "nonExistentUser";
        when(userRepository.findByUserName(username)).thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> customUserDetailsService.loadUserByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found");

        verify(userRepository, times(1)).findByUserName(username);
    }
}
