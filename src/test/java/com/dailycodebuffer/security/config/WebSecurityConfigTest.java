package com.dailycodebuffer.security.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WebSecurityConfigTest {

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private WebSecurityConfig webSecurityConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webSecurityConfig = new WebSecurityConfig(userDetailsService, jwtAuthenticationFilter);
    }

    @Test
    void testBCryptPasswordEncoder() {
        BCryptPasswordEncoder encoder = webSecurityConfig.bCryptPasswordEncoder();
        assertThat(encoder).isNotNull();
        assertThat(encoder.encode("password")).isNotBlank();
    }

//    @Test
//    void testAuthenticationProvider() {
//        AuthenticationProvider provider = webSecurityConfig.authenticationProvider();
//        assertThat(provider).isInstanceOf(DaoAuthenticationProvider.class);
//
//        DaoAuthenticationProvider daoProvider = (DaoAuthenticationProvider) provider;
//        assertThat(daoProvider.getUserDetailsService()).isEqualTo(userDetailsService);
//        assertThat(daoProvider.getPasswordEncoder()).isInstanceOf(BCryptPasswordEncoder.class);
//    }

    @Test
    void testAuthenticationManager() throws Exception {
        AuthenticationConfiguration authenticationConfiguration = mock(AuthenticationConfiguration.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

        // Mock behavior
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);

        AuthenticationManager result = webSecurityConfig.authenticationManager(authenticationConfiguration);
        assertThat(result).isEqualTo(authenticationManager);
    }

//    @Test
//    void testSecurityFilterChain() throws Exception {
//        HttpSecurity httpSecurity = mock(HttpSecurity.class);
//        SecurityFilterChain filterChain = webSecurityConfig.securityFilterChain(httpSecurity);
//
//        assertThat(filterChain).isNotNull();
//        // You can add more assertions by mocking `httpSecurity` to verify interactions or configurations if needed
//    }
}
