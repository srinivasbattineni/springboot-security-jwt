package com.dailycodebuffer.security.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleProductNotFoundException() {
        String errorMessage = "Product not found!";
        ProductNotFoundException exception = new ProductNotFoundException(errorMessage);

        ResponseEntity<String> response = globalExceptionHandler.handleProductNotFoundException(exception);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo(errorMessage);
    }

    @Test
    void testHandleGenericException() {
        String errorMessage = "Something went wrong!";
        Exception exception = new Exception(errorMessage);

        ResponseEntity<String> response = globalExceptionHandler.handleGenericException(exception);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isEqualTo("An unexpected error occurred: " + errorMessage);
    }
}
