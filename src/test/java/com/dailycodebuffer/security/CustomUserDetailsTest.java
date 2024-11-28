package com.dailycodebuffer.security;

import com.dailycodebuffer.security.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class CustomUserDetailsTest {

    private User user;
    private CustomUserDetails customUserDetails;

    @BeforeEach
    void setUp() {
        user = new User(null, null, null);
        user.setUserName("testUser");
        user.setPassword("testPassword");
        customUserDetails = new CustomUserDetails(user);
    }

    @Test
    void testGetUsername() {
        // Act
        String username = customUserDetails.getUsername();

        // Assert
        assertThat(username).isEqualTo("testUser");
    }

    @Test
    void testGetPassword() {
        // Act
        String password = customUserDetails.getPassword();

        // Assert
        assertThat(password).isEqualTo("testPassword");
    }

    @Test
    void testGetAuthorities() {
        // Act
        Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();

        // Assert
        assertThat(authorities).isNotNull();
        assertThat(authorities).hasSize(1);
        assertThat(authorities.iterator().next().getAuthority()).isEqualTo("USER");
    }

    @Test
    void testIsAccountNonExpired() {
        // Act
        boolean isAccountNonExpired = customUserDetails.isAccountNonExpired();

        // Assert
        assertThat(isAccountNonExpired).isTrue();
    }

    @Test
    void testIsAccountNonLocked() {
        // Act
        boolean isAccountNonLocked = customUserDetails.isAccountNonLocked();

        // Assert
        assertThat(isAccountNonLocked).isTrue();
    }

    @Test
    void testIsCredentialsNonExpired() {
        // Act
        boolean isCredentialsNonExpired = customUserDetails.isCredentialsNonExpired();

        // Assert
        assertThat(isCredentialsNonExpired).isTrue();
    }

    @Test
    void testIsEnabled() {
        // Act
        boolean isEnabled = customUserDetails.isEnabled();

        // Assert
        assertThat(isEnabled).isTrue();
    }
}
