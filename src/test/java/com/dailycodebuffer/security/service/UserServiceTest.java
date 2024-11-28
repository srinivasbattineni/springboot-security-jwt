package com.dailycodebuffer.security.service;

import com.dailycodebuffer.security.entity.User;
import com.dailycodebuffer.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, bCryptPasswordEncoder, authenticationManager, jwtService);
    }

    @Test
    void testRegister() {
        User user = new User(null, null, null);
        user.setUserName("testUser");
        user.setPassword("rawPassword");

        String encodedPassword = "encodedPassword";
        when(bCryptPasswordEncoder.encode("rawPassword")).thenReturn(encodedPassword);
        when(userRepository.save(user)).thenReturn(user);

        User registeredUser = userService.register(user);

        assertThat(registeredUser).isNotNull();
        assertThat(registeredUser.getPassword()).isEqualTo(encodedPassword);
        verify(bCryptPasswordEncoder, times(1)).encode("rawPassword");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testVerify_Success() {
        User user = new User(null, null, null);
        user.setUserName("testUser");
        user.setPassword("testPassword");

        Authentication mockAuthentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuthentication);
        when(mockAuthentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        String result = userService.verify(user);

        assertThat(result).isEqualTo("jwtToken");
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(user);
    }

    @Test
    void testVerify_Failure() {
        User user = new User(null, null, null);
        user.setUserName("testUser");
        user.setPassword("testPassword");

        Authentication mockAuthentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuthentication);
        when(mockAuthentication.isAuthenticated()).thenReturn(false);

        String result = userService.verify(user);

        assertThat(result).isEqualTo("failure");
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, never()).generateToken(user);
    }
}
