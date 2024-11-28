package com.dailycodebuffer.security.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.web.csrf.CsrfToken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class WelcomeControllerTest {

    private WelcomeController welcomeController;

    @Mock
    private HttpServletRequest request;

    @Mock
    private CsrfToken csrfToken;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        welcomeController = new WelcomeController();
    }

    @Test
    void testWelcome() {
        String response = welcomeController.welcome();
        assertThat(response).isEqualTo("Welcome to daily Code Buffer!!");
    }

    @Test
    void testGetToken() {
        when(request.getAttribute("_csrf")).thenReturn(csrfToken);

        CsrfToken result = welcomeController.getToken(request);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(csrfToken);

        verify(request, times(1)).getAttribute("_csrf");
    }

    @Test
    void testGetToken_NullCsrf() {
        when(request.getAttribute("_csrf")).thenReturn(null);

        CsrfToken result = welcomeController.getToken(request);

        assertThat(result).isNull();
        verify(request, times(1)).getAttribute("_csrf");
    }
}
