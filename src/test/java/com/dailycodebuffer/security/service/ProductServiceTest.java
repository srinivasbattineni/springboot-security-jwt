package com.dailycodebuffer.security.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.dailycodebuffer.security.entity.Product;
import com.dailycodebuffer.security.exception.ProductNotFoundException;
import com.dailycodebuffer.security.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

/**
 * Unit tests for {@link ProductService}.
 * 
 * This test class verifies the business logic implemented in the ProductService class.
 * It uses Mockito to mock dependencies and verify interactions with the ProductRepository.
 * 
 * Author: Srinivas Battineni
 */
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    /**
     * Sets up the test environment by initializing mocks.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the getAllProducts method to ensure it retrieves all products from the repository.
     */
    @Test
    void testGetAllProducts() {
        Product product1 = new Product(1, "Product1", 100.0);
        Product product2 = new Product(2, "Product2", 200.0);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        var products = productService.getAllProducts();

        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    /**
     * Tests the getProductById method for a valid product ID.
     * Ensures the correct product is returned.
     */
    @Test
    void testGetProductById_Success() {
        Product product = new Product(1, "Product1", 100.0);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        var result = productService.getProductById(1);

        assertNotNull(result);
        assertEquals("Product1", result.getProductName());
        verify(productRepository, times(1)).findById(1);
    }

    /**
     * Tests the getProductById method for a non-existent product ID.
     * Ensures a ProductNotFoundException is thrown.
     */
    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.getProductById(1);
        });

        assertEquals("Product not found with ID: 1", exception.getMessage());
        verify(productRepository, times(1)).findById(1);
    }
}
