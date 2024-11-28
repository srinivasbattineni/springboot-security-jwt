package com.dailycodebuffer.security.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductNotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        String errorMessage = "Product not found!";
        ProductNotFoundException exception = new ProductNotFoundException(errorMessage);

        assertThat(exception.getMessage()).isEqualTo(errorMessage);
    }

    @Test
    void testExceptionWithNullMessage() {
        ProductNotFoundException exception = new ProductNotFoundException(null);

        assertThat(exception.getMessage()).isNull();
    }
}
