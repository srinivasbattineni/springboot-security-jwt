package com.dailycodebuffer.security.controller;

import com.dailycodebuffer.security.entity.User;
import com.dailycodebuffer.security.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(null, userService);
    }

    @Test
    void testRegister() {
        User user = new User(null, null, null);
        user.setUserName("testUser");
        user.setPassword("testPass");

        when(userService.register(user)).thenReturn(user);

        User result = userController.register(user);

        assertThat(result).isNotNull();
        assertThat(result.getUserName()).isEqualTo("testUser");
        verify(userService, times(1)).register(user);
    }

    @Test
    void testLogin_Success() {
        User user = new User(null, null, null);
        user.setUserName("testUser");
        user.setPassword("testPass");

        when(userService.verify(user)).thenReturn("success");

        String result = userController.login(user);

        assertThat(result).isEqualTo("success");
        verify(userService, times(1)).verify(user);
    }

    @Test
    void testLogin_Failure() {
        User user = new User(null, null, null);
        user.setUserName("invalidUser");
        user.setPassword("wrongPass");

        when(userService.verify(user)).thenReturn("failure");

        String result = userController.login(user);

        assertThat(result).isEqualTo("failure");
        verify(userService, times(1)).verify(user);
    }
}
